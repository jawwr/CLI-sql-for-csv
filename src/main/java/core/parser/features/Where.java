package core.parser.features;

import core.structure.Table;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Where implements Feature {
    private static final String[] operations = {"=", "<", ">", ">=", "<="};

    @Override
    public Table parse(List<String> args, Table table) {
        String[][] values = null;
        for (int i = 0; i < args.size() - 1; i++) {
            var operation = args.get(i + 1);
            if (isAvailableOperations(operation)) {
                values = filter(table, operation, args.get(i), args.get(i + 2));
            }

            if (operation.equalsIgnoreCase("or")) {
                i += 2;
                operation = args.get(i + 1);
                var filterResult = filter(table, operation, args.get(i), args.get(i + 2));
                values = concatFilters(values, filterResult);
            } else if (operation.equalsIgnoreCase("and")) {
                i += 2;
                operation = args.get(i + 1);
                var filterResult = filter(table, operation, args.get(i), args.get(i + 2));
                values = concatFiltersWithAnd(values, filterResult);
            }
        }
        table.setValues(values);

        return table;
    }

    private String[][] concatFiltersWithAnd(String[][] firstValues, String[][] secondValues) {
        var minRowNum = Math.min(firstValues.length, secondValues.length);
        var maxLengthArray = firstValues.length != minRowNum ? firstValues : secondValues;
        var minLengthArray = secondValues.length == minRowNum ? secondValues : firstValues;
        String[][] result = new String[minRowNum][];
        int currIndex = 0;

        for (int i = 0; i < minRowNum; i++){
            if (Arrays.stream(minLengthArray).toList().contains(maxLengthArray[i])){
                result[currIndex] = maxLengthArray[i];
                currIndex++;
            }
        }

        return Arrays.stream(result).filter(Objects::nonNull).toList().toArray(new String[0][0]);
    }

    private String[][] filter(Table table, String operation, String columnName, String compareValue) {
        int index = getColumnIndex(table, columnName);
        return filterValueByIndex(table.getValues().clone(), index, operation, compareValue);
    }

    private String[][] concatFilters(String[][]... allFilterResult) {
        var rowNum = getRowCount(allFilterResult);
        String[][] result = new String[rowNum][];
        int row = 0;
        for (String[][] rows : allFilterResult) {
            for (String[] values : rows) {
                result[row] = values;
                row++;
            }
        }

        return Arrays.stream(result).distinct().toList().toArray(new String[0][0]);
    }

    private int getRowCount(String[][]... allRows) {
        int sum = 0;
        for (String[][] rows : allRows) {
            sum += rows.length;
        }

        return sum;
    }

    private String[][] filterValueByIndex(String[][] values, int index, String operation, String value) {
        return switch (operation) {
            case "=" -> equalsValues(values, index, value);
            case "<" -> compareValues(values, index, value, "<");
            case ">" -> compareValues(values, index, value, ">");
            case ">=" -> compareValues(values, index, value, ">=");
            case "<=" -> compareValues(values, index, value, "<=");
            default -> null;
        };
    }

    private String[][] compareValues(String[][] values, int index, String compareValue, String operation) {
        for (int i = 0; i < values.length; i++) {
            int intCompareValue = Integer.parseInt(compareValue);
            int intValue = Integer.parseInt(values[i][index]);
            boolean filter = switch (operation) {
                case ">" -> intValue > intCompareValue;
                case "<" -> intValue < intCompareValue;
                case ">=" -> intValue >= intCompareValue;
                case "<=" -> intValue <= intCompareValue;
                default -> throw new IllegalArgumentException();
            };

            if (!filter) {
                values[i] = null;
            }
        }
        return Arrays.stream(values).filter(Objects::nonNull).toList().toArray(new String[0][0]);
    }

    private String[][] equalsValues(String[][] values, int index, String equalsValue) {
        values = Arrays.stream(values).filter(Objects::nonNull).toList().toArray(new String[0][0]);
        for (int i = 0; i < values.length; i++) {
            var value = values[i][index];
            if (!value.equalsIgnoreCase(equalsValue)) {
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
