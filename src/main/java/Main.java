import Controller.BookController;
import Controller.UserController;
import Model.Book.BookManager;
import Model.Publisher.PublisherManager;
import Model.User.UserManager;
import View.DashboardView;
import javax.swing.*;

import View.LoginView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Main class to launch an application
 * Also implement the 'Builder'
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> runApplication());
    }

    /**
     *
     * Builder pattern
     */
    private static void runApplication() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryManagementPU");
        EntityManager entityManager = emf.createEntityManager();

        UserManager userManager = new UserManager(entityManager);
        UserController userController = new UserController(userManager);
        LoginView loginView = new LoginView(userController);
        loginView.setVisible(true);

        userController.fillUsersData();
//        Fill data when it needs
    }
}
