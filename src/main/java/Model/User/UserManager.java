package Model.User;

import jakarta.persistence.EntityManager;

public class UserManager {

    private EntityManager entityManager;
    public UserManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User signIn(String email, String password) {
        User user = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
        return user;
    }

    public User getUser(String email) {
        return entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
