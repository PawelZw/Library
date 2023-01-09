package pi.javastart.library.io;

import pi.javastart.library.model.Book;
import pi.javastart.library.model.Magazine;

import java.util.Scanner;

public class DataReader {
    private Scanner sc = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public void close() {
        sc.close();
    }

    public int getInt() {
        try {
            return sc.nextInt();
        } finally {
            sc.nextLine();
        }
    }
    public String getString() {
        return sc.nextLine();
    }

    public Book readAndCreateBook() {
        printer.printLine("Title: ");
        String title = sc.nextLine();
        printer.printLine("Author: ");
        String author = sc.nextLine();
        printer.printLine("Publishing house: ");
        String publisher = sc.nextLine();
        printer.printLine("ISBN: ");
        String isbn = sc.nextLine();
        printer.printLine("Publication date: ");
        int releaseDate = getInt();
        printer.printLine("Number of pages: ");
        int pages = getInt();

        return new Book(title, author, releaseDate, pages, publisher, isbn);
    }

    public Magazine readAndCreateMagazine() {
        printer.printLine("Title: ");
        String title = sc.nextLine();
        printer.printLine("Publishing house: ");
        String publisher = sc.nextLine();
        printer.printLine("Language: ");
        String language = sc.nextLine();
        printer.printLine("Publication date: ");
        int year = getInt();
        printer.printLine("Month: ");
        int month = getInt();
        printer.printLine("Day: ");
        int day = getInt();

        return new Magazine(title, publisher, language, year, month, day);
    }
}
