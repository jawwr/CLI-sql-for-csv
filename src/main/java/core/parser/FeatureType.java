package core.parser;

import core.parser.features.*;

public enum FeatureType {
    INSERT("insert", new Insert()),
    FROM("from", new From()),
    WHERE("where", new Where()),
    DELETE("delete", new Delete()),
    SELECT("select", new Select());
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
