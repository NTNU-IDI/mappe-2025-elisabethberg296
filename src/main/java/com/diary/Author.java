package com.diary;

/**
 * Author class represents the author of diary entries. 
 * It takes the name of the author as a parameter
 * and provides methods to retrieve the author's name and a string representation of the author.
 */
public class Author {

  private final String name;

  /**
   * Constructor for Author class.

   * @param name The name of the author.
   */
  public Author(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /**
   * Returns the name of the author as string.

   * @return String representation of the author.
   */
  @Override
  public String toString() {
    return name;
  }
}
