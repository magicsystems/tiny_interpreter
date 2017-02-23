package ui.action;

import ui.Editor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SaveAction extends AbstractAction {
    private final Editor editor;

    public SaveAction(Editor editor) {
        super("Save");
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(editor) !=
                JFileChooser.APPROVE_OPTION)
            return;
        File file = chooser.getSelectedFile();
        if (file == null)
            return;

        try (FileWriter writer = new FileWriter(file)) {
            editor.onSaveAction(writer);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(editor,
                    "File Not Saved", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
