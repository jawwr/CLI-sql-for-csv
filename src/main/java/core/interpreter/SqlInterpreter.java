package core.interpreter;

import core.fileWorker.FileWorker;

public class SqlInterpreter implements Interpreter {
    private final FileWorker worker;

    public SqlInterpreter(String path) {
        this.worker = new FileWorker(path);
    }

    @Override
    public void interpret(String query) {
        var fileValue = worker.readFile("countries.csv");

    }
}
