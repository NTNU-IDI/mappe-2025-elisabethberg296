package com.diary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * DiaryKlient class that makes use of the DiaryRegistry to manage authors and diary entries.
 * It provides a console-based interface for users to interact with the diary system.
 */
public class DiaryClient {

  private static Scanner scanner = new Scanner(System.in);
  private static DiaryRegistry register = new DiaryRegistry();

  /**
   * Initializes authors and example entries.
   */
  public static void init() {
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

    Diary entry3 = new Diary("2025-11-11 20:00", "Title3", "Entry3");
    entry3.addAuthor(author3);
    register.registerEntry(entry3);
  }

  /**
   * Starts the program loop and reads user actions.
   */
  public static void start() {
    int choice = -1;

    do {
      printMenu();

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
        case 10:
          authorStatistics();
          break;
        case 0:
          System.out.println("Exited!");
          break;
        default:
          System.out.println("Invalid selection, try again.");
      }

    } while (choice != 0);
  }

    private static void printMenu() {
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
    System.out.println("10. Statistics for each author");
    System.out.println("0. Exit");
    System.out.print("Select: ");
  }


  private static void registerAuthor() {
    while (true) {
      System.out.print("Name: ");
      String name = scanner.nextLine().trim();

      if (name.isBlank()) {
        System.out.println("Author name cannot be blank. Try again.");
        continue;
      }

      if (register.findAuthor(name) != null) {
        System.out.println("Author " + name + " already exists. Try a different name.");
        continue; 
      }
      register.registerAuthor(new Author(name));
      System.out.println("Author registered.");
      break;
    }
  }

private static void registerDiary() {
    String date;
    
    while (true) {
      System.out.print("Date and time (e.g. 2025-11-12 20:00): ");
      date = scanner.nextLine();
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime.parse(date, formatter);
        break;
      } catch (Exception e) {
        System.out.println("Invalid date format. Try again.");
      }
    }
    System.out.print("Entry title: ");
    String title = scanner.nextLine();
    System.out.print("Entry text: ");
    String content = scanner.nextLine();

    Diary entry = new Diary(date, title, content);

    while (true) {
      showAllAuthors();
      System.out.print("Add author to the entry: ");
      String name = scanner.nextLine();

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

  private static int amountOfEntries() {
    return register.getEntries().size();
  }

  private static void deleteEntry() {
    showAllEntries();

    int amount = amountOfEntries();
    switch (amount) {
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
        System.out.print("Enter index of entry to delete (1," + amount + "): ");
        try {
          int index = Integer.parseInt(scanner.nextLine()) - 1;
          boolean removed = register.deleteEntry(index);
          if (removed) {
            System.out.println("Entry deleted.");
          } else {
            System.out.println("No entry found for that index.");
          }
        } catch (NumberFormatException e) {
          System.out.println("Invalid input. Deletion cancelled.");
        }
        break;
    }
  }


  private static void findAuthorEntries() {
    while (true) {

      System.out.print("Write author name: (blank to cancel) ");
      String name = scanner.nextLine();

      if (name.isBlank()) {
        System.out.println("Cancelled.");
        break;
      }
      Author author = register.findAuthor(name);
      if (author != null) {
        System.out.println("\n-- Entries by " + author.getName() + " --");
        register.getEntries().stream()
          .filter(d -> d.getAuthors().contains(author))
          .forEach(d -> System.out.println(d));
          break;
      } else {
        System.out.println("No author found.");
      }
    }
  }

  private static String withoutDate(Diary d) { // makes sure the date is not printed twice
    String s = d.toString();
    int nl = s.indexOf('\n');
    return (nl >= 0) ? s.substring(nl + 1).trim() : s.trim();
  }

  private static LocalDate checkValidDate(String dateInput) {
    while (true) {
      System.out.print(dateInput);
      String input = scanner.nextLine().trim();
      try {
        return LocalDate.parse(input);
      } catch (java.time.format.DateTimeParseException e) {
        System.out.println("Invalid date format. Use yyyy-MM-dd. Try again.");
      }
    }
  }

  private static void findEntriesByDate() {
    LocalDate date = checkValidDate("Enter date (e.g. 2025-11-11): ");

    System.out.println("\n-- Entries on " + date + " --");
    register.getEntries().stream()
        .filter(e -> e.getDate().toLocalDate().equals(date))
        .forEach(e -> System.out.println(withoutDate(e) + "\n"));
  }

  private static void findEntriesByDateRange() {
    LocalDate start = checkValidDate("Enter start date (e.g. 2025-11-10): ");
    LocalDate end = checkValidDate("Enter start date (e.g. 2025-11-11): ");

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
    if (register.getEntries().stream()
        .noneMatch(d -> d.getContent().toLowerCase().contains(word) 
             || d.getTitle().toLowerCase().contains(word))) {
      System.out.println("No entries found containing the word \"" + word + "\".");
    }
  }

}
