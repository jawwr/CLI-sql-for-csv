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
        if (ListExtension.containsIgnoreCase(args, "as")){
            createTable = createTable(args.get(1), table.getStructure().columnList(), table.getValues());
        }else {
            if (!isParameterExist(args)) {
                throw new IllegalArgumentException("Missing parameters in query");
            }

            List<String> parameters = ListExtension.getParameters(args);
            createTable = createTable(args.get(1), getColumns(parameters), new String[0][0]);
        }

        createTableFile(createTable);
        return createTable;
    }

    private Table createTable(String name, List<Column> columnList, String[][] values){
        TableStructure structure = new TableStructure(columnList);

        return new Table(name, structure, values);
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
