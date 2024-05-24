import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Gui extends JFrame {
    public void guistartscherm() {
        setTitle("AI-assistent");
        setSize(1000, 600);
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

        JLabel titleLabel = new JLabel("Bouw je eigen AI-assistent");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel("Laat AI uw leerefficiëntie verbeteren");
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


        JLabel quickStartLabel = new JLabel("We gaan begin!");
        quickStartLabel.setFont(new Font("Arial", Font.BOLD, 18));
        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightGbc.gridwidth = 2;
        rightPanel.add(quickStartLabel, rightGbc);

        JLabel usernameLabel = new JLabel("Gebruiksnaam");
        rightGbc.gridx = 0;
        rightGbc.gridy = 1;
        rightGbc.gridwidth = 1;
        rightPanel.add(usernameLabel, rightGbc);

        JTextField usernameField = new JTextField(20);
        rightGbc.gridx = 1;
        rightGbc.gridy = 1;
        rightPanel.add(usernameField, rightGbc);

        JLabel passwordLabel = new JLabel("Password");
        rightGbc.gridx = 0;
        rightGbc.gridy = 2;
        rightPanel.add(passwordLabel, rightGbc);

        JPasswordField passwordField = new JPasswordField(20);
        rightGbc.gridx = 1;
        rightGbc.gridy = 2;
        rightPanel.add(passwordField, rightGbc);

        JButton loginButton = new JButton("Inloggen");
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
                    showMainApp();
                    dispose(); // close the login frame
                } else {
                    JOptionPane.showMessageDialog(Gui.this, "Invalid username or password");
                }
            }
        });
    }

    private static boolean authenticate(String username, String password) {


        return "admin".equals(username) && "111111".equals(password);
    }

    private void showMainApp() {
        JFrame mainFrame = new JFrame("Main Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1200, 800);
        mainFrame.setMinimumSize(new Dimension(800, 600));
        mainFrame.setLocationRelativeTo(null);

        // 主内容面板
        JPanel contentPane = new JPanel(new BorderLayout());
        mainFrame.setContentPane(contentPane);

        // 左侧导航面板
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BorderLayout());
        navPanel.setPreferredSize(new Dimension(250, getHeight()));
        navPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

        // 新对话按钮
        JButton newConversationButton = new JButton("Niewe");
        newConversationButton.setFont(new Font("Arial", Font.PLAIN, 16));
        newConversationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewConversation();
            }
        });
        navPanel.add(newConversationButton, BorderLayout.NORTH);

        // 搜索历史列表
        DefaultListModel<String> searchHistoryModel = new DefaultListModel<>();
        JList<String> searchHistoryList = new JList<>(searchHistoryModel);
        searchHistoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchHistoryList.setFixedCellHeight(50);
        searchHistoryList.setFont(new Font("Arial", Font.PLAIN, 16));
        searchHistoryList.setBackground(new Color(245, 245, 245));

        JScrollPane navScrollPane = new JScrollPane(searchHistoryList);
        navPanel.add(navScrollPane, BorderLayout.CENTER);

        contentPane.add(navPanel, BorderLayout.WEST);

        // 右侧主内容面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JTextArea mainContentArea = new JTextArea();
        mainContentArea.setLineWrap(true);
        mainContentArea.setWrapStyleWord(true);
        mainContentArea.setMargin(new Insets(10, 10, 10, 10));
        mainContentArea.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(mainContentArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(mainPanel, BorderLayout.CENTER);

        // 底部输入面板
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.PLAIN, 16));
        sendButton.setBackground(new Color(245, 245, 245));
        sendButton.setForeground(Color.WHITE);

        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        // 发送按钮监听事件
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText().trim();
                if (!inputText.isEmpty()) {
                    searchHistoryModel.addElement(inputText);
                    mainContentArea.append("User: " + inputText + "\n");
                    inputField.setText("");
                }
            }
        });

        // 搜索历史选择监听事件
        searchHistoryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedValue = searchHistoryList.getSelectedValue();
                if (selectedValue != null) {
                    mainContentArea.append("Selected: " + selectedValue + "\n");
                }
            }
        });

        mainFrame.setVisible(true);
    }

    private void startNewConversation() {
        // 实现新对话功能
        JOptionPane.showMessageDialog(this, "New conversation started!");
    }


}
