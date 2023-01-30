package core.parser.features;

import core.repos.TableRepo;
import core.structure.Column;
import core.structure.Table;
import core.utils.ListExtension;

import java.util.ArrayList;
import java.util.List;

public class Insert implements Feature {
    @Override
    public Table parse(List<String> args, Table table) {
        String tableName = findTableName(args);
        table = getAllTable(tableName);
        List<String> insertValues = getValues(args, table);

        if (isParameterExist(args)) {
            var params = getParameters(args);
            insertMissingParameters(table.getStructure().columnList(), insertValues, params);
        }

        String[][] values = addNewValue(table.getValues(), insertValues.toArray(new String[0]));

        table.setValues(values);

        addValueInFile(table);

        return table;
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

    private void insertMissingParameters(List<Column> structure, List<String> parameters, List<String> existParameters){
        for (Column column : structure) {
            if (!ListExtension.containsIgnoreCase(existParameters, column.getName())){
                int index = structure.indexOf(column);
                parameters.add(index, " ");
            }
        }
    }

    private List<String> getValues(List<String> args, Table table) {
        if (!ListExtension.containsIgnoreCase(args, "values")) {
            throw new IllegalArgumentException("Values not exist");
        }
        int valuesStartIndex = ListExtension.indexOfIgnoreCase(args, "values") + 1;
        List<String> values = new ArrayList<>();
        for (int i = valuesStartIndex; i < args.size(); i++) {
            values.add(args.get(i));
        }

        return values;
    }

    private List<String> getParameters(List<String> args){
        int intoIndex = ListExtension.indexOfIgnoreCase(args, "into") + 2;
        int valueIndex = ListExtension.indexOfIgnoreCase(args, "values");

        List<String> parameters = new ArrayList<>();
        for (int i = intoIndex; i < valueIndex; i++){
            parameters.add(args.get(i));
        }

        return parameters;
    }

    private boolean isParameterExist(List<String> args){
        int intoIndex = ListExtension.indexOfIgnoreCase(args, "into") + 2;
        int valueIndex = ListExtension.indexOfIgnoreCase(args, "values");

        return valueIndex - intoIndex != 0;
    }

    private String findTableName(List<String> args) {
        if (!ListExtension.containsIgnoreCase(args, "into")) {
            throw new IllegalArgumentException("Into not exist");
        }

        int intoIndex = ListExtension.indexOfIgnoreCase(args, "into") + 1;

        return args.get(intoIndex);
    }
}
