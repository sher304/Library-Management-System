package View;

import Controller.BookController;
import Controller.UserController;
import Model.Book.Book;
import Model.Book.BookObserver;
import Model.User.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class DashboardView extends JFrame implements BookObserver {

    private static DefaultTableModel defaultBooksTable = new DefaultTableModel();
    static JTable bookTable = new JTable(defaultBooksTable);

    private static BookController bookController;
    private static UserController userController;
    private static User user;

    public DashboardView(BookController bookController,
                         UserController userController,
                         User user, boolean isLibrarian) {
        this.bookController = bookController;
        this.userController = userController;
        this.user = user;
        bookController.addBookObservers(this);
        bookController.getBooks();
        drawMenu(isLibrarian);
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
        JLabel informLabel = new JLabel("Information about books", SwingConstants.CENTER);
        rightPanel.add(informLabel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        JTextField searchField = new JTextField("");
        searchPanel.add(searchField);
        searchPanel.add(new Button("Search book"));

        JScrollPane tableScrollPane = new JScrollPane(bookTable);

        rightPanel.add(tableScrollPane, BorderLayout.CENTER);
        rightPanel.add(searchPanel, BorderLayout.SOUTH);

        add(upperPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
    }

    private static JPanel createManagerPanel() {
        JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new GridLayout(7, 1));
        parentPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel bookTitlePanel = new JPanel();
        bookTitlePanel.setLayout(new BoxLayout(bookTitlePanel, BoxLayout.Y_AXIS));
        JLabel bookLabel = new JLabel("Book Title");
        JTextField bookTitleField = new JTextField("");
        bookTitlePanel.add(bookLabel);
        bookTitlePanel.add(bookTitleField);

        JPanel bookIdPanel = new JPanel();
        bookIdPanel.setLayout(new BoxLayout(bookIdPanel, BoxLayout.Y_AXIS));
        JLabel bookIdLabel = new JLabel("Book ID");
        JTextField bookIdField = new JTextField("");
        bookIdPanel.add(bookIdLabel);
        bookIdPanel.add(bookIdField);

        JPanel authorDataPanel = new JPanel();
        authorDataPanel.setLayout(new BoxLayout(authorDataPanel, BoxLayout.Y_AXIS));
        JLabel authorLabel = new JLabel("Author Name");
        JTextField authorField = new JTextField("");
        authorDataPanel.add(authorLabel);
        authorDataPanel.add(authorField);

        JPanel publisherDataPanel = new JPanel();
        publisherDataPanel.setLayout(new BoxLayout(publisherDataPanel, BoxLayout.Y_AXIS));
        JLabel publisherLabel = new JLabel("Publisher id");
        JTextField publisherField = new JTextField("");
        publisherDataPanel.add(publisherLabel);
        publisherDataPanel.add(publisherField);

        JPanel publicationDataPanel = new JPanel();
        publicationDataPanel.setLayout(new BoxLayout(publicationDataPanel, BoxLayout.Y_AXIS));
        JLabel publicationLabel = new JLabel("Publication Date");
        DateFormat df = new SimpleDateFormat("yyyy");
        JFormattedTextField txtDate = new JFormattedTextField(df);
        publicationDataPanel.add(publicationLabel);
        publicationDataPanel.add(txtDate);


        JPanel copiesDataPanel = new JPanel();
        copiesDataPanel.setLayout(new BoxLayout(copiesDataPanel, BoxLayout.Y_AXIS));
        JLabel copiesLabel = new JLabel("Copies: ");
        JTextField copiesField = new JTextField("");
        copiesDataPanel.add(copiesLabel);
        copiesDataPanel.add(copiesField);

        JButton addBook = new JButton("Add new book");
        addBook.setPreferredSize(new Dimension(150, 30));
        addBook.addActionListener(e -> {
            bookController.addBook(bookTitleField.getText(),
                                    bookIdField.getText(),
                                    authorField.getText(),
                                    publisherField.getText(),
                    Integer.parseInt(copiesField.getText()),
                    Integer.parseInt(txtDate.getText()));

        });

        JButton updateBook = new JButton("Update book");
        updateBook.setPreferredSize(new Dimension(150, 30));
        updateBook.addActionListener(e -> {
            bookController.updateBook(bookTitleField.getText(),
                    bookIdField.getText(),
                    authorField.getText(),
                    publisherField.getText(),
                    Integer.parseInt(txtDate.getText()));

        });

        JButton deleteBook = new JButton("Delete book");
        deleteBook.addActionListener( e -> {
            bookController.deleteBook(bookIdField.getText());
        });

        deleteBook.setPreferredSize(new Dimension(150, 30));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addBook);
        buttonPanel.add(updateBook);
        buttonPanel.add(deleteBook);

        parentPanel.add(bookTitlePanel);
        parentPanel.add(bookIdPanel);
        parentPanel.add(authorDataPanel);
        parentPanel.add(publisherDataPanel);
        parentPanel.add(publicationDataPanel);
        parentPanel.add(copiesDataPanel);
        parentPanel.add(buttonPanel);

        return  parentPanel;
    }


    private static JPanel createUserLeftPanel(JFrame jFrame) {
        JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new GridLayout(2, 1));

        JButton logOut = new JButton("Log Out");
        logOut.addActionListener(e -> {
            if (logOut.getText().equals("History")) bookController.getHistoryBooks(user);
            else {
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

        JButton returnBooks = new JButton("Show Books");
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
        System.out.println(message);
    }

    @Override
    public void onBooksLoaded(List<Book> books) {
        defaultBooksTable.setRowCount(0);
        defaultBooksTable.setColumnCount(0);

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
    }
}
