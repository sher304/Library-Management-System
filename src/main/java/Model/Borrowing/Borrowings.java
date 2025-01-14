package Model.Borrowing;

import Model.Copy.Copies;
import Model.User.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Borrowings")
public class Borrowings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "copyId", nullable = false)
    private Copies copy;

    @Column(nullable = false)
    private Date borrowDate;

    @Column
    private Date returnDate;
}
