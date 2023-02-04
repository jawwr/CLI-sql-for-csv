package core.repos;

import core.structure.Table;
import core.utils.Constants;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableRepo {
    private static final FileReader reader = new Reader();
    private static final FileWriter writer = new Writer();

    private static String[][] readFile(String fileName) {
        var values = reader.readFile(fileName);
        String[][] tableValues = new String[values.length][];
        int length = values[0].split(";").length;
        for (int i = 0; i < values.length; i++) {
            var lineValues = values[i].split(";");
            if (lineValues.length != length) {
                List<String> list = new ArrayList<>(Arrays.asList(lineValues));
                for (int j = lineValues.length; j < length; j++){
                    list.add("null");
                }
                tableValues[i] = list.toArray(new String[0]);
            } else {
                tableValues[i] = lineValues;
            }
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

    public static void writeFile(Table table){
        writeFile(table.toCSV(), table.getName());
    }

    public static void deleteTable(String tableName) {
        try {
            Files.delete(Path.of(Constants.PATH + File.separator + tableName + ".csv"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
