package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Objects;

public class DashboardView extends JFrame {

    private DefaultTableModel booksTable = new DefaultTableModel();

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
        JLabel informLabel = new JLabel("Information about books");
        rightPanel.add(informLabel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        JTextField searchField = new JTextField("");
        searchPanel.add(searchField);
        searchPanel.add(new Button("Search book"));

        JTable table = new JTable(booksTable);
        JScrollPane tableScrollPane = new JScrollPane(table);
        rightPanel.add(tableScrollPane, BorderLayout.CENTER);
        rightPanel.add(searchPanel, BorderLayout.SOUTH);

        add(upperPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel);
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
        JButton returnBooks = new JButton("Return Books");
        JButton logOut = new JButton("Log Out");

        parentPanel.add(showBooks);
        parentPanel.add(returnBooks);
        parentPanel.add(logOut);
        return  parentPanel;
    }

    public void setColumnNames() {
        booksTable.addColumn("Book Title");
        booksTable.addColumn("Book ID");
        booksTable.addColumn("Author");
        booksTable.addColumn("Status");

        // if manager
//        booksTable.addColumn("ID of holder");
    }

    public void setData() {
        booksTable.setRowCount(0);
        for (int i = 0; i < 20; i++) {
            booksTable.addRow(new String[]{"C:+" + i});
        }
    }
}
