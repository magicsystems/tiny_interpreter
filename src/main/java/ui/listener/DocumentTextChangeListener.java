package ui.listener;

import ui.Editor;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocumentTextChangeListener implements DocumentListener {
    private final Editor editor;

    public DocumentTextChangeListener(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        editor.onProgramTextChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        editor.onProgramTextChanged();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
