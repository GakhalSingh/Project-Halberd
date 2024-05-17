import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setupFrame();
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        addHeader(mainPanel, gbc);
        addLoginForm(mainPanel, gbc);

        add(mainPanel);
    }

    private void setupFrame() {
        setTitle("AI-Assistant");
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void addHeader(JPanel mainPanel, GridBagConstraints gbc) {
        RoundedPanel headerPanel = new RoundedPanel(30, new Color(0, 184, 148));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel("AI-Assistant (AIa)");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Verbeter je leerervaring met AIa!");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(subtitleLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        mainPanel.add(headerPanel, gbc);
    }

    private void addLoginForm(JPanel mainPanel, GridBagConstraints gbc) {
        RoundedPanel formPanel = new RoundedPanel(30, new Color(140, 132, 195));
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(10, 10, 10, 10);
        formGbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Gebruikersnaam");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formPanel.add(usernameLabel, formGbc);

        JTextField usernameField = new JTextField(20);
        formGbc.gridx = 1;
        formPanel.add(usernameField, formGbc);

        JLabel passwordLabel = new JLabel("Wachtwoord");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.WHITE);
        formGbc.gridx = 0;
        formGbc.gridy = 1;
        formPanel.add(passwordLabel, formGbc);

        JPasswordField passwordField = new JPasswordField(20);
        formGbc.gridx = 1;
        formPanel.add(passwordField, formGbc);

        RoundedButton loginButton = new RoundedButton("Log in", 20, new Color(0, 184, 148), Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formGbc.gridx = 0;
        formGbc.gridy = 2;
        formGbc.gridwidth = 2;
        formGbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(loginButton, formGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        mainPanel.add(formPanel, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}

class RoundedPanel extends JPanel {
    private Color backgroundColor;
    private int cornerRadius;

    public RoundedPanel(int radius, Color bgColor) {
        super();
        cornerRadius = radius;
        backgroundColor = bgColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);
    }
}

class RoundedButton extends JButton {
    private int cornerRadius;
    private Color backgroundColor;
    private Color foregroundColor;

    public RoundedButton(String text, int radius, Color bgColor, Color fgColor) {
        super(text);
        cornerRadius = radius;
        backgroundColor = bgColor;
        foregroundColor = fgColor;
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(fgColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);
        super.paintComponent(g);
    }

    @Override
    public void paint(Graphics g) {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        super.paint(g);
    }
}

