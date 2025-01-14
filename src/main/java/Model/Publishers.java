package Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Publishers")
public class Publishers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private List<Book> books;
}
