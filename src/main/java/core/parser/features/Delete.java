package core.parser.features;

import core.repos.TableRepo;
import core.structure.Column;
import core.structure.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Delete implements Feature {
    @Override
    public Table parse(List<String> args, Table table) {
        Table allTable = getAllTable(table.getName());
        String[][] values = deleteValues(allTable.getValues(), table.getValues());

        allTable.setValues(values);

        addValueInFile(values, allTable.getName(), allTable);

        return allTable;
    }

    private void addValueInFile(String[][] values, String tableName, Table table) {
        String[][] newValues = new String[values.length + 1][];
        newValues[0] = getHeader(table);
        System.arraycopy(values, 0, newValues, 1, values.length);
        TableRepo.writeFile(newValues, tableName);
    }

    private String[] getHeader(Table table) {
        var columns = table.getStructure().columnList();
        List<String> columnName = new ArrayList<>();
        for (Column column : columns) {
            columnName.add(column.getName());
        }

        return columnName.toArray(new String[0]);
    }

    private Table getAllTable(String name) {
        From from = new From();
        return from.parse(List.of(name), null);
    }

    private String[][] deleteValues(String[][] values, String[][] deleteValues) {
        for (int i = 0; i < values.length; i++) {
            for (String[] deleteValue : deleteValues) {
                if (Arrays.deepEquals(values[i], deleteValue)) {
                    values[i] = null;
                    break;
                }
            }
        }

        return Arrays.stream(values).filter(Objects::nonNull).toList().toArray(new String[0][0]);
    }
}
