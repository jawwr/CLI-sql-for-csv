package core.parser.features;

import core.repos.TableRepo;
import core.structure.Table;
import core.structure.TableStructure;
import core.utils.ListExtension;
import core.utils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Join implements Feature {
    @Override
    public Table parse(List<String> args, Table table) {
        Table joinTable = TableRepo.readTableFromCSV(args.get(0));
        if (!args.get(1).equalsIgnoreCase("on")) {
            joinTable.setAlias(args.get(1));
        }

        return joinTables(table, joinTable, args);
    }

    private Table joinTables(Table table1, Table table2, List<String> args) {
        if (!ListExtension.containsIgnoreCase(args, "on")) {
            throw new IllegalArgumentException("Missing 'ON'");
        }
        var columnsKey = getColumnsKey(args);

        var tempTable = table1;
        table1 = containsColumnWithAlias(columnsKey.first(), table1) ? tempTable : table2;
        table2 = containsColumnWithAlias(columnsKey.second(), table2) ? table2 : tempTable;

        validateTables(table1, table2);

        return createJoinTable(columnsKey, table1, table2);
    }

    private boolean containsColumnWithAlias(String columnKey, Table table) {
        try {
            table.getColumnIndex(columnKey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Table createJoinTable(Tuple<String, String> keys, Table table1, Table table2) {
        String joinKeyTable1 = keys.first();
        String joinKeyTable2 = keys.second();

        var joinValues = joinValues(table1, table2, joinKeyTable1, joinKeyTable2);
        var structures = table1.getStructure().getColumnList();
        structures.addAll(table2.getStructure().getColumnList());
        TableStructure structure = new TableStructure(structures);

        return new Table(table1.getName(), table1.getAlias(), structure, joinValues);
    }

    private String[][] joinValues(Table table1, Table table2, String keyTable1, String keyTable2) {
        int indexKeyTable1 = table1.getColumnIndex(keyTable1);
        int indexKeyTable2 = table2.getColumnIndex(keyTable2);

        var valuesTable1 = table1.getValues();
        var valuesTable2 = table2.getValues();

        for (int i = 0; i < valuesTable1.length; i++) {
            for (String[] values2 : valuesTable2) {
                if (valuesTable1[i][indexKeyTable1].equalsIgnoreCase(values2[indexKeyTable2])) {
                    List<String> values = new ArrayList<>(List.of(valuesTable1[i]));
                    values.addAll(List.of(values2));
                    valuesTable1[i] = values.toArray(new String[0]);

                    break;
                }
            }
        }

        int valuesLength = table1.getStructure().getColumnList().size() + table2.getStructure().getColumnList().size();

        return Arrays.stream(valuesTable1).filter(x -> x.length == valuesLength).toList().toArray(new String[0][0]);
    }

    private void validateTables(Table table1, Table table2) {
        if (table1 == table2) {
            throw new IllegalArgumentException("Invalid table name or alias");
        }
    }

    private Tuple<String, String> getColumnsKey(List<String> args) {
        int columnKeyIndex = ListExtension.indexOfIgnoreCase(args, "on") + 1;
        List<String> columnNames = args.subList(columnKeyIndex, args.size());

        if (!ListExtension.containsIgnoreCase(columnNames, "=")) {
            throw new IllegalArgumentException("Invalid operation in 'ON'");
        }

        return new Tuple<>(columnNames.get(0), columnNames.get(2));
    }
}
