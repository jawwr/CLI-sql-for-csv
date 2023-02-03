package core.structure;

public class Column {
    private ColumnType type;
    private String name;

    public Column(ColumnType type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        if (!name.contains(".")) {
            return name;
        }
        return name.split("\\.")[1];
    }

    public String getAllName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }
}
