package core.parser;

import core.parser.features.*;

public enum FeatureType {
    INSERT("insert", new Insert()),
    FROM("from", new From()),
    UPDATE("update", new Update()),
    WHERE("where", new Where()),
    SET("set", new Set()),
    DELETE("delete", new Delete()),
    SELECT("select", new Select()),
    CREATE("create", new Create());
    private final String queryName;
    private final Feature feature;

    public Feature getFeature() {
        return feature;
    }

    FeatureType(String queryName, Feature feature) {
        this.queryName = queryName;
        this.feature = feature;
    }

    public String getQueryName() {
        return queryName;
    }
}
