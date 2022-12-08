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
//        System.out.println(getBorder(table));
        System.out.print("║");
        for (int i = 0; i < columns.size(); i++) {
            var name = columns.get(i).getName();
            System.out.print(setSpaceCenter(name, space.get(name)));
            if (i != columns.size() - 1) {
                System.out.print("║");
            }
        }
        System.out.print("║");
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
