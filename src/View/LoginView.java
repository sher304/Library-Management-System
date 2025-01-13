package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LoginView extends JFrame {


    public LoginView() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        JPanel mainParentPanel = new JPanel();
        mainParentPanel.setLayout(new GridLayout(3, 1));
        mainParentPanel.setPreferredSize(new Dimension(300, 130));

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(2 , 1));
        JLabel loginLabel = new JLabel("Enter login:");
        JTextField loginTextField = new JTextField("");;
        loginPanel.add(loginLabel, BorderLayout.NORTH);
        loginPanel.add(loginTextField);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new GridLayout(2 , 1));
        JLabel passwordLabel = new JLabel("Enter password:");
        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setEchoChar('*');
        passwordTextField.setPreferredSize(new Dimension(200, 30));
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordTextField);

        JButton signInButton = new JButton("Login");
        JButton createAccount = new JButton("Create Account");
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(signInButton, BorderLayout.SOUTH);
        buttonsPanel.add(createAccount, BorderLayout.NORTH);

        mainParentPanel.add(loginPanel, BorderLayout.NORTH);
        mainParentPanel.add(passwordPanel, BorderLayout.CENTER);
        mainParentPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainParentPanel);
    }
}
