package pi.javastart.library.io.file;

import pi.javastart.library.exception.DataExportException;
import pi.javastart.library.exception.DataImportException;
import pi.javastart.library.model.Library;

import java.io.*;

public class SerializableFileManager implements FileManager {
    private static final String FILE_NAME = "Library.o";

    @Override
    public void exportData(Library library) {
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
             ObjectOutputStream oos = new ObjectOutputStream(fos);
        ){
            oos.writeObject(library);
        } catch (FileNotFoundException e) {
            throw new DataExportException("No file " + FILE_NAME);
        } catch (IOException e) {
            throw new DataExportException("File data writing error " + FILE_NAME);
        }
    }

    @Override
    public Library importData() {
        try (FileInputStream fis = new FileInputStream(FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            return (Library) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new DataImportException("No file " + FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("File read error " + FILE_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataImportException("Incompatible data type in the file " + FILE_NAME);
        }
    }
}