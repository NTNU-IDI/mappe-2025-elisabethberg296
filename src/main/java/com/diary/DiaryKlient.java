package com.diary;
import java.util.Scanner;

public class DiaryKlient {
    private static Scanner scanner = new Scanner(System.in);
    private static DiaryRegistry register = new DiaryRegistry();

    public static void main(String[] args) {
        initializeData();  
        
        int choice = 0;
        do {
            System.out.println("\n--- Diary ---");
            System.out.println("1. Register new author");
            System.out.println("2. Print all authors");
            System.out.println("3. Register new entry");
            System.out.println("4. Print all entries");
            System.out.println("5. Delete entry");
            System.out.println("6. Find entries by author");
            System.out.println("7. Find entries by date");
            System.out.println("8. Search for entries in a date range");
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
                showAllAuthors();
                break;
                case 3:
                registerDiary();
                break;
                case 4:
                showAllEntries();
                break;
                case 5:
                deleteEntry();
                break;
                case 6:
                findAuthorEntries();
                break;
                case 7:
                findEntriesByDate();
                break;
                case 8:
                findEntriesByDateRange();
                break;
                default:
                    System.out.println("Invalid selection, try again.");
            }
        } while (choice != 0);
    }

    private static void initializeData() {
        
        Author author1 = new Author("First author");
        Author author2 = new Author("Second author");
        Author author3 = new Author("Third author");
        
        register.registerAuthor(author1);
        register.registerAuthor(author2);
        register.registerAuthor(author3);
        
        
        Diary entry1 = new Diary("2025-11-11", "Title1",  "Entry1");
        entry1.addAuthor(author1);
        register.registerEntry(entry1);
        
        Diary entry2 = new Diary("2025-11-12", "Title2",  "Entry2");
        entry2.addAuthor(author2);
        register.registerEntry(entry2);
        
        Diary entry3 = new Diary("2025-11-11", "Title3",  "Entry3");
        entry3.addAuthor(author3);
        register.registerEntry(entry3);
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
        System.out.print("Entry title: ");
        String title = scanner.nextLine();
        System.out.print("Entry text: ");
        String content = scanner.nextLine();

        Diary entry = new Diary(date, title, content);

        while (true) {
            System.out.print("Add author to entry (blank for unknown author): ");
            String name = scanner.nextLine();
            if (name.isBlank()) {
                name = "Unknown";
                entry.addAuthor(name);
                System.out.println("Author added.");
                break; 
            }

            Author author = register.findAuthor(name);
            if (author != null) {
                entry.addAuthor(author.getName()); 
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
    int i = 1;
    for (Author a : register.getAuthors()) {
      
      System.out.println("Author " + i + ": " + a);
      i += 1;
      
    }
  }  

    private static void showAllEntries() {
        System.out.println("\n-- All entries --");
        for (Diary d : register.getEntries()) {
            System.out.println(d);
        }
    }

    private static void deleteEntry() {
        showAllEntries();
        if (amountOfEntries() == 1) {
            System.out.println("Delete the only entry? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                register.deleteEntry(0);
                System.out.println("Entry deleted.");
            } 
            else {
                System.out.println("Deletion cancelled.");
            }
        }
        else {
            System.out.print("Enter index of entry to delete (1,"+amountOfEntries()+"): ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            boolean removed = register.deleteEntry(index);
            if (removed) {
                System.out.println("Entry deleted.");
            } else {
                System.out.println("No entry found for that index.");
            }
        }
    }

    private static int amountOfEntries() {
        return register.getEntries().size();
    }

    private static void findAuthorEntries() {
        System.out.print("Write author name: ");
        String name = scanner.nextLine();
        Author author = register.findAuthor(name);
        if (author != null) {
            System.out.println("\n-- Entries by " + author.getName() + " --");
            for (Diary d : register.getEntries()) {
                if (d.getAuthors().contains(author)) {
                    System.out.println(d);
                }
            }
        } else {
            System.out.println("No author found.");
        }
    }
        private static String withoutDate(Diary d) { // makes sure the date is not printed twice
        String s = d.toString();
        int nl = s.indexOf('\n');
        return (nl >= 0) ? s.substring(nl + 1).trim() : s.trim();
    }

    private static void findEntriesByDate() {
        System.out.print("Enter date (e.g. 2025-11-11): ");
        String date = scanner.nextLine();
        System.out.println("\n-- Entries on " + date + " --");
        for (Diary d : register.getEntries()) {
        if (date.equals(d.getDate())) {
            System.out.println(withoutDate(d) + "\n");
        }
        }
    }

    private static void findEntriesByDateRange() {
    System.out.print("Enter start date (e.g. 2025-11-10): ");
    String startDate = scanner.nextLine();
    System.out.print("Enter end date (e.g. 2025-11-20): ");
    String endDate = scanner.nextLine();
    System.out.println("\n-- Entries from " + startDate + " to " + endDate + " --");
    for (Diary d : register.getEntries()) {
      String dt = d.getDate();
      if (dt.compareTo(startDate) >= 0 && dt.compareTo(endDate) <= 0) {
        System.out.println(d + "\n");
      }
    }
  }
}
