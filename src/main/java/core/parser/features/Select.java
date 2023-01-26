package core.parser.features;

import core.structure.Column;
import core.structure.Table;
import core.structure.TableStructure;

import java.util.ArrayList;
import java.util.List;

public class Select implements Feature {
    @Override
    public Table parse(List<String> args, Table table) {
        List<Integer> indexes = findColumnsIndexes(args, table);
        String[][] newValues = selectColumn(table, indexes);
        List<Column> newColumns = selectHeader(table, indexes);

        TableStructure newStructure = new TableStructure(newColumns);
        Table newTable = new Table(newStructure);
        newTable.setValues(newValues);

        return newTable;
    }

    private List<Column> selectHeader(Table table, List<Integer> indexes) {
        var columns = table.getStructure().columnList();
        List<Column> result = new ArrayList<>();

        for (int index : indexes) {
            result.add(columns.get(index));
        }

        return result;
    }

    private List<Integer> findColumnsIndexes(List<String> args, Table table) {
        List<Integer> indexes = new ArrayList<>();
        for (var name : args) {
            indexes.add(getColumnIndex(table, name));
        }

        return indexes;
    }

    private String[][] selectColumn(Table table, List<Integer> indexes) {
        var values = table.getValues();
        String[][] result = new String[values.length][];
        var columnCount = indexes.size();

        for (int i = 0; i < values.length; i++) {
            String[] value = new String[columnCount];
            for (int j = 0; j < columnCount; j++) {
                value[j] = values[i][indexes.get(j)];
            }
            result[i] = value;
        }

        return result;
    }

    private int getColumnIndex(Table table, String name) {
        var columns = table.getStructure().columnList();
        var column = columns.stream().filter(x -> x.getName().equals(name)).findFirst().get();

        return columns.indexOf(column);
    }
}
