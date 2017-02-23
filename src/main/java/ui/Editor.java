package ui;


import ui.action.ExitAction;
import ui.action.OpenAction;
import ui.action.SaveAction;
import ui.listener.ProgramTextKeyListener;
import interpreter.Interpreter;
import interpreter.Result;
import interpreter.error.ParserError;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Editor extends JFrame {
    private final JTextPane programTextComponent;
    private final JTextPane programOutputTextComponent;
    private final Interpreter interpreter;

    public Editor() {
        super("Tiny Interpreter");
        programTextComponent = createProgramTextArea(this);
        programOutputTextComponent = createProgramOutputTextArea();
        interpreter = new Interpreter();
        Container content = getContentPane();
        content.add(new JScrollPane(programTextComponent), BorderLayout.CENTER);
        content.add(new JScrollPane(programOutputTextComponent), BorderLayout.SOUTH);
        setJMenuBar(createMenuBar(this));
        setSize(600, 450);
    }

    public void onSaveAction(FileWriter writer) throws IOException {
        programTextComponent.write(writer);
    }

    public void onOpenAction(Reader reader) throws IOException {
        programTextComponent.read(reader, null);
        onProgramTextChanged();
    }

    public void onProgramTextChanged() {
        String text = programTextComponent.getText();
        Result result = interpreter.execute(text);
        Map<String, List<ParserError>> errors = result.getErrors();
        StringJoiner joiner = new StringJoiner("\n");
        result.getOutput().forEach(joiner::add);
        updateHighlights(errors);
        programOutputTextComponent.setText(joiner.toString());
    }

    private void updateHighlights(Map<String, List<ParserError>> errors) {
        String text = programTextComponent.getText();
        Highlighter highlighter = programTextComponent.getHighlighter();
        Highlighter.HighlightPainter painter =
                new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
        highlighter.removeAllHighlights();
        for (String errorCodeLines : errors.keySet()) {
            int start = text.indexOf(errorCodeLines);
            int end = start + errorCodeLines.length();
            try {
                highlighter.addHighlight(start, end, painter);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    private static CompoundBorder createBorder(String label) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(label),
                BorderFactory.createEmptyBorder(3, 3, 3, 3));
    }


    private static JTextPane createProgramTextArea(Editor editor) {
        JTextPane textArea = new JTextPane();
        textArea.setBorder(createBorder("Program"));
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.addKeyListener(new ProgramTextKeyListener(editor));
        return textArea;
    }

    private static JTextPane createProgramOutputTextArea() {
        JTextPane textArea = new JTextPane();
        textArea.setEditable(false);
        textArea.setBorder(createBorder("Program output"));
        return textArea;
    }

    private static JMenuBar createMenuBar(Editor editor) {
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        menu.add(file);
        file.add(new OpenAction(editor));
        file.add(new SaveAction((editor)));
        file.add(new ExitAction());
        return menu;
    }
}
