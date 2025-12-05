package com.diary;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

/**
 * DiaryKlient class that makes use of the DiaryRegistry to manage authors and diary entries.
 * It provides a console-based interface for users to interact with the diary system.
 */
public class DiaryKlient {
  private static Scanner scanner = new Scanner(System.in);
  private static DiaryRegistry register = new DiaryRegistry();

  /**
   * Main method to run the DiaryKlient application.
   */
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
      System.out.println("9. Search all entries for specific word");
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
        case 9:
          findEntriesByWord();
          break;
        case 0:
          System.out.println("Exited!");
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
        
        
    Diary entry1 = new Diary("2025-11-11 10:00", "Title1", "Entry1");
    entry1.addAuthor(author1);
    register.registerEntry(entry1);
        
    Diary entry2 = new Diary("2025-11-12 09:00", "Title2", "Entry2");
    entry2.addAuthor(author2);
    register.registerEntry(entry2);
        
    Diary entry3 = new Diary("2025-11-11 20:00", "Title3",  "Entry3");
    entry3.addAuthor(author3);
    register.registerEntry(entry3);
  }

  private static void registerAuthor() {
    while (true) {
      System.out.print("Name: ");
      String input = scanner.nextLine();

      try {
        String name = Optional.ofNullable(input)
            .map(String::trim)
            .filter(n -> !n.isEmpty())
            .orElseThrow(() -> new IllegalArgumentException(
                "You cannot register an author without a name. Try again."));

        register.registerAuthor(new Author(name));
        System.out.println("Author registered.");
        return; 
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private static void registerDiary() {
    System.out.print("Date and time (e.g. 2025-11-12 20:00): ");
    String date = scanner.nextLine();
    System.out.print("Entry title: ");
    String title = scanner.nextLine();
    System.out.print("Entry text: ");
    String content = scanner.nextLine();

    Diary entry = new Diary(date, title, content);

    while (true) {
      System.out.print("Add author to entry (blank for unknown): ");
      String name = scanner.nextLine();
      if (name.isBlank()) {
        Author unknown = new Author("Unknown");
        register.registerAuthor(unknown);
        entry.addAuthor(unknown);
        break;
      }

      Author author = register.findAuthor(name);
      if (author != null) {
        entry.addAuthor(author); 
        break;
      } else {
        System.out.println("Author not found. Add this person as a new author? (yes/no): ");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
          Author newAuthor = new Author(name);
          register.registerAuthor(newAuthor);
          entry.addAuthor(newAuthor);
          break;
        } else {
          System.out.println("Failed, try again.");
        }
      }
    }

    register.registerEntry(entry);
    System.out.println("Entry registered.");
  }

  private static void showAllAuthors() {
    System.out.println("\n-- All authors --");
    register.getAuthors().stream()
      .forEach(a -> System.out.println(a));
  }

  private static void showAllEntries() {
    System.out.println("\n-- All entries --");
    register.getEntries().stream()
        .sorted((e1, e2) -> e2.getDate().compareTo(e1.getDate())) // sorts by date
        .forEach(System.out::println);
  }

  private static void deleteEntry() {
    showAllEntries();

    switch (amountOfEntries()) {
      case 1:
        System.out.println("Delete the only entry? (yes/no): ");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
          register.deleteEntry(0);
          System.out.println("Entry deleted.");
        } else {
          System.out.println("Deletion cancelled.");
        }      
        break;
      case 0:
        System.out.println("No entries to delete.");
        break;
      default:
        System.out.print("Enter index of entry to delete (1," + amountOfEntries() + "): ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        boolean removed = register.deleteEntry(index);
        if (removed) {
          System.out.println("Entry deleted.");
        } else {
          System.out.println("No entry found for that index.");
        }       
        break;
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
      register.getEntries().stream()
        .filter(d -> d.getAuthors().contains(author))
        .forEach(d -> System.out.println(d));
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
    String dateInput = scanner.nextLine();

    LocalDate date = LocalDate.parse(dateInput);

    System.out.println("\n-- Entries on " + date + " --");
    register.getEntries().stream()
        .filter(e -> e.getDate().toLocalDate().equals(date))
        .forEach(e -> System.out.println(withoutDate(e) + "\n"));
  }

  private static void findEntriesByDateRange() {
    System.out.print("Enter start date (e.g. 2025-11-10): ");
    LocalDate start = LocalDate.parse(scanner.nextLine());
    System.out.print("Enter end date (e.g. 2025-11-20): ");
    LocalDate end = LocalDate.parse(scanner.nextLine());
    System.out.println("\n-- Entries from " + start + " to " + end + " --");
    register.getEntries().stream()
      .filter(d -> {
        LocalDate entryDate = d.getDate().toLocalDate();
        return !entryDate.isBefore(start) && !entryDate.isAfter(end); 
      })
      .forEach(d -> System.out.println(d + "\n"));
  }

  private static void findEntriesByWord() {
    System.out.print("Enter word to search for: ");
    String word = scanner.nextLine().toLowerCase();
    System.out.println("\n-- Entries containing the word \"" + word + "\" --");
    register.getEntries().stream()
      .filter(d -> d.getContent().toLowerCase().contains(word) 
             || d.getTitle().toLowerCase().contains(word))
      .forEach(d -> System.out.println(d + "\n"));
  }

}
