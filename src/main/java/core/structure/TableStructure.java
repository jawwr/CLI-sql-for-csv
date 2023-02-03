package core.structure;

import java.util.List;

public class TableStructure {
    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    private List<Column> columnList;

    public TableStructure(List<Column> columnList) {
        this.columnList = columnList;
    }

    public TableStructure(List<Column> columnList, String tableName) {
        for (Column column : columnList) {
            column.setName(tableName + "." + column.getName());
        }
        this.columnList = columnList;
    }

    public List<Column> getColumnList() {
        return columnList;
    }
}
