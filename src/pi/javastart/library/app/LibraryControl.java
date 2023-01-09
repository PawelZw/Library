package pi.javastart.library.app;

import pi.javastart.library.exception.DataExportException;
import pi.javastart.library.exception.DataImportException;
import pi.javastart.library.exception.InvalidDataException;
import pi.javastart.library.exception.NoSuchOptionException;
import pi.javastart.library.io.ConsolePrinter;
import pi.javastart.library.io.DataReader;
import pi.javastart.library.io.file.FileManager;
import pi.javastart.library.io.file.FileManagerBuilder;
import pi.javastart.library.model.Book;
import pi.javastart.library.model.Library;
import pi.javastart.library.model.Magazine;
import pi.javastart.library.model.Publication;
import pi.javastart.library.model.comparator.AlphabeticalTitleComparator;

import java.util.Arrays;
import java.util.InputMismatchException;

public class LibraryControl {

    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;

    private Library library;

    LibraryControl() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            library = fileManager.importData();
            printer.printLine("Imported file data");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("New database initiated.");
            library = new Library();
        }
    }

    void controlLoop() {
        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_MAGAZINE:
                    addMagazine();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MAGAZINES:
                    printMagazines();
                    break;
                case DELETE_BOOK:
                    deleteBook();
                    break;
                case DELETE_MAGAZINE:
                    deleteMagazine();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("There is no such option, please re-enter: ");
            }
        } while (option != Option.EXIT);
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage() + ", enter again:");
            } catch (InputMismatchException ignored) {
                printer.printLine("You entered a value that is not a number, please re-enter:");
            }
        }

        return option;
    }

    private void printOptions() {
        printer.printLine("Select an option: ");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());
        }
    }

    private void addBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            library.addPublication(book);
        } catch (InputMismatchException e) {
            printer.printLine("Failed to create book, invalid data");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Capacity limit reached, no more book can be added");
        }
    }
    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addPublication(magazine);
        } catch (InputMismatchException e) {
            printer.printLine("Failed to create vault, invalid data");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Capacity limit reached, no more magazine can be added");
        }
    }

    private void printBooks() {
        Publication[] publications = getSortedPublications();
        printer.printBooks(publications);
    }

    private void printMagazines() {
        Publication[] publications = getSortedPublications();
        printer.printMagazines(publications);
    }

    private Publication[] getSortedPublications() {
        Publication[] publications = library.getPublications();
        Arrays.sort(publications, new AlphabeticalTitleComparator());
        return publications;
    }

    private void deleteMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            if (library.removePublication(magazine))
                printer.printLine("Magazine removed.");
            else
                printer.printLine("No magazine indicated.");
        } catch (InputMismatchException e) {
            printer.printLine("Failed to create magazine, invalid data");
        }
    }

    private void deleteBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            if (library.removePublication(book))
                printer.printLine("Book removed.");
            else
                printer.printLine("No book indicated.");
        } catch (InputMismatchException e) {
            printer.printLine("Failed to create book, invalid data");
        }
    }

    private void exit() {
        try {
            fileManager.exportData(library);
            printer.printLine("Successfully exported data to a file");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        dataReader.close();
        printer.printLine("Program end!");
    }

    private enum Option {
        EXIT(0, "Exit the program"),
        ADD_BOOK(1, "Adding a book"),
        ADD_MAGAZINE(2,"Adding a magazine/newspaper"),
        PRINT_BOOKS(3, "View available books"),
        PRINT_MAGAZINES(4, "Display of available magazines/newspapers"),
        DELETE_BOOK(5, "Remove the book"),
        DELETE_MAGAZINE(6, "Delete the magazine");

        private int value;
        private String description;

        Option(int value, String desc) {
            this.value = value;
            this.description = desc;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch(ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("No id option " + option);
            }
        }
    }
}
