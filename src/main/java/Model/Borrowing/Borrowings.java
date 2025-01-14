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

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Copies getCopy() {
        return copy;
    }

    public void setCopy(Copies copy) {
        this.copy = copy;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
