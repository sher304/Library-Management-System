package Controller;

import Model.Book.Book;
import Model.Book.BookManager;

import java.util.Date;

public class BookController {

    private BookManager bookManager;

    public BookController(BookManager bookManager) {

    }

    public void addBook(String title, String id, String author, String publisher, String publicationDate) {
        System.out.println("Book title: " + title);
        System.out.println("Book id: " + id);
        System.out.println("Book author: " + author);
        System.out.println("Book publisher: " + publisher);
        System.out.println("Book publication date: " + publicationDate);
    }
}
