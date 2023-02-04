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
        if (!args.get(0).equalsIgnoreCase("table")) {
            throw new IllegalArgumentException("Missing 'TABLE' after 'CREATE'");
        }

        Table createTable;
        if (ListExtension.containsIgnoreCase(args, "as")) {
            createTable = createTable(args.get(1), table.getStructure().getColumnList(), table.getValues());
        } else {
            if (!isParameterExist(args)) {
                throw new IllegalArgumentException("Missing parameters in query");
            }

            List<String> parameters = ListExtension.getParameters(args);
            createTable = createTable(args.get(1), getColumns(parameters, args.get(1)), new String[0][0]);
        }

        TableRepo.writeFile(createTable);
        return null;
    }

    private Table createTable(String name, List<Column> columnList, String[][] values) {
        TableStructure structure = new TableStructure(columnList, name);

        return new Table(name, structure, values);
    }

    private List<Column> getColumns(List<String> args, String tableName) {
        List<Column> columns = new ArrayList<>();
        for (String columnName : args) {
            columns.add(new Column(ColumnType.VARCHAR, tableName + "." + columnName));
        }

        return columns;
    }

    private boolean isParameterExist(List<String> args) {
        return ListExtension.containsIgnoreCase(args, "(") && ListExtension.containsIgnoreCase(args, ")");
    }
}
