package Model.Librarian;

import Model.User.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LibrariansTest {

    private LibrarianManager librarianManager;
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        entityManager = Persistence.createEntityManagerFactory("LibraryManagementPU").createEntityManager();
        librarianManager = new LibrarianManager(entityManager);
    }

    @Test
    void addLibrarian() {
        User user = new User();
        user.setName("Librarian User");
        user.setEmail("librarian_test@test.com");
        user.setPassword("password");
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        Librarians librarian = new Librarians();
        librarian.setUser(user);
        librarian.setPosition("Head Librarian");
        librarian.setEmploymentDate(new Date());
        librarian.setPassword("passw");

        boolean result = librarianManager.addLibrarian(librarian);
        assertTrue(result, "Librarian should be added successfully");

        Librarians fetchedLibrarian = librarianManager.findLibrarianById(librarian.getId());
        assertNotNull(fetchedLibrarian, "Librarian should be retrievable");
        assertEquals("Head Librarian", fetchedLibrarian.getPosition());
    }

    @Test
    void deleteLibrarian() {
        User user = new User();
        user.setName("To Be Deleted Librarian");
        user.setEmail("delete_librarian@test.com");
        user.setPassword("password");
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        Librarians librarian = new Librarians();
        librarian.setUser(user);
        librarian.setPosition("Librarian");
        librarian.setEmploymentDate(new Date());
        librarian.setPassword("passw");
        librarianManager.addLibrarian(librarian);

        boolean result = librarianManager.deleteLibrarian(librarian.getId());
        assertTrue(result, "Librarian should be deleted successfully");

        Librarians deletedLibrarian = librarianManager.findLibrarianById(librarian.getId());
        assertNull(deletedLibrarian, "Deleted librarian should no longer exist");
    }

    @AfterEach
    public void tearDown() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Librarians").executeUpdate();
        entityManager.createQuery("DELETE FROM User").executeUpdate();
        entityManager.getTransaction().commit();
    }
}