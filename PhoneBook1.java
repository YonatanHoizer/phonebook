import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

class Contact1 {
    String name;
    String phoneNumber;

    Contact1(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber;
    }
}

public class PhoneBook1 {
    private LinkedList<Contact1> contacts = new LinkedList<>();

    public void addContact1(String name, String phoneNumber) {
        if (isValidPhoneNumber1(phoneNumber)) {
            contacts.add(new Contact1(name, phoneNumber));
        } else {
            JOptionPane.showMessageDialog(null, "Invalid phone number. It must be 10 digits long and start with '05'.");
        }
    }

    public void deleteContact1(String name) {
        Iterator<Contact1> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact1 contact = iterator.next();
            if (contact.name.equals(name)) {
                iterator.remove();
                break;
            }
        }
    }

    public void printContacts1(JTextArea textArea) {
        textArea.setText("");
        for (Contact1 contact : contacts) {
            textArea.append(contact + "\n");
        }
    }

    public void searchContacts1(String name, JTextArea textArea) {
        textArea.setText("");
        for (Contact1 contact : contacts) {
            if (contact.name.equals(name)) {
                textArea.append(contact + "\n");
            }
        }
    }

    public void sortByName1() {
        contacts.sort(Comparator.comparing(contact -> contact.name));
    }

    public void sortByPhoneNumber1() {
        contacts.sort((c1, c2) -> c2.phoneNumber.compareTo(c1.phoneNumber));
    }

    public void removeDuplicates1() {
        Set<String> seen = new HashSet<>();
        Iterator<Contact1> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact1 contact = iterator.next();
            String key = contact.name + ":" + contact.phoneNumber;
            if (seen.contains(key)) {
                iterator.remove();
            } else {
                seen.add(key);
            }
        }
    }

    public void reverseOrder1() {
        Collections.reverse(contacts);
    }

    public void saveToFile1(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Contact1 contact : contacts) {
                writer.write(contact.name + "," + contact.phoneNumber);
                writer.newLine();
            }
        }
    }

    public void loadFromFile1(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    addContact1(parts[0], parts[1]);
                }
            }
        }
    }

    private boolean isValidPhoneNumber1(String phoneNumber) {
        return phoneNumber.matches("^05\\d{8}$");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PhoneBook1().createAndShowGUI1();
        });
    }

    private void createAndShowGUI1() {
        JFrame frame = new JFrame("Phone Book");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JTextField nameField = new JTextField(15);
        JTextField phoneField = new JTextField(15);

        JButton addButton = new JButton("Add Contact");
        JButton deleteButton = new JButton("Delete Contact");
        JButton printButton = new JButton("Print Contacts");
        JButton searchButton = new JButton("Search Contact");
        JButton sortByNameButton = new JButton("Sort by Name");
        JButton sortByPhoneButton = new JButton("Sort by Phone Number");
        JButton removeDuplicatesButton = new JButton("Remove Duplicates");
        JButton reverseOrderButton = new JButton("Reverse Order");
        JButton saveButton = new JButton("Save to File");
        JButton loadButton = new JButton("Load from File");

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 4));
        buttonPanel.add(printButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(sortByNameButton);
        buttonPanel.add(sortByPhoneButton);
        buttonPanel.add(removeDuplicatesButton);
        buttonPanel.add(reverseOrderButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addContact1(nameField.getText(), phoneField.getText()));
        deleteButton.addActionListener(e -> deleteContact1(nameField.getText()));
        printButton.addActionListener(e -> printContacts1(textArea));
        searchButton.addActionListener(e -> searchContacts1(nameField.getText(), textArea));
        sortByNameButton.addActionListener(e -> {
            sortByName1();
            printContacts1(textArea);
        });
        sortByPhoneButton.addActionListener(e -> {
            sortByPhoneNumber1();
            printContacts1(textArea);
        });
        removeDuplicatesButton.addActionListener(e -> {
            removeDuplicates1();
            printContacts1(textArea);
        });
        reverseOrderButton.addActionListener(e -> {
            reverseOrder1();
            printContacts1(textArea);
        });
        saveButton.addActionListener(e -> {
            String filename = JOptionPane.showInputDialog(frame, "Enter filename to save:");
            if (filename != null) {
                try {
                    saveToFile1(filename);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving to file: " + ex.getMessage());
                }
            }
        });
        loadButton.addActionListener(e -> {
            String filename = JOptionPane.showInputDialog(frame, "Enter filename to load:");
            if (filename != null) {
                try {
                    loadFromFile1(filename);
                    printContacts1(textArea);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error loading from file: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}

