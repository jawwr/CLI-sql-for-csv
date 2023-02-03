package core.parser.features;

import core.repos.TableRepo;
import core.structure.Table;

import java.util.List;

public class From implements Feature {

    @Override
    public Table parse(List<String> args, Table table) {
        table = TableRepo.readTableFromCSV(args.get(0));
        if (args.size() != 1) {
            table.setAlias(args.get(1));
        }
        return table;
    }
}
