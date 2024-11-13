import java.util.*;
import java.io.*;

class Contact {
    String name;
    String phoneNumber;

    Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber;
    }
}

public class phonebook {
    private LinkedList<Contact> contacts = new LinkedList<>();

    public void addContact(String name, String phoneNumber) {
        contacts.add(new Contact(name, phoneNumber));
    }

    public void deleteContact(String name) {
        Iterator <Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.name.equals(name)) {
                iterator.remove();
                break;
            }
        }
    }

    public void printContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public String searchContacts(String name) {
        for (Contact contact : contacts) {
            if (contact.name.equals(name)) {
                return "name: " + contact.name  + " , phoneNumber: " + contact.phoneNumber;
            }
        }
        return "null";
    }

    public void sortByName() {
        contacts.sort(Comparator.comparing(contact -> contact.name));
    }

    public void sortByPhoneNumber() {
        contacts.sort((c1, c2) -> c2.phoneNumber.compareTo(c1.phoneNumber));
    }

    public void removeDuplicates() {
        Set <String> seen = new HashSet<>();
        Iterator <Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            String key = contact.name + ":" + contact.phoneNumber;
            if (seen.contains(key)) {
                iterator.remove();
            } else {
                seen.add(key);
            }
        }
    }

    public void reverseOrder() {
        Collections.reverse(contacts);
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Contact contact : contacts) {
                writer.write(contact.name + "," + contact.phoneNumber);
                writer.newLine();
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    addContact(parts[0], parts[1]);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        phonebook phoneBook = new phonebook();
        while (true) {
            System.out.println("\nPhone Book Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. Delete Contact");
            System.out.println("3. Print Contacts");
            System.out.println("4. Search Contact");
            System.out.println("5. Sort by Name");
            System.out.println("6. Sort by Phone Number");
            System.out.println("7. Remove Duplicates");
            System.out.println("8. Reverse Order");
            System.out.println("9. Save to File");
            System.out.println("10. Load from File");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");
            int choice = -1;
            try {
                choice = scanner.nextInt();
            }catch (Exception e) { scanner.next(); }
            scanner.nextLine();

            switch (choice) {
                case 1:
                    int check = 0;
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    for (int i = 0; i < phoneNumber.length();i++){
                        if (phoneNumber.charAt(i) != '0' && phoneNumber.charAt(i) != '1'
                                && phoneNumber.charAt(i) != '2' && phoneNumber.charAt(i) != '3'
                                && phoneNumber.charAt(i) != '4' && phoneNumber.charAt(i) != '5'
                                && phoneNumber.charAt(i) != '6' && phoneNumber.charAt(i) != '7'
                                && phoneNumber.charAt(i) != '8' && phoneNumber.charAt(i) != '9'
                                || phoneNumber.length() != 10){
                            check = 1;
                        }}
                    if (check == 0){
                        phoneBook.addContact(name, phoneNumber);
                    }else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                    break;
                case 2:
                    System.out.print("Enter name to delete: ");
                    name = scanner.nextLine();
                    phoneBook.deleteContact(name);
                    break;
                case 3:
                    phoneBook.printContacts();
                    break;
                case 4:
                    System.out.print("Enter name to search: ");
                    name = scanner.nextLine();
                    if (phoneBook.searchContacts(name) == "null"){
                        System.out.println("this person dose not exist.");
                    }else {
                        String str = phoneBook.searchContacts(name);
                        System.out.println(str);
                    }
                    break;
                case 5:
                    phoneBook.sortByName();
                    break;
                case 6:
                    phoneBook.sortByPhoneNumber();
                    break;
                case 7:
                    phoneBook.removeDuplicates();
                    break;
                case 8:
                    phoneBook.reverseOrder();
                    break;
                case 9:
                    System.out.print("Enter filename to save: ");
                    String saveFilename = scanner.nextLine();
                    try {
                        phoneBook.saveToFile(saveFilename);
                    } catch (IOException e) {
                        System.out.println("Error saving to file: " + e.getMessage());
                    }
                    break;
                case 10:
                    System.out.print("Enter filename to load: ");
                    String loadFilename = scanner.nextLine();
                    try {
                        phoneBook.loadFromFile(loadFilename);
                    } catch (IOException e) {
                        System.out.println("Error loading from file: " + e.getMessage());
                    }
                    break;
                case 11:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
