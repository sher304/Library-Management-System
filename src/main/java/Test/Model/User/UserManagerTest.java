package Model.User;

import Model.Librarian.Librarians;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager userManager;
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        entityManager = Persistence.createEntityManagerFactory("LibraryManagementPU").createEntityManager();
        userManager = new UserManager(entityManager);

    }

    @Test
    void signIn() {
        User user = new User();
        user.setName("Bob");
        user.setEmail("abob@gmail.com");
        user.setPassword("password");
        userManager.addUser(user);

        User signedInUser = userManager.signIn("abob@gmail.com", "password");
        assertNotNull(signedInUser, "User should successfully sign in");
        assertEquals("Bob", signedInUser.getName());
    }

    @Test
    void getUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("user@gmail.com");
        user.setPassword("12345");
        userManager.addUser(user);

        User fetchedUser = userManager.getUser("user@gmail.com");
        assertNotNull(fetchedUser, "User should be fetched successfully");
        assertEquals("Test User", fetchedUser.getName());
    }

    @Test
    void isLibrarian() {
        User user = new User();
        user.setName("Librarian User");
        user.setEmail("librarian@gmail.com");
        user.setPassword("librarian123");
        userManager.addUser(user);

        Librarians librarian = new Librarians();
        librarian.setUser(user);
        librarian.setPosition("Head Librarian");
        librarian.setPassword("securepassword");
        librarian.setEmploymentDate(new Date());
        entityManager.getTransaction().begin();
        entityManager.persist(librarian);
        entityManager.getTransaction().commit();

        boolean result = userManager.isLibrarian(user);
        assertTrue(result, "User should be identified as a librarian");
    }

    @Test
    public void testAddUser() {
        User newUser = new User();
        newUser.setName("Patrik Star");
        newUser.setEmail("testAddUserStar@gmail.com");
        newUser.setPassword("12");

        boolean result = userManager.addUser(newUser);

        assertTrue(result, "User should be added successfully");
        User fetchedUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", "testAddUserStar@gmail.com")
                .getSingleResult();
        assertNotNull(fetchedUser, "User should exist in the database");
        assertEquals("Patrik Star", fetchedUser.getName());
    }

    @Test
    void findUserById() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("findByID@example.com");
        user.setPassword("12345");
        userManager.addUser(user);

        User foundUser = userManager.findUserById(user.getId());
        assertNotNull(foundUser, "User should be found by ID");
        assertEquals("findByID@example.com", foundUser.getEmail());
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setName("Old Name");
        user.setEmail("update@gmail.com");
        user.setPassword("oldpassword");
        userManager.addUser(user);

        user.setName("New Name");
        user.setPassword("newpassword");
        boolean result = userManager.updateUser(user);

        assertTrue(result, "User should be updated successfully");

        User updatedUser = userManager.findUserById(user.getId());
        assertEquals("New Name", updatedUser.getName());
        assertEquals("newpassword", updatedUser.getPassword());
    }

    @Test
    void deleteUser() {
        User user = new User();
        user.setName("To Be Deleted");
        user.setEmail("delete@example.com");
        user.setPassword("deletepassword");
        userManager.addUser(user);

        boolean result = userManager.deleteUser(user.getId());
        assertTrue(result, "User should be deleted successfully");

        User deletedUser = userManager.findUserById(user.getId());
        assertNull(deletedUser, "Deleted user should not exist in the database");
    }

    @Test
    void getAllUsers() {
        User user1 = new User();
        user1.setName("User One");
        user1.setEmail("one@example.com");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setName("User Two");
        user2.setEmail("two@example.com");
        user2.setPassword("password2");

        int before = userManager.getAllUsers().size();
        userManager.addUser(user1);
        userManager.addUser(user2);
        int after = userManager.getAllUsers().size();
        List<User> users = userManager.getAllUsers();
        assertNotNull(users, "Users list should not be null");
        assertEquals(2, after-before, "There should be two users in the list");
    }

    @Test
    public void testAddUser_DuplicateEmail() {
        User user1 = new User();
        user1.setName("Sponge Bob");
        user1.setEmail("bob@gmail.com");
        user1.setPassword("2");
        userManager.addUser(user1);

        User user2 = new User();
        user2.setName("Sandy Chikcs");
        user2.setEmail("bob@gmail.com");
        user2.setPassword("6");

        boolean result = userManager.addUser(user2);

        assertFalse(result, "Adding a user with a duplicate email should fail");
    }

    @AfterEach
    public void tearDown() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Librarians").executeUpdate();
        entityManager.createQuery("DELETE FROM User").executeUpdate();
        entityManager.getTransaction().commit();
    }
}