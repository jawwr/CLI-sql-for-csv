import core.interpreter.Interpreter;
import core.interpreter.SqlInterpreter;
import core.parser.features.From;

public class Main {
    public static void main(String[] args) {
        From.setPath(args[0]);
        Interpreter interpreter = new SqlInterpreter();
        interpreter.interpret("SELECT capital, square FROM countries WHERE square > 8600");
    }
}