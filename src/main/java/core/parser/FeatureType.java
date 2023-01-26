package core.parser;

public enum FeatureType {
    SELECT("select"),
    FROM("from");
    private final String query;

    FeatureType(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
