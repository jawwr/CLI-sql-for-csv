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

        TableStructure newStructure = new TableStructure(newColumns, table.getAlias());
        Table newTable = new Table(table.getName(), newStructure);
        newTable.setValues(newValues);

        return newTable;
    }

    private List<Column> selectHeader(Table table, List<Integer> indexes) {
        var columns = table.getStructure().getColumnList();
        List<Column> result = new ArrayList<>();

        for (int index : indexes) {
            result.add(columns.get(index));
        }

        return result;
    }

    private List<Integer> findColumnsIndexes(List<String> args, Table table) {
        if (args.get(0).equals("*")) {
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < table.getStructure().getColumnList().size(); i++) {
                result.add(i);
            }
            return result;
        }
        List<Integer> indexes = new ArrayList<>();
        for (var name : args) {
            if (name.contains("*")) {
                var columns = table.getStructure().getColumnList()
                        .stream()
                        .filter(x -> x.getAllName().contains(name.split("\\.")[0]))
                        .toList();
                for (Column column : columns) {
                    indexes.add(table.getColumnIndex(column.getName()));
                }
            } else {
                indexes.add(table.getColumnIndex(name));
            }
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
}
