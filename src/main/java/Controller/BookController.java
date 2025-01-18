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
            notifyBookObservers("Book already exists " + title);
        } else {
            bookManager.saveBook(newBook, copyAmount);
            notifyBookObservers("Book added " + title);
        }
        getBooks();
    }

    public void deleteBook(String bookId) {
        if (bookManager.doesBookExists(bookId)) {
            bookManager.deleteBook(bookId);
        }
        getBooks();
    }

    public void updateBook(String title, String id, String author, String publisher, int publicationYear, int copyAmount) {

        Book existingBook = bookManager.findBookByIsbn(id);
        if (existingBook == null) {
            notifyBookObservers("There is no book with ISBN: " + id);
            return;
        }

        if (title != null && !title.trim().isEmpty()) {
            existingBook.setTitle(title);
        }
        if (author != null && !author.trim().isEmpty()) {
            existingBook.setAuthor(author);
        }
        if (publisher != null && !publisher.trim().isEmpty()) {
            try {
                Publishers publishers = publisherManager.findPublisherById(Integer.parseInt(publisher));
                if (publishers == null) {
                    notifyBookObservers("Publisher not found: " + publisher);
                    return;
                }
                existingBook.setPublisher(publishers);
            } catch (NumberFormatException e) {
                notifyBookObservers("Invalid publisher ID: " + publisher);
                return;
            }
        }
        if (publicationYear > 0) {
            existingBook.setPublicationYear(publicationYear);
        }

        bookManager.updateBook(existingBook, copyAmount);
        notifyBookObservers("Updated book: " + existingBook.getTitle());
        getBooks();
    }



    public void getBooks() {
        List<Book> books = bookManager.getAllBooks();
        notifyBookLoadObservers(books);
    }

    public void borrowBook(User user, String isbn) {
        if (bookManager.borrowBook(user, isbn)) notifyBookObservers("Book has been taken");
        else notifyBookObservers("Cannot borrow the book. Limit reached or no available copies");
    }

    public void returnBook(User user, String isbn) {
        if (bookManager.returnBook(user, isbn)) notifyBookObservers("Book has been returned!");
        else notifyBookObservers("No available borrowed copies of the book");
    }

    public void getBorrowedBooks(User user) {
        List<Book> borrowedBooks = bookManager.getBorrowedBooks(user);

        if (borrowedBooks.isEmpty()) notifyBookObservers("No books currently borrowed");
        notifyBookLoadObservers(borrowedBooks);
    }

    public void getHistoryBooks(User user) {
        List<Book> borrowedBooks = bookManager.getHistoryBooks(user);
        if (borrowedBooks.isEmpty()) notifyBookObservers("No books currently borrowed");
        notifyBookLoadObservers(borrowedBooks);
    }


    public void fillBooksData() {

        Book book3 = new Book();
        book3.setTitle("China Town");
        book3.setIsbn("2623");
        book3.setAuthor("Homer Orwell");
        int publisherId = publisherManager.getAllPublishers().get(0).getId();
        book3.setPublisher(publisherManager.findPublisherById(publisherId));
        book3.setPublicationYear(1929);
        bookManager.saveBook(book3, 1);

        Book book4 = new Book();
        book4.setTitle("Warriors");
        book4.setIsbn("2213");
        book4.setAuthor("Da Ci Ci");
        book4.setPublisher(publisherManager.findPublisherById(publisherId));
        book4.setPublicationYear(1200);
        bookManager.saveBook(book4, 1);

        Book book5 = new Book();
        book5.setTitle("Tesla Corporations");
        book5.setIsbn("0132");
        book5.setAuthor("Elon Musk");
        book5.setPublisher(publisherManager.findPublisherById(publisherId));
        book5.setPublicationYear(2021);
        bookManager.saveBook(book5, 1);

        Book book6 = new Book();
        book6.setTitle("Dragon Year");
        book6.setIsbn("9281");
        book6.setAuthor("Trump");
        book6.setPublisher(publisherManager.findPublisherById(publisherId));
        book6.setPublicationYear(2012);
        bookManager.saveBook(book6, 10);

        notifyBookObservers("Sample book data added.");
    }

}
