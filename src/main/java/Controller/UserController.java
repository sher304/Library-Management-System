package Controller;

import Model.Book.BookManager;
import Model.Book.BookObserver;
import Model.Librarian.LibrarianManager;
import Model.Publisher.PublisherManager;
import Model.User.User;
import Model.User.UserManager;
import Model.User.UserObserver;
import View.DashboardView;
import jakarta.persistence.Column;
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
            runDashboard(user);
        };
    }

    private void runDashboard(User user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryManagementPU");
        EntityManager entityManager = emf.createEntityManager();

        BookManager bookService = new BookManager(entityManager);
        PublisherManager publisherService = new PublisherManager(entityManager);
        BookController bookController = new BookController(bookService, publisherService);

        LibrarianManager librarianManager = new LibrarianManager(entityManager);
        LibrarianController librarianController = new LibrarianController(librarianManager);

        PublisherManager publisherManager = new PublisherManager(entityManager);
        PublisherController publisherController = new PublisherController(publisherManager);

        boolean isLibrarian = userManager.isLibrarian(user);
        DashboardView dashboardView = new DashboardView(bookController,
                                    this, user,
                                    isLibrarian,
                                    librarianController,
                                    publisherController
                                    );
        dashboardView.setVisible(true);
    }

    public void signOut() {
        this.user = null;
        notifyUserObservers("Log out");
    }

    public void addNewUser(String name, String email, String password, String phoneNumber, String address) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);

        if (userManager.addUser(user)) notifyUserObservers("User has been added");
        else notifyUserObservers("Can't add user");
    }

    public void updateUser(int id, String name, String email, String phoneNumber, String address) {
        User existingUser = userManager.findUserById(id);
        if (existingUser == null) {
            System.out.println("User not found with ID: " + id);
            return;
        }

        if (name != null && !name.trim().isEmpty()) existingUser.setName(name);
        if (email != null && !email.trim().isEmpty()) existingUser.setEmail(email);
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) existingUser.setPhoneNumber(phoneNumber);
        if (address != null && !address.trim().isEmpty()) existingUser.setAddress(address);


        if (userManager.updateUser(user)) notifyUserObservers("User updated");
        else notifyUserObservers("User can't be updted");
    }

    public void deleteUser(int userId) {
        if (userManager.deleteUser(userId)) notifyUserObservers("User deleted");
        else notifyUserObservers("User can't be deleted");
    }

    public List<User> getAllUsers() {
        return userManager.getAllUsers();
    }

    public void fillUsersData() {
        User user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("");
        user1.setPhoneNumber("+123456789");
        user1.setAddress("123 Main Street, Springfield");

        User user2 = new User();
        user2.setName("Jane Smith");
        user2.setEmail("jane.smith@example.com");
        user2.setPassword("password456");
        user2.setPhoneNumber("+987654321");
        user2.setAddress("456 Oak Avenue, Gotham");

        userManager.addUser(user1);
        userManager.addUser(user2);
    }
}
