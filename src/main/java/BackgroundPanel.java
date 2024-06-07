import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String fileName) {
        try {
            InputStream is = getClass().getResourceAsStream(fileName);
            if (is != null) {
                backgroundImage = new ImageIcon(ImageIO.read(is)).getImage();
            } else {
                System.err.println("File niet gevonden: " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon icon = new ImageIcon(getClass().getResource("\\img\\background.jpg")); // path niet fixen breekt applicatie
        Image image = icon.getImage();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
