package core.parser.features;

import core.repos.TableRepo;
import core.structure.Table;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Delete implements Feature {
    @Override
    public Table parse(List<String> args, Table table) {
        Table allTable = TableRepo.readTableFromCSV(table.getName());
        String[][] values = deleteValues(allTable.getValues(), table.getValues());

        allTable.setValues(values);

        TableRepo.writeFile(allTable);

        return null;
    }

    private String[][] deleteValues(String[][] values, String[][] deleteValues) {
        for (int i = 0; i < values.length; i++) {
            for (String[] deleteValue : deleteValues) {
                if (Arrays.deepEquals(values[i], deleteValue)) {
                    values[i] = null;
                    break;
                }
            }
        }

        return Arrays.stream(values).filter(Objects::nonNull).toList().toArray(new String[0][0]);
    }
}
