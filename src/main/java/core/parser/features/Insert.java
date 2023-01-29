package core.parser.features;

import core.repos.FileWorker;
import core.structure.Column;
import core.structure.Table;

import java.util.ArrayList;
import java.util.List;

public class Insert implements Feature {
    @Override
    public Table parse(List<String> args, Table table) {
        String tableName = findTableName(args);
        Table newTable = getAllTable(tableName);
        List<String> insertValues = getValues(args, newTable);
        String[][] values = addNewValue(newTable.getValues(), insertValues.toArray(new String[0]));

        newTable.setValues(values);

        addValueInFile(newTable);

        return newTable;
    }

    private Table getAllTable(String name) {
        From from = new From();
        return from.parse(List.of(name), null);
    }

    private void addValueInFile(Table table) {
        FileWorker.writeFile(table.toCSV(), table.getName());
    }

    private String[][] addNewValue(String[][] values, String[] addValue) {
        String[][] newValues = new String[values.length + 1][];

        System.arraycopy(values, 0, newValues, 0, values.length);
        newValues[values.length] = addValue;

        return newValues;
    }

    private List<String> getValues(List<String> args, Table table) {
        if (!containsIgnoreCase(args, "values")) {
            throw new IllegalArgumentException("Values not exist");
        }
        int valuesStartIndex = indexOfIgnoreCase(args, "values") + 1;
        List<String> values = new ArrayList<>();
        for (int i = valuesStartIndex; i < args.size(); i++) {
            values.add(args.get(i));
        }

        if (values.size() != table.getStructure().columnList().size()) {
            throw new IllegalArgumentException("Column count not match column count");
        }

        return values;
    }

    private String findTableName(List<String> args) {
        if (!containsIgnoreCase(args, "into")) {
            throw new IllegalArgumentException("Into not exist");
        }

        int intoIndex = indexOfIgnoreCase(args, "into") + 1;

        return args.get(intoIndex);
    }

    private boolean containsIgnoreCase(List<String> list, String word) {
        for (String item : list) {
            if (item.equalsIgnoreCase(word)) {
                return true;
            }
        }

        return false;
    }

    private int indexOfIgnoreCase(List<String> list, String word) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase(word)) {
                return i;
            }
        }

        return -1;
    }
}
