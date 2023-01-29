package core.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    private final String name;
    private final TableStructure structure;
    private String[][] values;

    public Table(String name, TableStructure structure) {
        this.structure = structure;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String[][] getValues() {
        return values;
    }

    public TableStructure getStructure() {
        return structure;
    }

    public void setValues(String[][] values) {
        this.values = values;
    }

    public static Table fromCSV(String[][] values, String tableName) {
        TableStructure structure = new TableStructure(getColumn(values[0]));
        Table table = new Table(tableName, structure);
        table.setValues(getTableValues(values));

        return table;
    }

    private static String[][] getTableValues(String[][] allValues) {
        var values = Arrays.stream(allValues).skip(1).toArray();
        String[][] tableValues = new String[values.length][];
        for (int i = 0; i < values.length; i++) {
            tableValues[i] = (String[]) values[i];
        }
        return tableValues;
    }

    private static List<Column> getColumn(String[] columns) {
        List<Column> columnList = new ArrayList<>();
        for (String columnValues : columns) {
            Column column = new Column(ColumnType.VARCHAR, columnValues);//TODO (?) сделать проверку на то, какие типы данных могут быть
            columnList.add(column);
        }

        return columnList;
    }

    public String[][] toCSV(){
        String[][] newValues = new String[values.length + 1][];
        newValues[0] = getHeader();
        System.arraycopy(values, 0, newValues, 1, values.length);

        return newValues;
    }

    private String[] getHeader() {
        var columns = structure.columnList();
        List<String> columnName = new ArrayList<>();
        for (Column column : columns) {
            columnName.add(column.getName());
        }

        return columnName.toArray(new String[0]);
    }

}
