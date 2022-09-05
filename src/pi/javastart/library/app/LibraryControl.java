package pi.javastart.library.app;

import pi.javastart.library.io.DataReader;
import pi.javastart.library.model.Book;
import pi.javastart.library.model.Library;
import pi.javastart.library.model.Magazine;

public class LibraryControl {



    // user communication variable
    private DataReader dataReader = new DataReader();

    // library collecting data
    private Library library = new Library();

    /*
     * The main method of the program that allows you to choose options and interact
     */
    public void controlLoop() {
        Option option;

        do {
            printOptions();
            option = Option.createFromInt(dataReader.getInt());
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
                case EXIT:
                    exit();
                    break;
                default:
                    System.out.println("Nie ma takiej opcji, wprowadź ponownie: ");
            }
        } while(option != Option.EXIT);
    }

    private void printMagazines() {
        library.printMagazines();
    }

    private void addMagazine() {
        Magazine magazine = dataReader.readAndCreateMagazine();
        library.addMagazine(magazine);

    }

    private void printOptions() {
        System.out.println("Wybierz opcję: ");
        for(Option value : Option.values()) {
            System.out.println(value);
        }
    }

    private void addBook() {
        Book book = dataReader.readAndCreateBook();
        library.addBook(book);
    }

    private void printBooks() {
        library.printBooks();
    }

    private void exit() {
        System.out.println("Koniec programu");
        // we close the input stream
        dataReader.close();
    }
}

