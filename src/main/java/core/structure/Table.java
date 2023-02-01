package core.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    private final String name;
    private String alias;
    private final TableStructure structure;
    private String[][] values;

    public Table(String name, String alias, TableStructure structure, String[][] values) {
        this.name = name;
        this.alias = alias;
        this.structure = structure;
        this.values = values;
    }

    public Table(String name, TableStructure structure, String[][] values) {
        this.name = name;
        this.structure = structure;
        this.values = values;
        this.alias = name;
    }

    public Table(String name, TableStructure structure) {
        this.structure = structure;
        this.name = name;
        this.alias = name;
    }

    public Table(Table copyTable){
        this.name = copyTable.name;
        this.alias = copyTable.alias;
//        this.structure = new TableStructure(copyTable.getStructure().columnList().stream().toList());
        this.structure = new TableStructure(copyTable.getStructure().columnList().stream().toList());
        this.values = copyTable.getValues().clone();
    }

    public String getName() {
        return name;
    }

    public String[][] getValues() {
        return values;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
        var values = Arrays.stream(allValues).skip(1).toList().toArray(new String[0][0]);
        String[][] tableValues = new String[values.length][];
        System.arraycopy(values, 0, tableValues, 0, values.length);
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

    public String[][] toCSV() {
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

    public int getColumnIndex(String name) {
        try {
            var columns = structure.columnList();
            var column = columns.stream()
                    .filter(x -> x.getName().equalsIgnoreCase(name))
                    .findFirst()
                    .get();

            return columns.indexOf(column);
        } catch (Exception e) {
            throw new IllegalArgumentException("Column with name " + name + " not exist");
        }
    }
}
