package Model.Book;

import Model.Borrowing.Borrowings;
import Model.Copy.Copies;
import Model.User.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;

import java.util.*;

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

    public Book findBookByIsbn(String isbn) {
        Book existingBook = entityManager.createQuery(
                        "SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
                .setParameter("isbn", isbn)
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

    public void saveBook(Book book, int copyAmount) {
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        for (int i = 0; i < copyAmount; i++) {
            Copies copy = new Copies();
            copy.setBook(book);
            copy.setStatus("Available");
            copy.setCopyNumber(i + 1);
            entityManager.persist(copy);
        }
        entityManager.getTransaction().commit();
    }

    public void updateBook(Book book, int newCopyAmount) {
        entityManager.getTransaction().begin();
        Book existingBook = findExistingBook(book);
        if (existingBook == null) {
            System.out.println("Book not found.");
            entityManager.getTransaction().rollback();
            return;
        }
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPublisher(book.getPublisher());
        existingBook.setPublicationYear(book.getPublicationYear());
        existingBook.setIsbn(book.getIsbn());

        List<Copies> existingCopies = entityManager.createQuery(
                        "SELECT c FROM Copies c WHERE c.book = :book", Copies.class)
                .setParameter("book", existingBook)
                .getResultList();
        int currentCopyCount = existingCopies.size();
        if (newCopyAmount < currentCopyCount) {
            for (int i = newCopyAmount; i < currentCopyCount; i++) {
                Copies copyToDelete = existingCopies.get(i);
                long count = entityManager.createQuery(
                                "SELECT COUNT(b) FROM Borrowings b WHERE b.copy = :copy", Long.class)
                        .setParameter("copy", copyToDelete)
                        .getSingleResult();
                if (count == 0) {
                    entityManager.remove(copyToDelete);
                } else {
                    System.out.println("Cannot delete copy with active borrowings.");
                }
            }
        } else if (newCopyAmount > currentCopyCount) {
            for (int i = currentCopyCount; i < newCopyAmount; i++) {
                Copies newCopy = new Copies();
                newCopy.setBook(existingBook);
                newCopy.setCopyNumber(i + 1);
                newCopy.setStatus("Available");
                entityManager.persist(newCopy);
            }
        }
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
        entityManager.clear();
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public boolean borrowBook(User user, String isbn) {
        int borrow_limiter = 5;

        System.out.println("TRYY WILL STRAT");
        entityManager.getTransaction().begin();
        try {
            long activeBorrowingsCount = entityManager.createQuery(
                            "SELECT COUNT(b) FROM Borrowings b WHERE b.user = :user AND b.returnDate IS NULL", Long.class)
                    .setParameter("user", user)
                    .getSingleResult();

            if (activeBorrowingsCount >= borrow_limiter) {
                System.out.println("Borrowing limit reached! You cannot borrow more than " + borrow_limiter + " books.");
                entityManager.getTransaction().rollback();
                return false;
            }

            System.out.println("TRYYY STARTED");
            System.out.println("ID: " + isbn);
            Copies availableCopy = entityManager.createQuery(
                            "SELECT c FROM Copies c JOIN c.book b WHERE b.isbn = :isbn AND c.status = 'Available'", Copies.class)
                    .setParameter("isbn", isbn)
                    .setMaxResults(1)
                    .getSingleResult();
            System.out.println("COPY ID!!!: " + availableCopy.getId());
            if (availableCopy != null) {
                System.out.println("IS NOT NULL!");

                availableCopy.setStatus("Borrowed");

                Borrowings borrowing = new Borrowings();
                borrowing.setCopy(availableCopy);
                borrowing.setUser(user);
                borrowing.setBorrowDate(new Date());
                borrowing.setReturnDate(null);

                entityManager.persist(borrowing);
                entityManager.getTransaction().commit();
                System.out.println("SETTED!\n");
                return true;
            }
        } catch (NoResultException e) {
            System.out.println("MESSAGE: " + e.getMessage());
            return  false;
        }
        System.out.println("FALSE CAESE: ");
        return false;
    }


    public boolean returnBook(User user, String isbn) {
        entityManager.getTransaction().begin();
        Book book = entityManager.createQuery(
                        "SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
                .setParameter("isbn", isbn)
                .getSingleResult();

        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }

        Copies copy = entityManager.createQuery(
                        "SELECT c FROM Copies c WHERE c.book = :book AND EXISTS (" +
                                "SELECT 1 FROM Borrowings br WHERE br.copy = c AND br.returnDate IS NULL)", Copies.class)
                .setParameter("book", book)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (copy == null) {
            System.out.println("No available borrowed copies of the book.");
            return false;
        }

        Borrowings borrowing = entityManager.createQuery(
                        "SELECT br FROM Borrowings br WHERE br.user = :user AND br.copy = :copy AND br.returnDate IS NULL", Borrowings.class)
                .setParameter("user", user)
                .setParameter("copy", copy)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (borrowing == null) {
            System.out.println("No active borrowing record found for this book and user.");
            return false;
        }

        borrowing.setReturnDate(new Date());
        entityManager.merge(borrowing);

        entityManager.getTransaction().commit();
        System.out.println("Book returned successfully.");
        return true;
    }


    public List<Book> getBorrowedBooks(User user) {
        entityManager.clear();
        List<Book> currentBooks = entityManager.createQuery(
                        "SELECT br.copy.book FROM Borrowings br WHERE br.user = :user AND br.returnDate IS NULL", Book.class)
                .setParameter("user", user)
                .getResultList();

        return currentBooks;
    }

    public List<Book> getHistoryBooks(User user) {
        entityManager.clear();
        return entityManager.createQuery(
                        "SELECT br.copy.book FROM Borrowings br WHERE br.user = :user", Book.class)
                .setParameter("user", user)
                .getResultList();
    }
}
