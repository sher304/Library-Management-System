import Controller.BookController;
import Model.Book.BookManager;
import View.DashboardView;
import View.LoginView;
import javax.swing.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> runApplication());
    }

    private static void runApplication() {
        // Create the EntityManagerFactory and EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryManagementPU");
        EntityManager entityManager = emf.createEntityManager();

        // Initialize the service and controller
        BookManager bookService = new BookManager(entityManager);
        BookController bookController = new BookController(bookService);

        // Pass the controller to the view
        DashboardView dashboardView = new DashboardView(bookController);
        dashboardView.setVisible(true);

        //
////        LoginView loginView = new LoginView();
//        DashboardView loginView = new DashboardView();
    }
}
