package core.interpreter;

import core.drawUtils.TableDrawer;
import core.parser.FeatureType;

import java.util.*;

public class SqlInterpreter implements Interpreter {
    private final TableDrawer drawer;

    public SqlInterpreter(String path) {
        drawer = new TableDrawer();
    }

    @Override
    public void interpret(String query) {
        Map<FeatureType, List<String>> splitQuery = splitQuery(query);

        var fromArgs = splitQuery.get(FeatureType.FROM);
        var table = FeatureType.FROM.getFeature().parse(fromArgs, null);

        splitQuery.remove(FeatureType.FROM);

        for (var subQuery : splitQuery.keySet()) {
            var args = splitQuery.get(subQuery);
            table = subQuery.getFeature().parse(args, table);
        }
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
}
