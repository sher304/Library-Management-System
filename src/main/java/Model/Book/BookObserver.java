package Model.Book;

import java.util.List;

public interface BookObserver {

    void updateBookStatus(String message);
    void onBooksLoaded(List<Book> books);

}
