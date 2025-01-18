package Model.Book;

import Model.Publisher.PublisherManager;
import Model.Publisher.Publishers;
import Model.User.User;
import Model.User.UserManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private EntityManager entityManager;
    private BookManager bookManager;

    @BeforeEach
    public void setUp() {
        entityManager = Persistence.createEntityManagerFactory("LibraryManagementPU").createEntityManager();
        bookManager = new BookManager(entityManager);
    }
    @Test
    void testAddBook() {
        Publishers publisher = new Publishers();
        publisher.setName("Addison-Wesley");
        publisher.setAddress("we");
        publisher.setPhoneNumber("12312");

        entityManager.getTransaction().begin();
        entityManager.persist(publisher);
        entityManager.getTransaction().commit();

        Book book = new Book();
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
        book.setIsbn("testest6");
        book.setPublicationYear(2018);
        book.setPublisher(publisher);

        bookManager.saveBook(book, 3);

        Book retrievedBook = bookManager.findBookByIsbn("testest6");

        assertNotNull(retrievedBook, "Book should be saved in the database");
        assertEquals("Effective Java", retrievedBook.getTitle(), "Book title should match");
        assertEquals("Joshua Bloch", retrievedBook.getAuthor(), "Book author should match");
        assertEquals("testest6", retrievedBook.getIsbn(), "Book ISBN should match");
        assertEquals(2018, retrievedBook.getPublicationYear(), "Book publication year should match");
        assertEquals("Addison-Wesley", retrievedBook.getPublisher().getName(), "Book publisher should match");
    }

    @AfterEach
    public void tearDown() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Borrowings b WHERE b.copy IN (SELECT c FROM Copies c)")
                .executeUpdate();
        entityManager.createQuery("DELETE FROM Copies").executeUpdate();
        entityManager.createQuery("DELETE FROM Book").executeUpdate();
        entityManager.createQuery("DELETE FROM User").executeUpdate();
        entityManager.createQuery("DELETE FROM Publishers").executeUpdate();
        entityManager.getTransaction().commit();
    }
}