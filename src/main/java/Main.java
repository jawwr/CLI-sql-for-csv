import core.interpreter.Interpreter;
import core.interpreter.SqlInterpreter;
import core.utils.Constants;

public class Main {
    public static void main(String[] args) {
//        From.setPath(args[0]);
        Constants.PATH = args[0];
        Interpreter interpreter = new SqlInterpreter();
//        Scanner scanner = new Scanner(System.in);
//        interpreter.interpret(scanner.next());
        interpreter.interpret("SELECT * FROM countries WHERE square < 100 and continent = europe or continent = asia");
//        interpreter.interpret("INSERT INTO countries VALUES testCountry, testCapital, testSquare, testPopulation, testContinent");
    }
}