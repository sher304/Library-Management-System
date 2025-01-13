package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DashboardView extends JFrame {

    private DefaultTableModel booksTable = new DefaultTableModel();

    public DashboardView() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(createManagerPanel());

        add(leftPanel, BorderLayout.WEST);
    }

    private static JPanel createManagerPanel() {
        JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new GridLayout(6, 1));
        parentPanel.setBackground(Color.red);

        JPanel bookTitlePanel = new JPanel();
        bookTitlePanel.setLayout(new BoxLayout(bookTitlePanel, BoxLayout.Y_AXIS));
        JLabel bookLabel = new JLabel("Book Title");
        JTextField bookTitleField = new JTextField("");
        bookTitleField.setPreferredSize(new Dimension(200, 30));
        bookTitleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        bookTitlePanel.add(bookLabel);
        bookTitlePanel.add(bookTitleField);

        JPanel bookIdPanel = new JPanel();
        bookIdPanel.setLayout(new BoxLayout(bookIdPanel, BoxLayout.Y_AXIS));
        JLabel bookIdLabel = new JLabel("Book ID");
        JTextField bookIdField = new JTextField("");
        bookIdField.setPreferredSize(new Dimension(200, 30));
        bookIdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        bookIdPanel.add(bookIdLabel);
        bookIdPanel.add(bookIdField);

        JPanel authorDataPanel = new JPanel();
        authorDataPanel.setLayout(new BoxLayout(authorDataPanel, BoxLayout.Y_AXIS));
        JLabel authorLabel = new JLabel("Author Name");
        JTextField authorField = new JTextField("");
        authorField.setPreferredSize(new Dimension(200, 30));
        authorField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        authorDataPanel.add(authorLabel);
        authorDataPanel.add(authorField);

        JPanel publisherDataPanel = new JPanel();
        publisherDataPanel.setLayout(new BoxLayout(publisherDataPanel, BoxLayout.Y_AXIS));
        JLabel publisherLabel = new JLabel("Publisher");
        JTextField publisherField = new JTextField("");
        publisherField.setPreferredSize(new Dimension(200, 30));
        publisherField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        publisherDataPanel.add(publisherLabel);
        publisherDataPanel.add(publisherField);

        JPanel publicationDataPanel = new JPanel();
        publicationDataPanel.setLayout(new BoxLayout(publicationDataPanel, BoxLayout.Y_AXIS));
        JLabel publicationLabel = new JLabel("Publication Date");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        JFormattedTextField txtDate = new JFormattedTextField(df);
        txtDate.setPreferredSize(new Dimension(200, 30));
        txtDate.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        publicationDataPanel.add(publicationLabel);
        publicationDataPanel.add(txtDate);

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

        return  parentPanel;
    }
}
