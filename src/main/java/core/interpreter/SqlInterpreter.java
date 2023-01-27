package core.interpreter;

import core.drawUtils.TableDrawer;
import core.parser.FeatureType;
import core.structure.Table;

import java.util.*;

public class SqlInterpreter implements Interpreter {

    @Override
    public void interpret(String query) {
        Map<FeatureType, List<String>> splitQuery = splitQuery(query);

        Table table = null;
        for (var subQuery : splitQuery.keySet()) {
            var args = splitQuery.get(subQuery);
            table = subQuery.getFeature().parse(args, table);
        }
        TableDrawer.draw(table);
    }

    private Map<FeatureType, List<String>> splitQuery(String query) {
        List<String> subQuery = Arrays.stream(query.split("\\s|,|\\s,\\s"))
                .filter(x -> !x.isEmpty())
                .toList();

        Map<FeatureType, List<String>> splitQuery = new TreeMap<>();
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
}
