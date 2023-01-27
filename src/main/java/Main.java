import core.interpreter.Interpreter;
import core.interpreter.SqlInterpreter;
import core.parser.features.From;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        From.setPath(args[0]);
        Interpreter interpreter = new SqlInterpreter(new File("").getAbsolutePath() + "\\test files");//TODO переделать на считывание файла по имени
        interpreter.interpret("SELECT capital FROM countries");
    }
}