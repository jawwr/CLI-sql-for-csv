package core.parser.features;

import core.repos.TableRepo;
import core.structure.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Set implements Feature {
    @Override
    public Table parse(List<String> args, Table table) {
        Table allTable = TableRepo.readTableFromCSV(table.getName());
        List<Integer> updateRowIndex = getRowsIndexes(allTable.getValues(), table.getValues());
        setValuesByArgs(table, args);

        updateTable(allTable, table, updateRowIndex);
        TableRepo.writeFile(allTable);

        return null;
    }

    private void setValuesByArgs(Table table, List<String> args) {
        for (int i = 0; i < args.size() - 1; i++) {
            var operation = args.get(i + 1);
            if (isAvailableOperations(operation)) {
                var columnName = args.get(i);
                var newValue = args.get(i + 2);
                setNewValue(table, columnName, newValue);
                i++;
            }
        }
    }

    private void updateTable(Table allTable, Table table, List<Integer> rowIndexes) {
        String[][] allValues = allTable.getValues();
        updateTableValues(allValues, table.getValues(), rowIndexes);
        table.setValues(allValues);
    }

    private void updateTableValues(String[][] allValues, String[][] updateValues, List<Integer> rowIndexes) {
        int updateRowIndex = 0;
        for (int index : rowIndexes) {
            allValues[index] = updateValues[updateRowIndex];
            updateRowIndex++;
        }
    }

    private void setNewValue(Table table, String columnName, String newValue) {
        int index = table.getColumnIndex(columnName);
        var values = table.getValues();
        for (String[] value : values) {
            value[index] = newValue;
        }
    }

    private List<Integer> getRowsIndexes(String[][] values, String[][] rows) {
        List<Integer> indexes = new ArrayList<>();
        for (String[] row : rows) {
            for (int i = 0; i < values.length; i++) {
                if (Arrays.deepEquals(values[i], row) && !indexes.contains(i)) {
                    indexes.add(i);
                }
            }
        }

        return indexes;
    }

    private int getRowIndex(String[][] values, String[] row) {
        for (int i = 0; i < values.length; i++) {
            if (Arrays.deepEquals(values[i], row)) {
                return i;
            }
        }

        return -1;
    }

    private boolean isAvailableOperations(String operationName) {
        return operationName.equalsIgnoreCase("=");
    }
}
