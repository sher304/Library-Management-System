package Model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class UserManager {

    private EntityManager entityManager;
    public UserManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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

    public boolean isLibrarian(User user) {
        return entityManager.createQuery(
                        "SELECT COUNT(l) FROM Librarians l WHERE l.user.id = :userId", Long.class)
                .setParameter("userId", user.getId())
                .getSingleResult() > 0;
    }
}
