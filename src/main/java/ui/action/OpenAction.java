package ui.action;


import ui.Editor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OpenAction extends AbstractAction {
    private final Editor editor;

    public OpenAction(Editor editor) {
        super("Open");
        this.editor = editor;
    }

    public void actionPerformed(ActionEvent ev) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(editor) !=
                JFileChooser.APPROVE_OPTION)
            return;
        File file = chooser.getSelectedFile();
        if (file == null)
            return;

        try (FileReader reader = new FileReader(file)) {
            editor.onOpenAction(reader);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(editor,
                    "File Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
