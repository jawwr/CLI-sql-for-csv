package core.parser.features;

import core.repos.TableRepo;
import core.structure.Table;

import java.util.List;

public class Drop implements Feature {
    @Override
    public Table parse(List<String> args, Table table) {
        if (!args.get(0).equalsIgnoreCase("table")) {
            throw new IllegalArgumentException("Missing 'TABLE' after 'DROP'");
        }

        TableRepo.deleteTable(args.get(1));

        return null;
    }
}
