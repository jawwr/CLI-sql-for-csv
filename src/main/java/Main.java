import core.drawUtils.TableDrawer;
import core.structure.Column;
import core.structure.ColumnType;
import core.structure.TableStructure;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TableStructure tableStructure = new TableStructure(
                List.of(new Column(ColumnType.VARCHAR, "te       stVarchar"),
                        new Column(ColumnType.INT, "artytestInt"),
                        new Column(ColumnType.DATE, "sdftestDateпроп         одроорпорп")
                )
        );

        TableDrawer drawer = new TableDrawer();
        drawer.draw(tableStructure);
    }
}
