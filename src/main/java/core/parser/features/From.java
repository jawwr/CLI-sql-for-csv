package core.parser.features;

import core.fileWorker.FileWorker;
import core.structure.Column;
import core.structure.ColumnType;
import core.structure.Table;
import core.structure.TableStructure;
import core.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class From implements Feature {
    private static FileWorker worker;

    public From() {
        worker = new FileWorker(Constants.PATH);
    }

    @Override
    public Table parse(List<String> args, Table table) {
        var fileValue = worker.readFile(args.get(0) + ".csv");

        TableStructure structure = new TableStructure(getColumn(fileValue[0]));
        Table allTable = new Table(structure);
        allTable.setValues(getTableValues(fileValue));

        return allTable;
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
            Column column = new Column(ColumnType.VARCHAR, columnValues);//TODO (?) сделать проверку на то, какие типы данных могут быть
            columnList.add(column);
        }

        return columnList;
    }
}
