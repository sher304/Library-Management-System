package Controller;

import Model.Book.Book;
import Model.Book.BookManager;
import Model.Book.BookObserver;
import Model.Publisher.PublisherManager;
import Model.Publisher.Publishers;
import Model.User.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookController {

    private BookManager bookManager;
    private PublisherManager publisherManager;
    private List<BookObserver> bookObservers = new ArrayList<>();

    public BookController(BookManager bookManager, PublisherManager publisherManager) {
        this.bookManager = bookManager;
        this.publisherManager = publisherManager;
    }
    public void findBookById(String id) {

    }
    public void addBookObservers(BookObserver bookObserver){
        bookObservers.add(bookObserver);
    }

    private void notifyBookObservers(String message) {
        for(BookObserver bookObserver: bookObservers) {
            bookObserver.updateBookStatus(message);
        }
    }
    private void notifyBookLoadObservers(List<Book> books) {
        for (BookObserver observer : bookObservers) {
            observer.onBooksLoaded(books);
        }
    }
    public void addBook(String title, String id, String author,
                        String publisherId,
                        int copyAmount,
                        int publicationYear) {
        Publishers publisher = publisherManager.findPublisherById(Integer.parseInt(publisherId));
        if (publisher == null) {
            notifyBookObservers("Publisher is not founded: " + publisherId);
            return;
        }

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setIsbn(id);
        newBook.setAuthor(author);
        newBook.setPublisher(publisher);
        newBook.setPublicationYear(publicationYear);

        if (bookManager.doesBookExists(id)) {
            notifyBookObservers("Book already exists! " + title);
        } else {
            bookManager.saveBook(newBook, copyAmount);
            notifyBookObservers("Book added! " + title);
        }
        getBooks();
    }

    public void deleteBook(String bookId) {
        if (bookManager.doesBookExists(bookId)) {
            bookManager.deleteBook(bookId);
        }
        getBooks();
    }


    public void updateBook(String title, String id, String author, String publisher, int publicationDate) {
        if (!bookManager.doesBookExists(id)) {
            notifyBookObservers("There is no any book with id: " + id);
            return;
        };

        Publishers publishers = publisherManager.findPublisherById(Integer.parseInt(publisher));

        if (publisher == null) {
            notifyBookObservers("Publisher is not founded: " + publisher);
            return;
        }

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setIsbn(id);
        newBook.setAuthor(author);
        newBook.setPublisher(publishers);
        newBook.setPublicationYear(publicationDate);

        notifyBookObservers("Updated book: " + title);
        bookManager.updateBook(newBook);
        getBooks();
    }

    public void getBooks() {
        List<Book> books = bookManager.getAllBooks();
        notifyBookLoadObservers(books);
    }

    public void borrowBook(User user, String isbn) {
        if (bookManager.borrowBook(user, isbn)) notifyBookObservers("Book has been taken");
        else notifyBookObservers("Can not take this book...");
    }

    public void returnBook(User user, String isbn) {
        if (bookManager.returnBook(user, isbn)) notifyBookObservers("Book has been returned!");
        else notifyBookObservers("Book can't be founded");
    }

    public void getBorrowedBooks(User user) {
        List<Book> borrowedBooks = bookManager.getBorrowedBooks(user);

        if (borrowedBooks.isEmpty()) {
            notifyBookObservers("No books currently borrowed.");
        } else {
            notifyBookLoadObservers(borrowedBooks);
        }
    }

    public void getHistoryBooks(User user) {
        List<Book> borrowedBooks = bookManager.getHistoryBooks(user);
        if (borrowedBooks.isEmpty()) {
            notifyBookObservers("No books currently borrowed.");
        } else {
            notifyBookLoadObservers(borrowedBooks);
        }
    }
}
