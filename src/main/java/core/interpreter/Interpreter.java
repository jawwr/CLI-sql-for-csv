package core.interpreter;

@FunctionalInterface
public interface Interpreter {
    void interpret(String query);
}
