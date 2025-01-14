package Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Librarians")
public class Librarians {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private Date employmentDate;
    @Column(nullable = false)
    private String position;
}
