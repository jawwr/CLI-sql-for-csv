package core.parser.features;

import core.structure.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Where implements Feature {
    private static final String[] operations = {"=", "<", ">",};

    @Override
    public Table parse(List<String> args, Table table) {
        List<String[][]> newValues = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            var operation = args.get(i + 1);
            if (isAvailableOperations(operation)) {
                var columnName = args.get(i);
                int index = getColumnIndex(table, columnName);
                String[][] values = filterValueByIndex(table, index, operation, args.get(i + 2));
                newValues.add(values);
                i += 2;
            }
        }
        var concat = concatFilters(newValues);
        table.setValues(concatFilters(newValues));

        return table;
    }

    private String[][] concatFilters(List<String[][]> allFilterResult) {
        var rowNum = getRowCount(allFilterResult);
        String[][] result = new String[rowNum][];
        int row = 0;
        for (String[][] rows : allFilterResult) {
            for (String[] values : rows) {
                result[row] = values;
                row++;
            }
        }

        return result;
    }

    private int getRowCount(List<String[][]> allRows) {
        int sum = 0;
        for (String[][] rows : allRows) {
            sum += rows.length;
        }

        return sum;
    }

    private String[][] filterValueByIndex(Table table, int index, String operation, String value) {
        return switch (operation) {
            case "=" -> equalsValues(table, index, value);
            case "<" -> compareValues(table, index, value, "<");
            case ">" -> compareValues(table, index, value, ">");
            default -> null;
        };
    }

    private String[][] compareValues(Table table, int index, String compareValue, String operation) {
        var values = table.getValues();
        for (int i = 0; i < values.length; i++) {
            int intCompareValue = Integer.parseInt(compareValue);
            int intValue = Integer.parseInt(values[i][index]);
//            var compareResult = values[i][index].compareTo(compareValue);
            boolean filter;
            if (operation.equals(">")) {
                filter = intCompareValue < intValue;
            }else {
                filter = intCompareValue > intValue;
            }

            if (!filter){
                values[i] = null;
            }
        }
        return Arrays.stream(values).filter(Objects::nonNull).toList().toArray(new String[0][0]);
    }

    private String[][] equalsValues(Table table, int index, String equalsValue) {
        var values = table.getValues();
        for (int i = 0; i < values.length; i++) {
            if (!values[i][index].equals(equalsValue)) {
                values[i] = null;
            }
        }
        return Arrays.stream(values).filter(Objects::nonNull).toList().toArray(new String[0][0]);
    }

    private boolean isAvailableOperations(String operationName) {
        return Arrays.asList(operations).contains(operationName);
    }

    private int getColumnIndex(Table table, String name) {
        var columns = table.getStructure().columnList();
        var column = columns.stream().filter(x -> x.getName().equalsIgnoreCase(name)).findFirst().get();

        return columns.indexOf(column);
    }
}
