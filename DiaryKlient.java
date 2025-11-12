package .git;
import java.util.Scanner;

public class DiaryKlient {
    private static Scanner scanner = new Scanner(System.in);
    private static DiaryRegistry register = new DiaryRegistry();

    public static void main(String[] args) {
        int choice = 0;
        do {
            System.out.println("\n--- DiaryRegistry ---");
            System.out.println("1. Register new author");
            System.out.println("2. Print all entries");
            System.out.println("3. Delete entry by date");
            System.out.println("4. Register new entry");
            System.out.println("5. Print all authors");
            System.out.println("6. Search author by name");
            System.out.println("0. Exit");
            System.out.print("Select: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    registerAuthor();
                    break;
                case 2:
                    showAllEntries();
                    break;
                case 3:
                    deleteEntry();
                    break;
                case 4:
                    registerDiary();
                    break;
                case 5:
                    showAllAuthors();
                    break;
                case 6:
                    findAuthor();
                    break;
                case 0:
                    System.out.println("Exited!");
                    break;
                default:
                    System.out.println("Invalid selection, try again.");
            }
        } while (choice != 0);
    }

    private static void registerAuthor() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        register.registerAuthor(new Author(name));
        System.out.println("Author registered.");
    }

    private static void findAuthor() {
        System.out.print("Write author name: ");
        String name = scanner.nextLine();
        Author author = register.findAuthor(name);
        if (author != null) {
            System.out.println(author);
        } else {
            System.out.println("No author found.");
        }
    }

    private static void registerDiary() {
        System.out.print("Date (e.g. 2025-11-12): ");
        String date = scanner.nextLine();
        System.out.print("Entry text: ");
        String content = scanner.nextLine();

        Diary entry = new Diary(date, content);

        while (true) {
            System.out.print("Add author to entry (blank to finish): ");
            String name = scanner.nextLine();
            if (name.isBlank()) {
                break;
            }

            Author author = register.findAuthor(name);
            if (author != null) {
                entry.addAuthor(author);
                System.out.println("Author added.");
                break;
            } else {
                System.out.println("Author not found.");
            }
        }

        register.registerEntry(entry);
        System.out.println("Entry registered.");
    }

    private static void showAllAuthors() {
        System.out.println("\n-- All authors --");
        for (Author a : register.getAuthors()) {
            System.out.println(a);
        }
    }

    private static void showAllEntries() {
        System.out.println("\n-- All entries --");
        for (Diary d : register.getEntries()) {
            System.out.println(d);
        }
    }

    private static void deleteEntry() {
        System.out.print("Enter date of entry to delete: ");
        String date = scanner.nextLine();
        boolean removed = register.deleteEntry(date);
        if (removed) {
            System.out.println("Entry deleted.");
        } else {
            System.out.println("No entry found for that date.");
        }
    }
}
