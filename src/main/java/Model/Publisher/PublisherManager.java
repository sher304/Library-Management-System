package Model.Publisher;

import jakarta.persistence.EntityManager;

public class PublisherManager {
    private EntityManager entityManager;
    public PublisherManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Publishers findPublisherById(int id) {
        return entityManager.find(Publishers.class, id);
    }


    public void savePublisher(Publishers publisher) {
        entityManager.getTransaction().begin();
        entityManager.persist(publisher);
        entityManager.getTransaction().commit();
    }

}
