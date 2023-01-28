package core.structure;

public class Table {
    private final String name;
    private final TableStructure structure;
    private String[][] values;

    public Table(String name, TableStructure structure) {
        this.structure = structure;
        this.name = name;
    }

    public String getName() {
        return name;
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
