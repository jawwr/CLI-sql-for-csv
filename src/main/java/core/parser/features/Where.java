package core.parser.features;

import core.structure.Table;
import core.utils.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//TODO сделать строковые параметры с кавычками, числовые оставить
public class Where implements Feature {
    @Override
    public Table parse(List<String> args, Table table) {
        String[][] values = null;
        for (int i = 0; i < args.size() - 1; i++) {
            var operation = args.get(i + 1);
            if (isAvailableOperations(operation)) {
                values = filter(table, operation, args.get(i), args.get(i + 2));
                i++;
            }

            if (operation.equalsIgnoreCase("or")) {
                try {
                    i += 2;
                    operation = args.get(i + 1);
                    var filterResult = filter(table, operation, args.get(i), args.get(i + 2));
                    values = concatFiltersWithOr(values, filterResult);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Missing arguments after 'or'");
                }
            } else if (operation.equalsIgnoreCase("and")) {
                try {
                    i += 2;
                    operation = args.get(i + 1);
                    var filterResult = filter(table, operation, args.get(i), args.get(i + 2));
                    values = concatFiltersWithAnd(values, filterResult);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Missing arguments after 'and'");
                }
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

        for (int i = 0; i < minRowNum; i++) {
            boolean isContains = contains(minLengthArray[i], maxLengthArray);
            if (isContains) {
                result[currIndex] = minLengthArray[i];
                currIndex++;
            }
        }

        return Arrays.stream(result).filter(Objects::nonNull).toList().toArray(new String[0][0]);
    }

    private boolean contains(String[] arrayFrom, String[][] compareArray) {
        for (String[] arrCompare : compareArray) {
            if (Arrays.deepEquals(arrayFrom, arrCompare)) {
                return true;
            }
        }
        return false;
    }

    private String[][] filter(Table table, String operation, String columnName, String compareValue) {
        int index = table.getColumnIndex(columnName);
        return filterValueByIndex(table.getValues().clone(), index, operation, compareValue);
    }

    private String[][] concatFiltersWithOr(String[][]... allFilterResult) {
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
            case "<>" -> notEqualsValues(values, index, value);
            default -> null;
        };
    }

    private String[][] notEqualsValues(String[][] values, int index, String equalsValue) {
        values = Arrays.stream(values).filter(Objects::nonNull).toList().toArray(new String[0][0]);
        for (int i = 0; i < values.length; i++) {
            var value = values[i][index];
            if (value.equalsIgnoreCase(equalsValue)) {
                values[i] = null;
            }
        }
        return Arrays.stream(values).filter(Objects::nonNull).toList().toArray(new String[0][0]);
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
        return Constants.AVAILABLE_OPERATION.contains(operationName);
    }
}
