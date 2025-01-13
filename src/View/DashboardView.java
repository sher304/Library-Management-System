package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DashboardView extends JFrame {

    private static DefaultTableModel defaultBooksTable = new DefaultTableModel();
    static JTable bookTable = new JTable(defaultBooksTable);
    private static boolean toggleVisibility = true;

    public DashboardView() {
        setColumnNames();
        setData();

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
//        leftPanel.add(createManagerPanel());
        leftPanel.add(createUserLeftPanel());

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
        JLabel publisherLabel = new JLabel("Publisher");
        JTextField publisherField = new JTextField("");
        publisherDataPanel.add(publisherLabel);
        publisherDataPanel.add(publisherField);

        JPanel publicationDataPanel = new JPanel();
        publicationDataPanel.setLayout(new BoxLayout(publicationDataPanel, BoxLayout.Y_AXIS));
        JLabel publicationLabel = new JLabel("Publication Date");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        JFormattedTextField txtDate = new JFormattedTextField(df);
        publicationDataPanel.add(publicationLabel);
        publicationDataPanel.add(txtDate);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        JTextField searchField = new JTextField("");
        searchPanel.add(searchField);
        searchPanel.add(new Button("Search book"));

        JButton addBook = new JButton("Add new book");
        addBook.addActionListener(e -> {
            System.out.println("Book title: " + bookTitleField.getText());
            System.out.println("Book id: " + bookIdField.getText());
            System.out.println("Book author: " + authorField.getText());
            System.out.println("Book publisher: " + publisherField.getText());
            System.out.println("Book publication date: " + txtDate.getText());
        });
        addBook.setPreferredSize(new Dimension(150, 30));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addBook);

        parentPanel.add(bookTitlePanel);
        parentPanel.add(bookIdPanel);
        parentPanel.add(authorDataPanel);
        parentPanel.add(publisherDataPanel);
        parentPanel.add(publicationDataPanel);
        parentPanel.add(buttonPanel);
        parentPanel.add(searchPanel);

        return  parentPanel;
    }


    private static JPanel createUserLeftPanel() {
        JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new GridLayout(2, 1));

        JButton showBooks = new JButton("Show Books");
        showBooks.addActionListener(e -> {
            toggleVisibility = true;
            bookTable.setVisible(toggleVisibility);
            setColumnNames();
            setData();

            System.out.println("SSS Column count: " + defaultBooksTable.getColumnCount());
            System.out.println("SSS Row count: " + defaultBooksTable.getRowCount());
        });
        JButton returnBooks = new JButton("Return Books");
        returnBooks.addActionListener(e -> {
            toggleVisibility = false;
            bookTable.setVisible(toggleVisibility);
            setBookedColumnNames();
            setBookedData();

            System.out.println("RRR Column count: " + defaultBooksTable.getColumnCount());
            System.out.println("RRRR Row count: " + defaultBooksTable.getRowCount());

        });
        JButton logOut = new JButton("Log Out");

        parentPanel.add(showBooks);
        parentPanel.add(returnBooks);
        parentPanel.add(logOut);
        return  parentPanel;
    }

    private static void setColumnNames() {
        defaultBooksTable.setColumnCount(0);
        defaultBooksTable.addColumn("Book Title");
        defaultBooksTable.addColumn("Book ID");
        defaultBooksTable.addColumn("Author");
        defaultBooksTable.addColumn("Status");

        // if manager
//        booksTable.addColumn("ID of holder");
    }

    private static void setData() {
        defaultBooksTable.setRowCount(0);
        for (int i = 0; i < 20; i++) {
            defaultBooksTable.addRow(new String[]{"C:+" + i});
        }
    }

    private static void setBookedColumnNames() {
        defaultBooksTable.setColumnCount(0);
        defaultBooksTable.addColumn("Book Title");
        defaultBooksTable.addColumn("Book ID");
        defaultBooksTable.addColumn("Author");
        defaultBooksTable.addColumn("Status");
        defaultBooksTable.addColumn("Borrow date");
        defaultBooksTable.addColumn("Return date");
        defaultBooksTable.addColumn("Days");
        // if manager
//        booksTable.addColumn("ID of holder");
    }

    private static void setBookedData() {
        defaultBooksTable.setRowCount(0);
        for (int i = 0; i < 20; i++) {
            defaultBooksTable.addRow(new String[]{"A:+" + i});
        }
    }
}
