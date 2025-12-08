# Portfolio project IDATT1003

STUDENT NAME = "Elisabeth Berg" 
STUDENT ID = "157352"

## Project description
This project is a console-based diary system written in Java. The program lets the user register authors, create diary entries with date, time, title, and content, search for entries, and view statistics. It demonstrates basic object-oriented design using classes such as Diary, Author, Main, DiaryClient and DiaryRegistry.

## Project structure
This project uses standard Maven-structure. 
The main project is located in src/main/java/com/diary.
Tests are located in src/test/java/com/diary. 

## Link to repository
https://github.com/NTNU-IDI/mappe-2025-elisabethberg296

## How to run the project
Open the project in VSCode.
You can run the project either via Maven or directly by running the Main class.
The program starts in the console and waits for user input. You can enter data such as:
- Date (format: YYYY-MM-DD)
- Time (format: HH:MM)
- Title (a short text string)
- Entry text (your main content string)
After entering an index or selecting a menu option, the program will display the corresponding output in text form.
The program will continue running until you chose the option to close it. Note: all input data is temporary and will not be saved after closing the program.

## How to run the tests
To run all tests, you can either:
- Use Maven: run mvn test in the project directory.
- Run tests directly from your IDE (e.g., right-click the test class and select "Run").
The test results will be displayed in the console, showing which tests passed or failed.

## References
No references.
