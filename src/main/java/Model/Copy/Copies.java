package Model.Copy;

import Model.Book.Book;
import jakarta.persistence.*;

@Entity
@Table(name = "Copies")
public class Copies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    private Book book;
    @Column(nullable = false)
    private int copyNumber;
    @Column(nullable = false)
    private String status;
    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(int copyNumber) {
        this.copyNumber = copyNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
