package core.drawUtils;

import core.structure.TableStructure;

import java.util.HashMap;
import java.util.Map;

public class TableDrawer {
    public void draw() {

    }

    public void draw(TableStructure table) {
        var columns = table.getColumnList();
        Map<String, Integer> space = getColumnsSize(table);
        System.out.println(getUpHeaderBorder(space));
        for (int i = 0; i < columns.size(); i++) {
            System.out.print("║");
            var name = columns.get(i).getName();
            System.out.print(setSpaceCenter(name, space.get(name)));
            if (i == columns.size() - 1) {
                System.out.println("║");
            }
        }
        System.out.println(getBottomHeaderBorder(space));
    }

    private Map<String, Integer> getColumnsSize(TableStructure table) {
        Map<String, Integer> spaces = new HashMap<>();
        for (var column : table.getColumnList()) {
            int width = calculateTableColumnSize(column.getName());
            spaces.put(column.getName(), width);
        }

        return spaces;
    }

    private String setSpace(String value, int width) {
        var returnedString = " ".repeat(Math.max(0, width - value.length()));

        return returnedString + value;
    }

    private String getUpHeaderBorder(Map<String, Integer> values) {

        return getBorderByPattern(values, "╔", "═", "╦", "╗");
    }

    private String getBottomHeaderBorder(Map<String, Integer> values) {

        return getBorderByPattern(values, "╠", "═", "╬", "╣");
    }

    private String getBorderByPattern(Map<String, Integer> values, String leftSymbol, String centerSymbol, String delimiterSymbol, String rightSymbol) {
        StringBuilder border = new StringBuilder(leftSymbol);
        int i = 0;
        for (var columnName : values.keySet()) {
            int size = values.get(columnName);
            border.append(centerSymbol.repeat(Math.max(0, size)));
            if (i != values.size() - 1) {
                border.append(delimiterSymbol);
            }
            i++;
        }
        border.append(rightSymbol);

        return border.toString();
    }

    private String setSpaceCenter(String value, int width) {
        String spaceAround = " ".repeat(Math.max(0, (width - value.length()) / 2));
        var returnedString = spaceAround;
        returnedString += value;
        returnedString += spaceAround;

        return returnedString;
    }

    private int calculateTableColumnSize(String name) {
        return name.length() + 2;
    }

//    private String getBorder(TableStructure table) {
//        StringBuilder sb = new StringBuilder();
//        var columns = table.getColumnList();
//    }
}
