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
}
