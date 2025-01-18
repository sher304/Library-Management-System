package Model.Publisher;

import jakarta.persistence.EntityManager;

import java.util.List;
/**
 * Publisher Manages publisher-related operations such as saving, updating, deleting,
 */
public class PublisherManager {
    private EntityManager entityManager;
    /**
     * Constructor of Publisher Manager.
     * Initialize the Entity Manager
     * @param entityManager
     */
    public PublisherManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Find PublisherBy Id
     * @param id accpets id, to starting the searching the Publisher
     * @return returns Publisher class
     */
    public Publishers findPublisherById(int id) {
        return entityManager.find(Publishers.class, id);
    }

    /**
     * Add Publisher
     * @param publisher accepts publisher class
     */
    public void addPublisher(Publishers publisher) {
        entityManager.getTransaction().begin();
        entityManager.persist(publisher);
        entityManager.getTransaction().commit();
    }
    /**
     * Update Publisher
     * @param publisher accepts publisher class
     * @return boolean in case of success or failure
     */
    public boolean updatePublisher(Publishers publisher) {
        Publishers existingPublisher = findPublisherById(publisher.getId());
        if (existingPublisher == null) {
            System.out.println("Publisher not found.");
            return false;
        }

        existingPublisher.setName(publisher.getName());
        existingPublisher.setAddress(publisher.getAddress());
        existingPublisher.setPhoneNumber(publisher.getPhoneNumber());

        entityManager.getTransaction().begin();
        entityManager.merge(existingPublisher);
        entityManager.getTransaction().commit();
        return true;
    }

    /**
     * Delete Publisher
     * @param id accepts publishers id
     * @return boolean in case of success or failure
     */
    public boolean deletePublisher(int id) {
        Publishers publisher = findPublisherById(id);
        if (publisher == null) {
            System.out.println("Publisher not found.");
            return false;
        }

        long bookCount = entityManager.createQuery(
                        "SELECT COUNT(b) FROM Book b WHERE b.publisher = :publisher", Long.class)
                .setParameter("publisher", publisher)
                .getSingleResult();

        if (bookCount > 0) {
            System.out.println("Cannot delete publisher with associated books.");
            return false;
        }

        entityManager.getTransaction().begin();
        entityManager.remove(publisher);
        entityManager.getTransaction().commit();
        return true;
    }
    /**
     * Get all Publisher
     * @return List<Publishers>
     */
    public List<Publishers> getAllPublishers() {
        return entityManager.createQuery("SELECT p FROM Publishers p", Publishers.class).getResultList();
    }

}
