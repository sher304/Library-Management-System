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

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> runApplication());
    }

    private static void runApplication() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryManagementPU");
        EntityManager entityManager = emf.createEntityManager();

        UserManager userManager = new UserManager(entityManager);
        UserController userController = new UserController(userManager);
        LoginView loginView = new LoginView(userController);
        loginView.setVisible(true);

//        BookManager bookService = new BookManager(entityManager);
//        PublisherManager publisherService = new PublisherManager(entityManager);
//        BookController bookController = new BookController(bookService, publisherService);
//
//        DashboardView dashboardView = new DashboardView(bookController);
//        dashboardView.setVisible(true);
    }
}
