import java.util.Scanner;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {

        setTitle("AI-Assistant");
        setSize(1000, 600);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        BackgroundPanel mainPanel = new BackgroundPanel("background.jpg");
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(true);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel("Creëer je eigen AI-assistant (Aia) 😲");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel("Laat Aia uw leerefficiëntie verbeteren 📚");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(titleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(descriptionLabel);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        mainPanel.add(leftPanel, gbc);


        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(10, 10, 10, 10);
        rightGbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel quickStartLabel = new JLabel("Laten we beginnen!");
        quickStartLabel.setFont(new Font("Arial", Font.BOLD, 18));
        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightGbc.gridwidth = 2;
        rightPanel.add(quickStartLabel, rightGbc);

        JLabel usernameLabel = new JLabel("Gebruikersnaam");
        rightGbc.gridx = 0;
        rightGbc.gridy = 1;
        rightGbc.gridwidth = 1;
        rightPanel.add(usernameLabel, rightGbc);

        JTextField usernameField = new JTextField(20);
        rightGbc.gridx = 1;
        rightGbc.gridy = 1;
        rightPanel.add(usernameField, rightGbc);

        JLabel passwordLabel = new JLabel("Wachtwoord");
        rightGbc.gridx = 0;
        rightGbc.gridy = 2;
        rightPanel.add(passwordLabel, rightGbc);

        JPasswordField passwordField = new JPasswordField(20);
        rightGbc.gridx = 1;
        rightGbc.gridy = 2;
        rightPanel.add(passwordField, rightGbc);

        JButton registerButton = new JButton("Log in");
        registerButton.setBackground(new Color(255, 255, 255));
        registerButton.setForeground(Color.BLACK);
        rightGbc.gridx = 1;
        rightGbc.gridy = 3;
        rightGbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(registerButton, rightGbc);


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        mainPanel.add(rightPanel, gbc);


        add(mainPanel);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}
