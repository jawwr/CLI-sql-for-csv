package core.parser.features;

import core.repos.TableRepo;
import core.structure.Table;

import java.util.List;

public class Update implements Feature {
    @Override
    public Table parse(List<String> args, Table table) {
        return TableRepo.readTableFromCSV(args.get(0));
    }
}
