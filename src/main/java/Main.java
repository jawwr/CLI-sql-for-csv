import core.interpreter.Interpreter;
import core.interpreter.SqlInterpreter;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Interpreter interpreter = new SqlInterpreter(new File("").getAbsolutePath() + "\\test files");//TODO переделать на считывание файла по имени
        interpreter.interpret("SELECT Populations FROM countries");
    }
}