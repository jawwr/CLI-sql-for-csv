package core.parser.features;

import core.fileWorker.FileWorker;
import core.structure.Column;
import core.structure.Table;
import core.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Insert implements Feature {
    private final FileWorker fileWorker;

    public Insert() {
        this.fileWorker = new FileWorker(Constants.PATH);
    }

    @Override
    public Table parse(List<String> args, Table table) {
        String tableName = findTableName(args);
        From from = new From();
        Table newTable = from.parse(Collections.singletonList(tableName), null);
        List<String> insertValues = getValues(args, newTable);
        String[][] values = addNewValue(newTable.getValues(), insertValues.toArray(new String[0]));

        newTable.setValues(values);

        addValueInFile(values, tableName, newTable);

        return newTable;
    }

    private void addValueInFile(String[][] values, String tableName, Table table) {
        String[][] newValues = new String[values.length + 1][];
        newValues[0] = getHeader(table);
        System.arraycopy(values, 0, newValues, 1, values.length);
        fileWorker.writeFile(newValues, tableName, table);
    }

    private String[] getHeader(Table table){
        var columns = table.getStructure().columnList();
        List<String> columnName = new ArrayList<>();
        for (Column column : columns) {
            columnName.add(column.getName());
        }

        return columnName.toArray(new String[0]);
    }

    private String[][] addNewValue(String[][] values, String[] addValue) {
        String[][] newValues = new String[values.length + 1][];

        System.arraycopy(values, 0, newValues, 0, values.length);
        newValues[values.length] = addValue;

        return newValues;
    }

    private List<String> getValues(List<String> args, Table table) {
        if (!args.contains("VALUES")) {
            throw new IllegalArgumentException("Values not exist");
        }
        int valuesStartIndex = args.indexOf("VALUES") + 1;
        List<String> values = new ArrayList<>();
        for (int i = valuesStartIndex; i < args.size(); i++) {
            values.add(args.get(i));
        }
        if (values.size() != table.getStructure().columnList().size()) {
            throw new IllegalArgumentException("Column count not match column count");
        }

        return values;
    }

    private String findTableName(List<String> args) {
        if (!args.contains("INTO")) {
            throw new IllegalArgumentException("Into not exist");
        }

        int intoIndex = args.indexOf("INTO") + 1;

        return args.get(intoIndex);
    }
}
