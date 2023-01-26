package core.parser.features;

import core.structure.Table;

import java.util.List;

@FunctionalInterface
public interface Feature {
    Table parse(List<String> args, Table table);
}
