package ui.listener;

import ui.Editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class ProgramTextKeyListener implements KeyListener {
    private final Editor editor;

    public ProgramTextKeyListener(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (isProgramNeedToBeRecalculated(keyCode)) {
            editor.onProgramTextChanged();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    private static boolean isProgramNeedToBeRecalculated(int keycode) {
        return (keycode != 16 && keycode < 37) || keycode > 39;
    }
}
