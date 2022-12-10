package core.interpreter;

import core.drawUtils.TableDrawer;
import core.fileWorker.FileWorker;
import core.structure.Column;
import core.structure.ColumnType;
import core.structure.Table;
import core.structure.TableStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlInterpreter implements Interpreter {
    private final FileWorker worker;
    private final TableDrawer drawer;

    public SqlInterpreter(String path) {
        this.worker = new FileWorker(path);
        drawer = new TableDrawer();
    }

    @Override
    public void interpret(String query) {
        var fileValue = worker.readFile("countries.csv");//TODO

        TableStructure structure = new TableStructure(getColumn(fileValue[0]));
        Table table = new Table(structure);
        table.setValues(getTableValues(fileValue));

        drawer.draw(table);
    }

    private String[][] getTableValues(String[][] allValues) {
        var values = Arrays.stream(allValues).skip(1).toArray();
        String[][] tableValues = new String[values.length][];
        for (int i = 0; i < values.length; i++) {
            tableValues[i] = (String[]) values[i];
        }
        return tableValues;
    }

    private List<Column> getColumn(String[] columns) {
        List<Column> columnList = new ArrayList<>();
        for (String columnValues : columns) {
            Column column = new Column(ColumnType.VARCHAR, columnValues);//TODO сделать проверку на то, какие типы данных могут быть
            columnList.add(column);
        }

        return columnList;
    }
}
