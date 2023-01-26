package core.parser.features;

import core.structure.Table;

@FunctionalInterface
public interface Feature {
    Table work(String query, Table table);
}
