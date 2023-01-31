package core.parser.features;

import core.repos.TableRepo;
import core.structure.Column;
import core.structure.Table;
import core.structure.TableStructure;
import core.utils.ListExtension;

import java.util.List;

public class Insert implements Feature {
    @Override
    public Table parse(List<String> args, Table table) {
        String tableName = findTableName(args);
        table = getAllTable(tableName);
        List<String> insertValues = getValues(args);

        if (isColumnListExist(args)) {
            var params = ListExtension.getParameters(args);
            insertMissingParameters(table.getStructure().columnList(), insertValues, params);
        }
        validateParameterCount(insertValues, table.getStructure());

        String[][] values = addNewValue(table.getValues(), insertValues.toArray(new String[0]));

        table.setValues(values);

        addValueInFile(table);

        return table;
    }

    private void validateParameterCount(List<String> insertValues, TableStructure structure) {
        if (insertValues.size() != structure.columnList().size()) {
            throw new IllegalArgumentException("Invalid column count");
        }
    }

    private Table getAllTable(String name) {
        return TableRepo.readTableFromCSV(name);
    }

    private void addValueInFile(Table table) {
        TableRepo.writeFile(table.toCSV(), table.getName());
    }

    private String[][] addNewValue(String[][] values, String[] addValue) {
        String[][] newValues = new String[values.length + 1][];

        System.arraycopy(values, 0, newValues, 0, values.length);
        newValues[values.length] = addValue;

        return newValues;
    }

    private void insertMissingParameters(List<Column> structure, List<String> parameters, List<String> existParameters) {
        for (Column column : structure) {
            if (!ListExtension.containsIgnoreCase(existParameters, column.getName())) {
                int index = structure.indexOf(column);
                parameters.add(index, "null");
            }
        }
    }

    private List<String> getValues(List<String> args) {
        if (!ListExtension.containsIgnoreCase(args, "values")) {
            throw new IllegalArgumentException("Missing 'VALUES'");
        }
        int valuesIndex = ListExtension.indexOfIgnoreCase(args, "values");
        var argsAfterValues = args.subList(valuesIndex, args.size());

        return ListExtension.getParameters(argsAfterValues);
    }

    private boolean isColumnListExist(List<String> args) {
        int valuesIndex = ListExtension.indexOfIgnoreCase(args, "values");
        var list = args.subList(0, valuesIndex);
        return (ListExtension.containsIgnoreCase(list, "(") && ListExtension.containsIgnoreCase(list, ")"))
                || !args.get(2).equalsIgnoreCase("values");
    }

    private String findTableName(List<String> args) {
        if (!ListExtension.containsIgnoreCase(args, "into")) {
            throw new IllegalArgumentException("Missing 'INTO");
        }

        int intoIndex = ListExtension.indexOfIgnoreCase(args, "into") + 1;

        return args.get(intoIndex);
    }
}
