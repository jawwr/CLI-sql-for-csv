package core.parser;

import core.parser.features.Feature;
import core.parser.features.From;
import core.parser.features.Select;
import core.parser.features.Where;

public enum FeatureType {
    FROM("from", new From()),
    WHERE("where", new Where()),
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
