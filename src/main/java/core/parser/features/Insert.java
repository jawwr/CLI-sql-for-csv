package core.parser.features;

import core.repos.TableRepo;
import core.structure.Table;
import core.utils.ListExtension;

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
        TableRepo.writeFile(table.toCSV(), table.getName());
    }

    private String[][] addNewValue(String[][] values, String[] addValue) {
        String[][] newValues = new String[values.length + 1][];

        System.arraycopy(values, 0, newValues, 0, values.length);
        newValues[values.length] = addValue;

        return newValues;
    }

    private List<String> getValues(List<String> args, Table table) {
        if (!ListExtension.containsIgnoreCase(args, "values")) {
            throw new IllegalArgumentException("Values not exist");
        }
        int valuesStartIndex = ListExtension.indexOfIgnoreCase(args, "values") + 1;
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
        if (!ListExtension.containsIgnoreCase(args, "into")) {
            throw new IllegalArgumentException("Into not exist");
        }

        int intoIndex = ListExtension.indexOfIgnoreCase(args, "into") + 1;

        return args.get(intoIndex);
    }
}
