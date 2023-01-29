import core.interpreter.Interpreter;
import core.interpreter.SqlInterpreter;
import core.utils.Constants;

public class Main {
    public static void main(String[] args) {
        Constants.PATH = args[0];
        Interpreter interpreter = new SqlInterpreter();
//        Scanner scanner = new Scanner(System.in);
//        interpreter.interpret(scanner.next());
//        interpreter.interpret("SELECT * FROM countries WHERE square<100 and continent =europe and populations< 50000 or ");
//        interpreter.interpret("INSERT INTO countries VALUES testCountry, testCapital, -10, 10, testContinent");
//        interpreter.interpret("INSERT INTO countries VALUES testCountry, testCapital, -10, 20, testContinent");
//        interpreter.interpret("INSERT INTO countries VALUES testCountry, testCapital, 10, 123, testContinent");
//        interpreter.interpret("select * from numbers");
        interpreter.interpret("select * from countries");
//        interpreter.interpret("DELETE FROM countries WHERE square < 0 or populations = 123");
    }
}