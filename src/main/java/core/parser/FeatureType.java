package core.parser;

import core.parser.features.Feature;
import core.parser.features.From;
import core.parser.features.Select;

public enum FeatureType {
    SELECT("select", new Select()),
    FROM("from", new From());
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
