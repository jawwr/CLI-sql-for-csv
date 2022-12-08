package core.structure;

public class Table {
    private final TableStructure structure;
    private String[][] values;

    public Table(TableStructure structure) {
        this.structure = structure;
    }

    public String[][] getValues() {
        return values;
    }

    public TableStructure getStructure() {
        return structure;
    }

    public void setValues(String[][] values) {
        this.values = values;
    }
}
