package Model.Librarian;

import Model.User.User;
import jakarta.persistence.EntityManager;

import java.util.Date;
import java.util.List;
/**
 * Manages Librarian-related operations such as saving, updating, deleting, books.
 */
public class LibrarianManager {

    EntityManager entityManager;

    /**
     * Constructor of the Librarian Manager
     *
     * @param entityManager implements the entity manager
     */
    public LibrarianManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Add a new Librarian function
     *
     * @param librarian accepts from the controller the librarian and
     *                  via entityManager saved new Librarian
     *                  @return boolean statement, in case of success or failure
     */
    public boolean addLibrarian(Librarians librarian) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(l) FROM Librarians l WHERE l.user.id = :userId", Long.class)
                .setParameter("userId", librarian.getUser().getId())
                .getSingleResult();

        if (count > 0) {
            System.out.println("Librarian with userId " + librarian.getUser().getId() + " already exists.");
            return false;
        }

        entityManager.getTransaction().begin();
        entityManager.persist(librarian);
        entityManager.getTransaction().commit();
        return true;
    }

    /**
     * Find a librarian by id
     *
     * @param librarianId accept the librarian id
     *
     *                  @return Librarian entity
     */
    public Librarians findLibrarianById(int librarianId) {
        return entityManager.find(Librarians.class, librarianId);
    }

    /**
     * Find a User by id
     *
     * @param userId accept the user id, to check for possibility to add a new librarian
     *
     *                  @return User entity
     */
    public User findUserById(int userId) {
        return entityManager.find(User.class, userId);
    }
    /**
     * Update a Librarian by id
     *
     * @param librarian accept the librarian class
     *
     *                  @return boolean, in case of success, or failure
     */
    public boolean updateLibrarian(Librarians librarian) {
        Librarians existingLibrarian = findLibrarianById(librarian.getId());
        if (existingLibrarian == null) return false;

        existingLibrarian.setPosition(librarian.getPosition());
        existingLibrarian.setEmploymentDate(librarian.getEmploymentDate());
        existingLibrarian.setPassword(librarian.getPassword());

        entityManager.getTransaction().begin();
        entityManager.merge(existingLibrarian);
        entityManager.getTransaction().commit();
        return  true;
    }

    /**
     * Delete Librarian by id
     *
     * @param librarianId accept the librarian id, to deleting
     *
     *                  @return boolean, in case of success, or failure
     */
    public boolean deleteLibrarian(int librarianId) {
        Librarians librarian = findLibrarianById(librarianId);
        if (librarian == null) return false;

        entityManager.getTransaction().begin();
        entityManager.remove(librarian);
        entityManager.getTransaction().commit();
        return true;
    }
    /**
     * Get all Librarians
     *
     * @return boolean, in case of success, or failure
     */
    public List<Librarians> getAllLibrarians() {
        entityManager.clear();
        return entityManager.createQuery("SELECT l FROM Librarians l", Librarians.class).getResultList();
    }

    public List<User> getAllUsers() {
        entityManager.clear();
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

}
