package core.interpreter;

import core.drawUtils.TableDrawer;
import core.parser.FeatureType;
import core.structure.Table;

import java.util.*;

public class SqlInterpreter implements Interpreter {
    private final List<String> operations = Arrays.asList("=", ">", "<", "<=", ">=");

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
        List<String> subQuery = Arrays.stream(query.split("\\s|,|\\s,\\s|^$"))
                .filter(x -> !x.isEmpty())
                .toList();

        Map<FeatureType, List<String>> splitQuery = new TreeMap<>();
        FeatureType currentFeature = null;
        for (var word : subQuery) {
            if (isFeatureType(word)) {
                currentFeature = FeatureType.valueOf(word.toUpperCase());
                splitQuery.put(currentFeature, new ArrayList<>());
            } else {
                if (isConcatOperation(word)) {
                    var operation = splitOperations(word);
                    splitQuery.get(currentFeature).addAll(operation);
                } else {
                    splitQuery.get(currentFeature).add(word);
                }
            }
        }

        return splitQuery;
    }

    private boolean isConcatOperation(String word) {
        for (String operation : operations) {
            if (word.contains(operation)) {
                return true;
            }
        }

        return false;
    }

    private List<String> splitOperations(String word) {
        List<String> result = new ArrayList<>();
        for (String operation : operations) {
            if (word.contains(operation)) {
                var split = Arrays.stream(word.split(operation))
                        .filter(x -> !x.isEmpty())
                        .toList()
                        .toArray(new String[0]);

                if (word.startsWith(operation)) {
                    result.add(operation);
                    result.add(split[0]);
                } else {
                    result.add(split[0]);
                    result.add(operation);
                }

                if (split.length == 2) {
                    result.add(split[1]);
                }
                break;
            }
        }

        return result;
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
