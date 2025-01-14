import View.DashboardView;
import View.LoginView;
import javax.swing.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> runApplication());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryManagementPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        // You can add sample data here for testing
        em.getTransaction().commit();

        em.close();
        emf.close();
    }

    private static void runApplication() {
//        LoginView loginView = new LoginView();
        DashboardView loginView = new DashboardView();
        loginView.setVisible(true);
    }
}
