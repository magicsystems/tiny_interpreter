package ui.listener;


import ui.Editor;
import ui.Range;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Map;

public class ProgramTextMouseMotionListener implements MouseMotionListener {
    private final Editor editor;

    public ProgramTextMouseMotionListener(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Map<Range, String> mapWithErrors = editor.getErrorsMap();
        JTextArea textArea = editor.getProgramTextComponent();
        textArea.setToolTipText("");
        int offset = textArea.viewToModel(e.getPoint());
        mapWithErrors.entrySet().stream().
                filter(entry -> entry.getKey().isInRange(offset))
                .forEach(entry -> {
                    textArea.setToolTipText(entry.getValue());
                });

    }
}
