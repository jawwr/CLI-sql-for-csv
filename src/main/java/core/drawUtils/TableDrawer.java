package core.drawUtils;

import core.structure.Column;
import core.structure.TableStructure;
import core.utils.Tuple;

import java.util.ArrayList;
import java.util.List;

public class TableDrawer {
    public void draw() {

    }

    public void draw(TableStructure table) {
        var columns = table.getColumnList();
        List<Tuple<String, Integer>> space = getColumnsSize(table);
        drawHeader(columns, space);
    }

    private void drawHeader(List<Column> columns, List<Tuple<String, Integer>> space) {
        System.out.println(getUpHeaderBorder(space));
        for (int i = 0; i < columns.size(); i++) {
            System.out.print("║");
            var name = columns.get(i).getName();
            System.out.print(setSpaceCenter(name, space.get(i).second()));
            if (i == columns.size() - 1) {
                System.out.println("║");
            }
        }
        System.out.println(getBottomHeaderBorder(space));
    }

    private List<Tuple<String, Integer>> getColumnsSize(TableStructure table) {
        List<Tuple<String, Integer>> spaces = new ArrayList<>();
        for (var column : table.getColumnList()) {
            int width = calculateTableColumnSize(column.getName());
            spaces.add(new Tuple<>(column.getName(), width));
        }

        return spaces;
    }

    private String setSpace(String value, int width) {
        var returnedString = " ".repeat(Math.max(0, width - value.length()));

        return returnedString + value;
    }

    private String getUpHeaderBorder(List<Tuple<String, Integer>> values) {

        return getBorderByPattern(values, "╔", "═", "╦", "╗");
    }

    private String getBottomHeaderBorder(List<Tuple<String, Integer>> values) {

        return getBorderByPattern(values, "╠", "═", "╬", "╣");
    }

    private String getBorderByPattern(List<Tuple<String, Integer>> values, String leftSymbol, String centerSymbol, String delimiterSymbol, String rightSymbol) {
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
}
