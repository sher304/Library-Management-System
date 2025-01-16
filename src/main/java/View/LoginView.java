package View;

import Controller.UserController;
import Model.User.UserObserver;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LoginView extends JFrame implements UserObserver {

    private UserController userController;
    private static JLabel loginLabel = new JLabel();
    public LoginView(UserController userController)  {
        this.userController = userController;
        userController.addUserObservers(this);
        drawWindow();
    }

    public void drawWindow() {
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
        loginLabel.setText("Enter email");
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

        signInButton.addActionListener(e -> {
            userController.singIn(loginTextField.getText(), passwordTextField.getPassword());
        });

        mainParentPanel.add(loginPanel, BorderLayout.NORTH);
        mainParentPanel.add(passwordPanel, BorderLayout.CENTER);
        mainParentPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainParentPanel);
    }

    @Override
    public void loginStatus(String message) {
        if (message.equals("Login")) this.setVisible(false);
        else if (message.equals("Log out")) this.setVisible(true);
        loginLabel.setText("Wrong password or email!");
        System.out.println(message);
    }
}
