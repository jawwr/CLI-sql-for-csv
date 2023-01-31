import core.drawUtils.TableDrawer;
import core.interpreter.Interpreter;
import core.interpreter.SqlInterpreter;
import core.utils.Constants;

public class Main {
    public static void main(String[] args) {
        Constants.PATH = args[0];
        Interpreter interpreter = new SqlInterpreter();
        var table =
//        Scanner scanner = new Scanner(System.in);
//        interpreter.interpret(scanner.next());
//        interpreter.interpret("SELECT * FROM countries WHERE square<100 and continent =europe and populations< 50000 or ");
//        interpreter.interpret("INSERT INTO countries VALUES testCountry, testCapital, -10, 10, testContinent");
//        interpreter.interpret("INSERT INTO countries VALUES testCountry, testCapital, -10, 20, testContinent");
//                interpreter.interpret("INSERT INTO countries (capital, square, populations) VALUES testCountry, 10, 123");
//        interpreter.interpret("select * from numbers");
//        interpreter.interpret("update numbers set values = 10 where name = a or name = c");
//        interpreter.interpret("select * from countries");
//        interpreter.interpret("DELETE FROM countries WHERE square < 0 or populations = 123");
//        interpreter.interpret("UPDATE countries SET populations = 10, continent = newContinent WHERE square = -10");
//                interpreter.interpret("CREATE table testTable (id, name, age, surname)");
//                interpreter.interpret("select id, name from testTable");
//                interpreter.interpret("insert into testTable values (3, Mike, 15, Whiller)");
//                interpreter.interpret("delete from testTable where id = 1");
                interpreter.interpret("CREATE TABLE newTestTable1 AS SELECT id, name, surname FROM testTable where id < 3");
        TableDrawer.draw(table);
    }
}