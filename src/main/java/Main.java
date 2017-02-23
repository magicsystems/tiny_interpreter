import ui.Editor;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Main {
    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.setDefaultCloseOperation(EXIT_ON_CLOSE);
        editor.setVisible(true);
    }
}
