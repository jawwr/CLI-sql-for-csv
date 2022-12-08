import core.drawUtils.TableDrawer;
import core.structure.Column;
import core.structure.ColumnType;
import core.structure.TableStructure;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TableStructure tableStructure = new TableStructure(
                List.of(new Column(ColumnType.VARCHAR, "testVarchar"),
                        new Column(ColumnType.INT, "testInt"),
                        new Column(ColumnType.DATE, "testDate")
                )
        );

        TableDrawer drawer = new TableDrawer();
        drawer.draw(tableStructure);
    }
}
