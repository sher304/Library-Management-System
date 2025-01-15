package Model.Book;

import jakarta.persistence.EntityManager;

import java.util.List;

public class BookManager {

    private EntityManager entityManager;
    public BookManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean doesBookExists(String id) {
        return entityManager
                .createQuery("SELECT COUNT(b) FROM Book b WHERE b.isbn = :isbn", Long.class)
                .setParameter("isbn", id)
                .getSingleResult() > 0;
    }

    public void saveBook(Book book) {
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
    }

    public List<Book> getAllBooks() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }
}
