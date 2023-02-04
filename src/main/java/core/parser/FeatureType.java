package core.parser;

import core.parser.features.*;

public enum FeatureType {
    INSERT("INSERT", "Insertion new value into a table (use with 'SET')", new Insert()),
    FROM("FROM", "Get values from table", new From()),
    JOIN("JOIN", "Joining tables", new Join()),
    UPDATE("UPDATE", "Update values in table", new Update()),
    WHERE("WHERE", "Filter values", new Where()),
    SET("SET", "Set a new values (use with 'INSERT')", new Set()),
    DELETE("DELETE", "Delete values in table", new Delete()),
    SELECT("SELECT", "Select columns from table", new Select()),
    DROP("DROP", "Delete table", new Drop()),
    LIMIT("LIMIT", "Limit number of values", new Limit()),
    CREATE("CREATE", "Create a new table (use 'AS' when you need to create table using other table)", new Create());
    private final String queryName;
    private final String description;

    public String getDescription() {
        return description;
    }

    private final Feature feature;

    public Feature getFeature() {
        return feature;
    }

    FeatureType(String queryName, String description, Feature feature) {
        this.queryName = queryName;
        this.feature = feature;
        this.description = description;
    }

    public String getQueryName() {
        return queryName;
    }
}
