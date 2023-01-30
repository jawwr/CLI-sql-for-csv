package core.parser.features;

import core.repos.TableRepo;
import core.structure.Column;
import core.structure.ColumnType;
import core.structure.Table;
import core.structure.TableStructure;
import core.utils.ListExtension;

import java.util.ArrayList;
import java.util.List;

public class Create implements Feature {
    @Override
    public Table parse(List<String> args, Table table) {
        validate(args);
        List<String> parameters = ListExtension.getParameters(args);

        String tableName = args.get(1);
        List<Column> columns = getColumns(parameters);
        TableStructure structure = new TableStructure(columns);
        Table createTable = new Table(tableName, structure, new String[0][0]);
        createTableFile(createTable);

        return createTable;
    }

    private void validate(List<String> args){
        if (!args.get(0).equalsIgnoreCase("table")) {
            throw new IllegalArgumentException("Missing 'TABLE' after 'CREATE'");
        }
        if (!isParameterExist(args)) {
            throw new IllegalArgumentException("Missing parameters in query");
        }
    }

    private void createTableFile(Table table) {
        TableRepo.writeFile(table.toCSV(), table.getName());
    }

    private List<Column> getColumns(List<String> args) {
        List<Column> columns = new ArrayList<>();
        for (String columnName : args) {
            columns.add(new Column(ColumnType.VARCHAR, columnName));
        }

        return columns;
    }

    private boolean isParameterExist(List<String> args) {
        return ListExtension.containsIgnoreCase(args, "(") && ListExtension.containsIgnoreCase(args, ")");
    }
}
