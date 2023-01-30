package core.interpreter;

import core.structure.Table;

@FunctionalInterface
public interface Interpreter {
    Table interpret(String query);
}
