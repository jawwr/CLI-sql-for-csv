package core.fileWorker;

import java.io.File;

public class FileWorker {
    private final FileReader reader;
    private final FileWriter writer;

    public FileWorker() {
        this.reader = new Reader(new File("").getAbsolutePath());
        this.writer = new Writer(new File("").getAbsolutePath());
    }

    public FileWorker(String directory) {
        this.reader = new Reader(directory);
        this.writer = new Writer(directory);
    }

    public String[][] readFile(String fileName){
        var values = this.reader.readFile(fileName);
        String[][] tableValues = new String[values.length][];
        for (int i = 0; i < values.length; i++) {
            var lineValues = values[i].split(";");
            tableValues[i] = lineValues;
        }
        return tableValues;
    }
}
