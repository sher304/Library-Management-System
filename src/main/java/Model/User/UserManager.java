package Model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

/**
 * User Manages user-related operations such as saving, updating, deleting,
 */
public class UserManager {

    private EntityManager entityManager;
    /**
     * User Managers constructor
     * @param entityManager accepts Entiti Manager class
     */
    public UserManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Sign in feature
     * @param email get an email fo the User
     * @param password get a password of the user
     *                 and send to the db.
     * @return In case of the success returns the User class.
     */
    public User signIn(String email, String password) {
        try {
            return entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Get User
     * @param email get an email fo the User, due to its unique
     * @return In case of the success returns the User class.
     */
    public User getUser(String email) {
        try {
            return entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Validate to Is Librarian field
     * @param user get a User instance
     * @return boolean case, if success, or false
     */
    public boolean isLibrarian(User user) {
        return entityManager.createQuery(
                        "SELECT COUNT(l) FROM Librarians l WHERE l.user.id = :userId", Long.class)
                .setParameter("userId", user.getId())
                .getSingleResult() > 0;
    }

    /**
     * Create user feature
     * @param user get a User instance
     * @return boolean case, if success, or false
     */
    public boolean addUser(User user) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", user.getEmail())
                .getSingleResult();

        if (count > 0) {
            System.out.println("User with email " + user.getEmail() + " already exists.");
            return false;
        }

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        return true;

    }

    /**
     * Create user feature
     * @param userId get a Userid, to able to find him
     * @return User class returned, if there is option to find
     */
    public User findUserById(int userId) {
        return entityManager.find(User.class, userId);
    }

    /**
     * Update user feature
     * @param user get a user
     * @return boolean case in case of success
     */
    public boolean updateUser(User user) {
        User existingUser = findUserById(user.getId());
        if (existingUser == null) return false;

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setAddress(user.getAddress());
        existingUser.setPassword(user.getPassword());

        entityManager.getTransaction().begin();
        entityManager.merge(existingUser);
        entityManager.getTransaction().commit();
        return true;
    }

    /**
     * Delete user feature
     * @param userId get user id
     * @return boolean case in case of success
     */
    public boolean deleteUser(int userId) {
        User user = findUserById(userId);
        if (user == null) return false;

        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
        return true;
    }

    /**
     * Show all users in db
     * @return Give us List<User> instance
     */
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
