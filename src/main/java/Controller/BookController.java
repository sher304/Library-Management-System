package Controller;

import Model.Book.Book;
import Model.Book.BookManager;
import Model.Book.BookObserver;
import Model.Publisher.PublisherManager;
import Model.Publisher.Publishers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookController {

    private BookManager bookManager;
    private PublisherManager publisherManager;
    private List<BookObserver> bookObservers = new ArrayList<>();

    public BookController(BookManager bookManager, PublisherManager publisherManager) {
        this.bookManager = bookManager;
        this.publisherManager = publisherManager;
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
    public void addBook(String title, String id, String author, String publisherId, int publicationYear) {
        if (bookManager.doesBookExists(id)) {
            notifyBookObservers("Book already exists! " + title);
            return;
        }

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

        bookManager.saveBook(newBook);
        notifyBookObservers("Book added! " + title);
    }


    public void updateBook(String title, String id, String author, String publisher, String publicationDate) {
        System.out.println("Update book: ");
        System.out.println("Book title: " + title);
        System.out.println("Book id: " + id);
        System.out.println("Book author: " + author);
        System.out.println("Book publisher: " + publisher);
        System.out.println("Book publication date: " + publicationDate);
    }

    public void getBooks() {
        List<Book> books = bookManager.getAllBooks();
        notifyBookLoadObservers(books);
    }
}
