import core.interpreter.Interpreter;
import core.interpreter.SqlInterpreter;

import java.io.File;

public class Main {
    public static void main(String[] args) {
//        TableStructure tableStructure = new TableStructure(
//                List.of(new Column(ColumnType.VARCHAR, "Name"),
//                        new Column(ColumnType.INT, "Age"),
//                        new Column(ColumnType.DATE, "City")
//                )
//        );
//
//        Table table = new Table(tableStructure);
//        table.setValues(new String[][]{
//                {"John", "19", "Arizona"},
//                {"Lean", "23", "NYC"},
//                {"Lili", "15", "Chicago"},
//                {"Boris", "19", "Chelyabinsk"}
//        });
//        FileWorker fileWorker = new FileWorker();
//        fileWorker.readFile("");


        String s = new File("").getAbsolutePath() + "\\test files";
        Interpreter interpreter = new SqlInterpreter(new File("").getAbsolutePath() + "\\test files");//TODO переделать на считывание файла по имени
        interpreter.interpret("select all from this");


//        TableDrawer drawer = new TableDrawer();
//        drawer.draw(table);
    }
}