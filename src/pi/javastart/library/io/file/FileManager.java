package pi.javastart.library.io.file;

import pi.javastart.library.model.Library;

public interface FileManager {
    Library importData();
    void exportData(Library library);
}
