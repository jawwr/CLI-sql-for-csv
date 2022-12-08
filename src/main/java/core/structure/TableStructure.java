package core.structure;

import java.util.List;

public class TableStructure {
    public List<Column> getColumnList() {
        return columnList;
    }

    public TableStructure(List<Column> columnList) {
        this.columnList = columnList;
    }

    private final List<Column> columnList;
}
