package core.parser.features;

import core.structure.Table;

import java.util.List;

public class Create implements Feature {
    @Override
    public Table parse(List<String> args, Table table) {
        return table;
    }
}