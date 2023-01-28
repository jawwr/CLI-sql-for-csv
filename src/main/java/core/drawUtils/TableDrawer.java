package core.drawUtils;

import core.structure.Column;
import core.structure.Table;
import core.utils.Tuple;

import java.util.ArrayList;
import java.util.List;

public class TableDrawer {
    public static void draw(Table table) {
        var columns = table.getStructure().columnList();
        List<Tuple<String, Integer>> space = getColumnsSize(table);
        drawHeader(columns, space);
        drawValues(table.getValues(), space);
    }

    private static void drawValues(String[][] values, List<Tuple<String, Integer>> space) {
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                int width = space.get(j).second();
                System.out.print("║");
                System.out.print(setSpace(values[i][j], width));
                if (j == values[i].length - 1) {
                    System.out.println("║");
                }
            }
            if (i != values.length - 1) {
                var border = getBetweenBorder(space);
                System.out.println(border);
            }
        }
        var border = getBottomBorder(space);
        System.out.println(border);
    }

    private static void drawHeader(List<Column> columns, List<Tuple<String, Integer>> space) {
        System.out.println(getUpBorder(space));
        for (int i = 0; i < columns.size(); i++) {
            System.out.print("║");
            var name = columns.get(i).getName();
            System.out.print(setSpaceCenter(name, space.get(i).second()));
            if (i == columns.size() - 1) {
                System.out.println("║");
            }
        }
        System.out.println(getBetweenBorder(space));
    }

    private static List<Tuple<String, Integer>> getColumnsSize(Table table) {
        List<Tuple<String, Integer>> spaces = new ArrayList<>();
        for (int i = 0; i < table.getStructure().columnList().size(); i++) {
            var column = table.getStructure().columnList().get(i);
            var columnValue = table.getValues();
            int width = Math.max(column.getName().trim().length() + 2, getMaxValueSizeInColumn(columnValue, i) + 2);
            spaces.add(new Tuple<>(column.getName().trim(), width));
        }

        return spaces;
    }

    private static int getMaxValueSizeInColumn(String[][] columnValue, int index) {
        int max = 0;
        for (String[] value : columnValue) {
            if (value[index].length() > max) {
                max = value[index].length();
            }
        }
        return (max ^ 1) != 0 ? max + 1 : max;
    }

    private static String setSpace(String value, int width) {
        var returnedString = " ".repeat(Math.max(0, width - value.length()));

        return returnedString + value;
    }

    private static String getUpBorder(List<Tuple<String, Integer>> values) {
        return getBorderByPattern(values, "╔", "═", "╦", "╗");
    }

    private static String getBetweenBorder(List<Tuple<String, Integer>> values) {
        return getBorderByPattern(values, "╠", "═", "╬", "╣");
    }

    private static String getBottomBorder(List<Tuple<String, Integer>> values) {
        return getBorderByPattern(values, "╚", "═", "╩", "╝");
    }

    private static String getBorderByPattern(List<Tuple<String, Integer>> values, String leftSymbol, String centerSymbol, String delimiterSymbol, String rightSymbol) {
        StringBuilder border = new StringBuilder(leftSymbol);
        int i = 0;
        for (var columnName : values) {
            int size = columnName.second();
            border.append(centerSymbol.repeat(Math.max(0, size)));
            if (i != values.size() - 1) {
                border.append(delimiterSymbol);
            }
            i++;
        }
        border.append(rightSymbol);

        return border.toString();
    }

    private static String setSpaceCenter(String value, int width) {
        int widthWithoutSpace = width - value.strip().trim().length() - 2;
        var spaceAround = " ".repeat(Math.max(0, (width - value.length()) / 2));
        var returnedString = (widthWithoutSpace % 2) == 0 ? spaceAround : spaceAround + " ";
        returnedString += value;
        returnedString += spaceAround;

        return returnedString;
    }
}
