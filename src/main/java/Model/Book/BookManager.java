package Model.Book;

import jakarta.persistence.EntityManager;

import java.util.List;

public class BookManager {

    private EntityManager entityManager;
    public BookManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Book findExistingBook(Book book) {
        Book existingBook = entityManager.createQuery(
                        "SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
                .setParameter("isbn", book.getIsbn())
                .getResultStream()
                .findFirst()
                .orElse(null);
        return existingBook;
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

    public void updateBook(Book book) {
        Book existingBook = findExistingBook(book);
        if (existingBook == null) return;

        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPublisher(book.getPublisher());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPublicationYear(book.getPublicationYear());

        entityManager.getTransaction().begin();
        entityManager.merge(existingBook);
        entityManager.getTransaction().commit();

    }

    public void deleteBook(String id) {
        entityManager.getTransaction().begin();

        entityManager.createQuery(
                        "DELETE FROM Borrowings b WHERE b.copy.book.isbn = :isbn")
                .setParameter("isbn", id)
                .executeUpdate();

        entityManager.createQuery(
                        "DELETE FROM Copies c WHERE c.book.isbn = :isbn")
                .setParameter("isbn", id)
                .executeUpdate();

        Book book = entityManager
                .createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
                .setParameter("isbn", id)
                .getSingleResult();
        entityManager.remove(book);

        entityManager.getTransaction().commit();
    }


    public List<Book> getAllBooks() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }
}
