package View;

import Controller.BookController;
import Controller.LibrarianController;
import Controller.PublisherController;
import Controller.UserController;
import Model.Book.Book;
import Model.Book.BookObserver;
import Model.Librarian.LibrarianObserver;
import Model.Publisher.PublisherObserver;
import Model.User.User;
import Model.User.UserObserver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class DashboardView extends JFrame implements BookObserver, LibrarianObserver, PublisherObserver, UserObserver {

    private static DefaultTableModel defaultBooksTable = new DefaultTableModel();
    static JTable bookTable = new JTable(defaultBooksTable);

    private static BookController bookController;
    private static UserController userController;
    private static LibrarianController librarianController;
    private static PublisherController publisherController;
    private static User user;
    private static JLabel informLabel;
    public DashboardView(BookController bookController,
                         UserController userController,
                         User user, boolean isLibrarian,
                         LibrarianController librarianController,
                         PublisherController publisherController) {
        this.bookController = bookController;
        this.userController = userController;
        this.librarianController = librarianController;
        this.publisherController = publisherController;
        this.user = user;
        bookController.addBookObservers(this);
        librarianController.addLibrarianObserver(this);
        userController.addUserObservers(this);
        publisherController.addPublisherListener(this);
        bookController.getBooks();
        drawMenu(isLibrarian);

        // In case i want to add some datas here
//        publisherController.fillPublishersData();
//        bookController.fillBooksData();
//        librarianController.fillLibrariansData();
    }

    private void drawMenu(boolean isLibrarian) {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel upperPanel = new JPanel();
        upperPanel.setBackground(Color.white);
        upperPanel.add(new Label("Dashboard"));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        if (isLibrarian) leftPanel.add(createManagerPanel());
        else leftPanel.add(createUserLeftPanel(this));

        // Right Table
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        informLabel = new JLabel("Information about books", SwingConstants.CENTER);
        rightPanel.add(informLabel, BorderLayout.NORTH);

        JScrollPane tableScrollPane = new JScrollPane(bookTable);

        rightPanel.add(tableScrollPane, BorderLayout.CENTER);

        add(upperPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
    }

    private static JPanel createManagerPanel() {
        JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new GridLayout(12, 1));
        parentPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel bookTitlePanel = new JPanel();
        bookTitlePanel.setLayout(new BoxLayout(bookTitlePanel, BoxLayout.Y_AXIS));
        JLabel bookLabel = new JLabel("1. Book Title / Name (User) / Position (Librarian)");
        JTextField bookTitleField = new JTextField("");
        bookTitleField.setToolTipText("Provide Book Title, User Name, or Librarian Position based on context.");
        bookTitlePanel.add(bookLabel);
        bookTitlePanel.add(bookTitleField);

        JPanel bookIdPanel = new JPanel();
        bookIdPanel.setLayout(new BoxLayout(bookIdPanel, BoxLayout.Y_AXIS));
        JLabel bookIdLabel = new JLabel("2. Book ID / Email (User) / Librarian ID");
        JTextField bookIdField = new JTextField("");
        bookIdField.setToolTipText("Provide Book ID, User Email, or Librarian ID based on context.");
        bookIdPanel.add(bookIdLabel);
        bookIdPanel.add(bookIdField);

        JPanel authorDataPanel = new JPanel();
        authorDataPanel.setLayout(new BoxLayout(authorDataPanel, BoxLayout.Y_AXIS));
        JLabel authorLabel = new JLabel("3. Author Name / Password");
        JTextField authorField = new JTextField("");
        authorField.setToolTipText("Provide Author Name or Password based on context.");
        authorDataPanel.add(authorLabel);
        authorDataPanel.add(authorField);

        JPanel publisherDataPanel = new JPanel();
        publisherDataPanel.setLayout(new BoxLayout(publisherDataPanel, BoxLayout.Y_AXIS));
        JLabel publisherLabel = new JLabel("4. Publisher ID / Phone Number");
        JTextField publisherField = new JTextField("");
        publisherField.setToolTipText("Provide Publisher ID or Phone Number based on context.");
        publisherDataPanel.add(publisherLabel);
        publisherDataPanel.add(publisherField);

        JPanel publicationDataPanel = new JPanel();
        publicationDataPanel.setLayout(new BoxLayout(publicationDataPanel, BoxLayout.Y_AXIS));
        JLabel publicationLabel = new JLabel("5. Publication Year / Employment Date");
        DateFormat df = new SimpleDateFormat("yyyy");
        JFormattedTextField txtDate = new JFormattedTextField(df);
        txtDate.setToolTipText("Provide Publication Year for Book or Employment Date for Librarian.");
        publicationDataPanel.add(publicationLabel);
        publicationDataPanel.add(txtDate);

        JPanel copiesDataPanel = new JPanel();
        copiesDataPanel.setLayout(new BoxLayout(copiesDataPanel, BoxLayout.Y_AXIS));
        JLabel copiesLabel = new JLabel("6. Copies (Book) / Address (User)");
        JTextField copiesField = new JTextField("");
        copiesField.setToolTipText("Provide number of Copies for Books or Address for Users.");
        copiesDataPanel.add(copiesLabel);
        copiesDataPanel.add(copiesField);

        JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(e -> {
            bookController.addBook(
                    bookTitleField.getText(),
                    bookIdField.getText(),
                    authorField.getText(),
                    publisherField.getText(),
                    Integer.parseInt(copiesField.getText()),
                    Integer.parseInt(txtDate.getText()));

            bookTitleField.setText("");
            bookIdField.setText("");
            authorField.setText("");
            publisherField.setText("");
            copiesField.setText("");
            txtDate.setText("");
        });

        JButton updateBookButton = new JButton("Update Book");
        updateBookButton.addActionListener(e -> {
            bookController.updateBook(
                    bookTitleField.getText(),
                    bookIdField.getText(),
                    authorField.getText(),
                    publisherField.getText(),
                    Integer.parseInt(txtDate.getText().isEmpty() ? "0" : txtDate.getText()),
                    Integer.parseInt(copiesField.getText().isEmpty() ? "0" : copiesField.getText()));

            bookTitleField.setText("");
            bookIdField.setText("");
            authorField.setText("");
            publisherField.setText("");
            copiesField.setText("");
            txtDate.setText("");
        });

        JButton deleteBookButton = new JButton("Delete Book");
        deleteBookButton.addActionListener(e -> {
            bookController.deleteBook(bookIdField.getText());

            bookTitleField.setText("");
            bookIdField.setText("");
            authorField.setText("");
            publisherField.setText("");
            copiesField.setText("");
            txtDate.setText("");
        });

        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(e -> {
            userController.addNewUser(
                    bookTitleField.getText(), bookIdField.getText(), authorField.getText(),
                    publisherField.getText(), copiesField.getText());
            bookTitleField.setText("");
            bookIdField.setText("");
            authorField.setText("");
            publisherField.setText("");
            copiesField.setText("");
        });


        JButton updateUserButton = new JButton("Update User");
        updateUserButton.addActionListener(e -> {
            userController.updateUser(
                    Integer.parseInt(bookIdField.getText()), bookTitleField.getText(), authorField.getText(),
                    copiesField.getText(), publisherField.getText());
            bookTitleField.setText("");
            bookIdField.setText("");
            authorField.setText("");
            publisherField.setText("");
            copiesField.setText("");
            txtDate.setText("");
        });

        JButton deleteUserButton = new JButton("Delete User");
        deleteUserButton.addActionListener(e -> userController.deleteUser(Integer.parseInt(bookIdField.getText())));

        JButton addLibrarianButton = new JButton("Add Librarian");
        addLibrarianButton.addActionListener(e -> {
            librarianController.addNewLibrarian(
                    bookIdField.getText(), bookTitleField.getText(), authorField.getText(), txtDate.getText());
            bookTitleField.setText("");
            bookIdField.setText("");
            authorField.setText("");
            publisherField.setText("");
            copiesField.setText("");
            txtDate.setText("");
        });

        JButton updateLibrarianButton = new JButton("Update Librarian");
        updateLibrarianButton.addActionListener(e -> {
            librarianController.updateLibrarian(
                    bookIdField.getText(), bookTitleField.getText(), authorField.getText(), txtDate.getText());
            bookTitleField.setText("");
            bookIdField.setText("");
            authorField.setText("");
            publisherField.setText("");
            copiesField.setText("");
            txtDate.setText("");
        });

        JButton deleteLibrarianButton = new JButton("Delete Librarian");
        deleteLibrarianButton.addActionListener(e -> {
            librarianController.deleteLibrarian(Integer.parseInt(bookIdField.getText()));
            bookTitleField.setText("");
            bookIdField.setText("");
            authorField.setText("");
            publisherField.setText("");
            copiesField.setText("");
            txtDate.setText("");
        });


        JButton addPublisherButton = new JButton("Add Publisher");
        addPublisherButton.addActionListener(e -> {
            publisherController.addNewPublisher(
                    bookTitleField.getText(), copiesField.getText(), publisherField.getText());
            bookTitleField.setText("");
            bookIdField.setText("");
            authorField.setText("");
            publisherField.setText("");
            copiesField.setText("");
            txtDate.setText("");
        });

        JButton updatePublisherButton = new JButton("Update Publisher");
        updatePublisherButton.addActionListener(e -> {
            publisherController.updatePublisher(
                    Integer.parseInt(bookIdField.getText()), bookTitleField.getText(),
                    copiesField.getText(), publisherField.getText());
            bookTitleField.setText("");
            bookIdField.setText("");
            authorField.setText("");
            publisherField.setText("");
            copiesField.setText("");
            txtDate.setText("");
        });

        JButton deletePublisherButton = new JButton("Delete Publisher");
        deletePublisherButton.addActionListener(e -> {
            publisherController.deletePublisher(Integer.parseInt(bookIdField.getText()));
            bookTitleField.setText("");
            bookIdField.setText("");
            authorField.setText("");
            publisherField.setText("");
            copiesField.setText("");
            txtDate.setText("");
        });

        parentPanel.add(bookTitlePanel);
        parentPanel.add(bookIdPanel);
        parentPanel.add(authorDataPanel);
        parentPanel.add(publisherDataPanel);
        parentPanel.add(publicationDataPanel);
        parentPanel.add(copiesDataPanel);

        parentPanel.add(addBookButton);
        parentPanel.add(updateBookButton);
        parentPanel.add(deleteBookButton);

        parentPanel.add(addUserButton);
        parentPanel.add(updateUserButton);
        parentPanel.add(deleteUserButton);

        parentPanel.add(addLibrarianButton);
        parentPanel.add(updateLibrarianButton);
        parentPanel.add(deleteLibrarianButton);

        parentPanel.add(addPublisherButton);
        parentPanel.add(updatePublisherButton);
        parentPanel.add(deletePublisherButton);

        return parentPanel;
    }


    private static JPanel createUserLeftPanel(JFrame jFrame) {
        JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new GridLayout(2, 1));

        JButton logOut = new JButton("Log Out");
        logOut.addActionListener(e -> {
            if (logOut.getText().equals("History")) {
                bookController.getHistoryBooks(user);
            } else {
                userController.signOut();
                jFrame.setVisible(false);
            }
        });

        JButton borrowBook = new JButton("Borrow book");
        borrowBook.addActionListener(e -> {
            int row = bookTable.getSelectedRow();
            int column = bookTable.getSelectedColumn();
            String value = bookTable.getValueAt(row, column).toString();

            if (borrowBook.getText().equals("Return book")) bookController.returnBook(user, value);
            else bookController.borrowBook(user, value);
        });

        JButton returnBooks = new JButton("My Books");
        returnBooks.addActionListener(e -> {
            if (logOut.getText().equals("Log Out")) logOut.setText("History");
            if (borrowBook.getText().equals("Borrow book")) borrowBook.setText("Return book");
            bookController.getBorrowedBooks(user);
        });

        JButton showBooks = new JButton("Show Books");
        showBooks.addActionListener(e -> {
            if (borrowBook.getText().equals("Return book")) borrowBook.setText("Borrow book");
            if (logOut.getText().equals("History")) logOut.setText("Log Out");
            bookController.getBooks();
        });

        parentPanel.add(showBooks);
        parentPanel.add(returnBooks);
        parentPanel.add(logOut);
        parentPanel.add(borrowBook);
        return  parentPanel;
    }

    @Override
    public void updateBookStatus(String message) {
        informLabel.setText(message);
    }

    @Override
    public void onBooksLoaded(List<Book> books) {
        defaultBooksTable.setRowCount(0);
        defaultBooksTable.setColumnCount(0);

        for(Book book: books) System.out.println("B: " + book.getTitle());
        String[] columnNames = {"ID", "Title", "Author", "Publisher", "Publication Year", "ISBN"};

        for(String column: columnNames) {
            defaultBooksTable.addColumn(column);
        }

        for (Book book : books) {
            Object[] rowData = {
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher().getName(),
                    book.getPublicationYear(),
                    book.getIsbn()
            };
            defaultBooksTable.addRow(rowData);
        }

        defaultBooksTable.fireTableDataChanged();
    }

    @Override
    public void librarianStatus(String message) {
        informLabel.setText(message);
    }

    @Override
    public void publisherStatus(String message) {
        informLabel.setText(message);
    }

    @Override
    public void loginStatus(String message) {
        informLabel.setText(message);
    }
}
