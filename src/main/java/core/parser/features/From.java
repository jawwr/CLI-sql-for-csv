package core.parser.features;

import core.repos.FileWorker;
import core.structure.Table;

import java.util.List;

public class From implements Feature {

    @Override
    public Table parse(List<String> args, Table table) {
        var fileValue = FileWorker.readFile(args.get(0) + ".csv");

        return Table.fromCSV(fileValue, args.get(0));
    }
}
