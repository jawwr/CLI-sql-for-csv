package core.interpreter;

import core.drawUtils.TableDrawer;
import core.fileWorker.FileWorker;
import core.parser.FeatureType;
import core.structure.Column;
import core.structure.ColumnType;
import core.structure.Table;
import core.structure.TableStructure;

import java.util.*;

public class SqlInterpreter implements Interpreter {
    private final FileWorker worker;
    private final TableDrawer drawer;

    public SqlInterpreter(String path) {
        this.worker = new FileWorker(path);
        drawer = new TableDrawer();
    }

    @Override
    public void interpret(String query) {
        Map<FeatureType, List<String>> splitQuery = splitQuery(query);

        var args = splitQuery.get(FeatureType.FROM);
        var table =  FeatureType.FROM.getFeature().parse(args, null);

//        Table table = null;
//        for (var subQuery : splitQuery.keySet()){
//            var args = splitQuery.get(subQuery);
//            table = subQuery.getFeature().parse(args, table);
//        }
//        var fileValue = worker.readFile("countries.csv");//TODO
//
//        TableStructure structure = new TableStructure(getColumn(fileValue[0]));
//        Table allTable = new Table(structure);
//        allTable.setValues(getTableValues(fileValue));
        drawer.draw(table);
    }

    private Map<FeatureType, List<String>> splitQuery(String query) {
        List<String> subQuery = Arrays.stream(query.split("\\s|,|\\s,\\s"))
                .filter(x -> !x.isEmpty())
                .toList();

        Map<FeatureType, List<String>> splitQuery = new HashMap<>();
        FeatureType currentFeature = null;
        for (var word : subQuery) {
            if (isFeatureType(word)) {
                currentFeature = FeatureType.valueOf(word.toUpperCase());
                splitQuery.put(currentFeature, new ArrayList<String>());
            } else {
                splitQuery.get(currentFeature).add(word);
            }
        }

        return splitQuery;
    }

    private boolean isFeatureType(String word) {
        try {
            FeatureType.valueOf(word.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    private String[][] getTableValues(String[][] allValues) {
//        var values = Arrays.stream(allValues).skip(1).toArray();
//        String[][] tableValues = new String[values.length][];
//        for (int i = 0; i < values.length; i++) {
//            tableValues[i] = (String[]) values[i];
//        }
//        return tableValues;
//    }
//
//    private List<Column> getColumn(String[] columns) {
//        List<Column> columnList = new ArrayList<>();
//        for (String columnValues : columns) {
//            Column column = new Column(ColumnType.VARCHAR, columnValues);//TODO (?) сделать проверку на то, какие типы данных могут быть
//            columnList.add(column);
//        }
//
//        return columnList;
//    }
}
