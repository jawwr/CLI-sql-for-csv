import core.drawUtils.TableDrawer;
import core.structure.Column;
import core.structure.ColumnType;
import core.structure.Table;
import core.structure.TableStructure;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TableStructure tableStructure = new TableStructure(
                List.of(new Column(ColumnType.VARCHAR, "Name"),
                        new Column(ColumnType.INT, "Age"),
                        new Column(ColumnType.DATE, "City")
                )
        );

        Table table = new Table(tableStructure);
        table.setValues(new String[][]{
                {"John", "19", "Arizona"},
                {"Lean", "23", "NYC"},
                {"Lili", "15", "Chicago"},
                {"Boris", "19", "Chelyabinsk"}
        });

        TableDrawer drawer = new TableDrawer();
        drawer.draw(table);
    }
}