package Controller;

import Model.Publisher.PublisherManager;
import Model.Publisher.PublisherObserver;
import Model.Publisher.Publishers;

import java.util.ArrayList;
import java.util.List;

public class PublisherController {

    PublisherManager publisherManager;
    List<PublisherObserver> publisherListeners = new ArrayList<>();
    public PublisherController(PublisherManager publisherManager) {
        this.publisherManager = publisherManager;
    }

    public void addPublisherListener(PublisherObserver po){
        publisherListeners.add(po);
    }

    public void addNewPublisher(String name, String address, String phoneNumber) {
        Publishers publisher = new Publishers();
        publisher.setName(name);
        publisher.setAddress(address);
        publisher.setPhoneNumber(phoneNumber);

        publisherManager.addPublisher(publisher);
        notifyListeners("Publisher has been added!");
    }

    public void updatePublisher(int id, String name, String address, String phoneNumber) {
        Publishers publisher = new Publishers();
        publisher.setId(id);
        publisher.setAddress(address);
        publisher.setPhoneNumber(phoneNumber);

        Publishers existsPublisher = publisherManager.findPublisherById(id);
        if (name.isEmpty()) publisher.setName(existsPublisher.getName());
        else publisher.setName(name);
        if (phoneNumber.isEmpty()) publisher.setPhoneNumber(existsPublisher.getPhoneNumber());
        else publisher.setName(phoneNumber);
        if (address.isEmpty()) publisher.setAddress(existsPublisher.getAddress());
        else publisher.setName(address);

        if (publisherManager.updatePublisher(publisher)) notifyListeners("Publisher updated!");
        else notifyListeners("Publisher can't be updated");
    }

    public void deletePublisher(int id) {
        if (publisherManager.deletePublisher(id)) notifyListeners("Publisher has been deleted!");
        else notifyListeners("Publisher can't be deleted!");
        System.out.println("Publisher deleted.");
    }

    public List<Publishers> getAllPublishers() {
        return publisherManager.getAllPublishers();
    }

    public void notifyListeners(String message) {
        for (PublisherObserver publisherListener: publisherListeners) {
            publisherListener.publisherStatus(message);
        }
    }

    public void fillPublishersData() {
        Publishers publisher1 = new Publishers();
        publisher1.setName("Penguin Random House");
        publisher1.setAddress("1745 Broadway, New York, USA");
        publisher1.setPhoneNumber("+1-212-555-1234");

        Publishers publisher2 = new Publishers();
        publisher2.setName("HarperCollins");
        publisher2.setAddress("195 Broadway, New York, USA");
        publisher2.setPhoneNumber("+1-212-555-5678");

        publisherManager.addPublisher(publisher1);
        publisherManager.addPublisher(publisher2);
    }
}
