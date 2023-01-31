package core.repos;

import core.structure.Table;
import core.utils.Constants;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class TableRepo {
    private static final FileReader reader;
    private static final FileWriter writer;

    static {
        reader = new Reader(Constants.PATH);
        writer = new Writer(Constants.PATH);
    }

    private static String[][] readFile(String fileName) {
        var values = reader.readFile(fileName);
        String[][] tableValues = new String[values.length][];
        for (int i = 0; i < values.length; i++) {
            var lineValues = values[i].split(";");
            tableValues[i] = lineValues;
        }
        return tableValues;
    }

    public static void writeFile(String[][] values, String fileName) {
        writer.writeFile(values, fileName);
    }

    public static Table readTableFromCSV(String name) {
        var values = readFile(name + ".csv");

        return Table.fromCSV(values, name);
    }

    public static void deleteTable(String tableName) {
        try {
            Files.delete(Path.of(Constants.PATH + File.separator + tableName + ".csv"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
