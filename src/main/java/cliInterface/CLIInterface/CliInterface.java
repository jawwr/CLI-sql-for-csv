package cliInterface.CLIInterface;

import cliInterface.tableDrawer.TableDrawer;
import core.interpreter.Interpreter;
import core.interpreter.SqlInterpreter;

import java.util.Scanner;

public class CliInterface {
    public static void start() {
        Interpreter interpreter = new SqlInterpreter();
        while (true) {
            try {
                System.out.println("Input Query: ");
                Scanner scanner = new Scanner(System.in);
                var table = interpreter.interpret(scanner.nextLine());
                TableDrawer.draw(table);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
