package ui;


import ui.action.ExitAction;
import ui.action.OpenAction;
import ui.action.SaveAction;
import ui.listener.ProgramTextKeyListener;
import interpreter.Interpreter;
import interpreter.Result;
import interpreter.error.ParserError;
import ui.listener.ProgramTextMouseMotionListener;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Editor extends JFrame {
    private final JTextArea programTextComponent;
    private final JTextArea programOutputTextComponent;
    private final Interpreter interpreter;

    private volatile Map<Range, String> errorsMap = new HashMap<>();

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

    public Map<Range, String> getErrorsMap() {
        return errorsMap;
    }

    public JTextArea getProgramTextComponent() {
        return programTextComponent;
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
        errorsMap = processErrors(errors);
        programOutputTextComponent.setText(joiner.toString());
    }

    private HashMap<Range, String> processErrors(Map<String, List<ParserError>> errors) {
        String text = "\n" + programTextComponent.getText() + "\n";
        Highlighter highlighter = programTextComponent.getHighlighter();
        Highlighter.HighlightPainter painter =
                new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
        highlighter.removeAllHighlights();
        HashMap<Range, String> newMapWithErrors = new HashMap<>();
        for (Map.Entry<String, List<ParserError>> errorWithLine : errors.entrySet()) {
            String line = errorWithLine.getKey();
            int start = text.indexOf("\n" + line + "\n");
            int end = start + line.length();
            try {
                highlighter.addHighlight(start, end, painter);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            newMapWithErrors.put(new Range(start, end), errorWithLine.getValue().get(0).getMessage());
        }
        return newMapWithErrors;
    }

    private static JTextArea createProgramTextArea(Editor editor) {
        final JTextArea textArea = new JTextArea();
        textArea.setBorder(createBorder("Program"));
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.addKeyListener(new ProgramTextKeyListener(editor));
        textArea.addMouseMotionListener(new ProgramTextMouseMotionListener(editor));
        return textArea;
    }

    private static JTextArea createProgramOutputTextArea() {
        JTextArea textArea = new JTextArea();
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

    private static CompoundBorder createBorder(String label) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(label),
                BorderFactory.createEmptyBorder(3, 3, 3, 3));
    }

}
