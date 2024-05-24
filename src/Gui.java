import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Gui extends JFrame{

    public void guiStartScherm(){
        setTitle("AI Study Help Assistant (A.I.S.H.A.)");
        setSize(1250, 750);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建内容面板并设置背景图片
        JPanel contentPane = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 使用类加载器加载背景图片
                ImageIcon icon = new ImageIcon(getClass().getResource("/resources/background.jpg"));
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        contentPane.add(panel, BorderLayout.CENTER);

        setVisible(true);


        BackgroundPanel mainPanel = new BackgroundPanel("src/resources/background.jpg");
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(true); // 设置透明以显示背景
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel("Chat met A.I.S.H.A.!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel("Verbeter je leerervaring met Aisha (Artificial Intelligence Study Help Assistent!");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
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
        quickStartLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightGbc.gridwidth = 2;
        rightPanel.add(quickStartLabel, rightGbc);

        JLabel usernameLabel = new JLabel("Gebruikersnaam/Email");
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

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(52, 152, 219));
        loginButton.setForeground(Color.BLACK);
        rightGbc.gridx = 1;
        rightGbc.gridy = 3;
        rightGbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(loginButton, rightGbc);


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        mainPanel.add(rightPanel, gbc);


        add(mainPanel);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (authenticate(username, password)) {
                    JFrame mainFrame = new JFrame("Main Application");
                    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    mainFrame.setSize(800, 600);
                    mainFrame.setVisible(true);
                    dispose(); // close the login frame
                } else {
                    JOptionPane.showMessageDialog(Gui.this, "Invalid username or password");
                }
            }
        });
    }

    private static boolean authenticate(String username, String password) {

        CSVReader reader = new CSVReader("accounts.csv");
        Map<String, String> accounts = reader.readAccounts();
        return accounts.containsKey(username) && accounts.get(username).equals(password);
    }
    public void guiMainPanneel(){

    }
}
