import Controller.BookController;
import Model.Book.Book;
import Model.Book.BookManager;
import Model.Borrowing.Borrowings;
import Model.Copy.Copies;
import Model.Librarian.Librarians;
import Model.Publisher.Publishers;
import Model.User.User;
import View.DashboardView;
import View.LoginView;
import javax.swing.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> runApplication());
    }

    private static void runApplication() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryManagementPU");
        EntityManager entityManager = emf.createEntityManager();

        BookManager bookService = new BookManager(entityManager);
        BookController bookController = new BookController(bookService);

        DashboardView dashboardView = new DashboardView(bookController);
        dashboardView.setVisible(true);
    }


//    public static void createDatas() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryManagementPU");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//
//
//        Publishers publisher1 = new Publishers();
//        publisher1.setName("Pearson");
//        publisher1.setAddress("221B Baker Street, London, UK");
//        publisher1.setPhoneNumber("+44 20 7946 0958");
//
//        Publishers publisher2 = new Publishers();
//        publisher2.setName("Penguin Random House");
//        publisher2.setAddress("1745 Broadway, New York, USA");
//        publisher2.setPhoneNumber("+1 212-782-9000");
//
//        Book book1 = new Book();
//        book1.setTitle("To Kill a Mockingbird");
//        book1.setAuthor("Harper Lee");
//        book1.setPublisher(publisher1);
//        book1.setPublicationYear(1960);
//        book1.setIsbn("978-0-06-112008-4");
//
//        Book book2 = new Book();
//        book2.setTitle("1984");
//        book2.setAuthor("George Orwell");
//        book2.setPublisher(publisher2);
//        book2.setPublicationYear(1949);
//        book2.setIsbn("978-0-452-28423-4");
//
//        Copies copy1 = new Copies();
//        copy1.setBook(book1);
//        copy1.setCopyNumber(1);
//        copy1.setStatus("Available");
//
//        Copies copy2 = new Copies();
//        copy2.setBook(book1);
//        copy2.setCopyNumber(2);
//        copy2.setStatus("Borrowed");
//
//        Copies copy3 = new Copies();
//        copy3.setBook(book2);
//        copy3.setCopyNumber(1);
//        copy3.setStatus("Available");
//
//        User user1 = new User();
//        user1.setName("John Doe");
//        user1.setEmail("john.doe@example.com");
//        user1.setPhoneNumber("+1 555-1234");
//        user1.setAddress("123 Maple Street, Springfield");
//
//        User user2 = new User();
//        user2.setName("Jane Smith");
//        user2.setEmail("jane.smith@example.com");
//        user2.setPhoneNumber("+1 555-5678");
//        user2.setAddress("456 Oak Avenue, Gotham");
//
//
//        Librarians librarian1 = new Librarians();
//        librarian1.setUser(user1);
//        librarian1.setEmploymentDate(new Date());
//        librarian1.setPosition("Senior Librarian");
//
//        Borrowings borrowing1 = new Borrowings();
//        borrowing1.setUser(user2);
//        borrowing1.setCopy(copy2);
//        borrowing1.setBorrowDate(new Date());
//        borrowing1.setReturnDate(null); // Not returned yet
//
//        em.persist(publisher1);
//        em.persist(publisher2);
//
//        book1.setPublisher(publisher1);
//        book2.setPublisher(publisher2);
//        em.persist(book1);
//        em.persist(book2);
//
//        copy1.setBook(book1);
//        copy2.setBook(book1);
//        copy3.setBook(book2);
//        em.persist(copy1);
//        em.persist(copy2);
//        em.persist(copy3);
//
//
//        em.persist(user1);
//        em.persist(user2);
//
//        librarian1.setUser(user1);
//        em.persist(librarian1);
//
//        borrowing1.setUser(user2);
//        borrowing1.setCopy(copy2);
//        em.persist(borrowing1);
//
//        try {
//            tx.begin();
//
//            // Save entities in the correct order
//            em.persist(publisher1);
//            em.persist(publisher2);
//            em.persist(book1);
//            em.persist(book2);
//            em.persist(copy1);
//            em.persist(copy2);
//            em.persist(copy3);
//            em.persist(user1);
//            em.persist(user2);
//            em.persist(librarian1);
//            em.persist(borrowing1);
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//    }
}
