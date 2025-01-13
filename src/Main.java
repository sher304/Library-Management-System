import View.DashboardView;
import View.LoginView;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> runApplication());
    }

    private static void runApplication() {
//        LoginView loginView = new LoginView();
        DashboardView loginView = new DashboardView();
        loginView.setVisible(true);
    }
}
