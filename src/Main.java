import javax.swing.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Gui gui = new Gui();
                gui.guiStartScherm();
                new Main();
            }
        });

    }
}
