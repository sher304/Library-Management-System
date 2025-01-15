package Controller;

import Model.Book.BookManager;
import Model.Book.BookObserver;
import Model.Publisher.PublisherManager;
import Model.User.User;
import Model.User.UserManager;
import Model.User.UserObserver;
import View.DashboardView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserController {

    UserManager userManager;
    List<UserObserver> userObservers = new ArrayList<>();
    User user;
    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    public void notifyUserObservers(String message) {
        for(UserObserver userObserver: userObservers) {
            userObserver.loginStatus(message);
        }
    }
    public void addUserObservers(UserObserver userObserver) {
        userObservers.add(userObserver);
    }

    public void singIn(String email, char[] password) {
        String passwordV = new String(password);

        User user = userManager.signIn(email, passwordV);

        if (user == null) notifyUserObservers("Wrong password or email");
        else {
            this.user = user;
            notifyUserObservers("Login");
            runDashboard();
        };
    }

    private void runDashboard() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryManagementPU");
        EntityManager entityManager = emf.createEntityManager();

        BookManager bookService = new BookManager(entityManager);
        PublisherManager publisherService = new PublisherManager(entityManager);
        BookController bookController = new BookController(bookService, publisherService);

        DashboardView dashboardView = new DashboardView(bookController, this);
        dashboardView.setVisible(true);
    }
}
