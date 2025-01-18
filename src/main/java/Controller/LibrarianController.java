package Controller;

import Model.Librarian.LibrarianManager;
import Model.Librarian.LibrarianObserver;
import Model.Librarian.Librarians;
import Model.User.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Librarian Controller
 * Class intercats with Model and View
 * and implement the MVC controller
 */

public class LibrarianController {

    LibrarianManager librarianManager;

    List<LibrarianObserver> librarianListeners = new ArrayList<>();
    public LibrarianController(LibrarianManager librarianManager) {
        this.librarianManager = librarianManager;
    }

    /**
     * Add Listener to interact with all Listeners
     * @param librarianObserver
     */
    public void addLibrarianObserver(LibrarianObserver librarianObserver) {
        librarianListeners.add(librarianObserver);
    }
    /**
     * Add new librarian, method called from view.
     *
     * @param id userId
     * @param position Librarian position
     * @param password password for librarian
     * @param employmentDate date of starting work
     *
     *                       collect all datas, create enityt of Librarians and interact with Manager
     */
    public void addNewLibrarian(String id, String position, String password, String employmentDate) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy");
            Date employmentDateData = df.parse(employmentDate);
            Librarians librarian = new Librarians();
            User user = librarianManager.findUserById(Integer.parseInt(id));
            if (user == null) return;

            librarian.setUser(user);
            librarian.setPosition(position);
            librarian.setPassword(password);
            librarian.setEmploymentDate(employmentDateData);

            if (librarianManager.addLibrarian(librarian)) notifyListeners("Librarian has been added!");
            else notifyListeners("Librarian can't be added");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update existing librarian, method called from view.
     *
     * @param id userId
     * @param position Librarian position
     * @param password password for librarian
     * @param employmentDate date of starting work
     *
     *                       collect all datas, create enityt of Librarian and interact with Manager to update
     */
    public void updateLibrarian(String id, String position, String password, String employmentDate) {
        try {
            Librarians librarian = new Librarians();
            librarian.setId(Integer.parseInt(id));
            librarian.setPosition(position);
            librarian.setPassword(password);

            if(employmentDate.isEmpty()) {
                Date emplDate = librarianManager.findLibrarianById(Integer.parseInt(id)).getEmploymentDate();
                librarian.setEmploymentDate(emplDate);
            } else {
                DateFormat df = new SimpleDateFormat("yyyy");
                Date employmentDateData = df.parse(employmentDate);
                librarian.setEmploymentDate(employmentDateData);
            }


            if (librarianManager.updateLibrarian(librarian)) notifyListeners("Librarian has been updated!");
            else notifyListeners("Librarian can't be updated...");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update existing librarian, method called from view.
     *
     * @param message get message from the methods and send to listeners
     */
    public void notifyListeners(String message) {
        for(LibrarianObserver librarianObserver: librarianListeners){
            librarianObserver.librarianStatus(message);
        }
    }

    public void deleteLibrarian(int id) {
        if (librarianManager.deleteLibrarian(id)) notifyListeners("Librarian has been deleted!");
        else notifyListeners("Can not find librarian!");

    }

    /**
     * Called once to interact with 'data'
     *
     */
    public void fillLibrariansData() {

        List<User> users = librarianManager.getAllUsers();
        System.out.println(users.size() +  " SIZE");
        Librarians librarian1 = new Librarians();
        librarian1.setUser(librarianManager.findUserById(users.get(0).getId()));
        librarian1.setPosition("Head Librarian");
        librarian1.setEmploymentDate(new Date());
        librarian1.setPassword("librarian123");

        Librarians librarian2 = new Librarians();
        librarian1.setUser(librarianManager.findUserById(users.get(1).getId()));
        librarian2.setPosition("Assistant Librarian");
        librarian2.setEmploymentDate(new Date());
        librarian2.setPassword("librarian456");

        librarianManager.addLibrarian(librarian1);
        librarianManager.addLibrarian(librarian2);


    }
}
