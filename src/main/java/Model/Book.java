package Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @ManyToOne
    @JoinColumn(name = "publisherId", nullable = false)
    private Publishers publisher;
    @Column(nullable = false)
    private int publicationYear;
    @Column(nullable = false, unique = true)
    private String isbn;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Copies> copies;
}
