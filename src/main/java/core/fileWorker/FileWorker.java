package core.fileWorker;

import core.structure.Table;
import core.utils.Constants;

public class FileWorker {
    private final FileReader reader;
    private final FileWriter writer;

    public FileWorker() {
        this.reader = new Reader(Constants.PATH);
        this.writer = new Writer(Constants.PATH);
    }

    public FileWorker(String directory) {
        this.reader = new Reader(directory);
        this.writer = new Writer(directory);
    }

    public String[][] readFile(String fileName) {
        var values = this.reader.readFile(fileName);
        String[][] tableValues = new String[values.length][];
        for (int i = 0; i < values.length; i++) {
            var lineValues = values[i].split(";");
            tableValues[i] = lineValues;
        }
        return tableValues;
    }

    public void writeFile(String[][] values, String fileName, Table table) {
        writer.writeFile(values, fileName);
    }
}
