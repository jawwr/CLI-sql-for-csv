package core.interpreter;

import core.parser.FeatureType;
import core.structure.Table;
import core.utils.Constants;
import core.utils.ListExtension;
import core.utils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlInterpreter implements Interpreter {
    @Override
    public Table interpret(String query) {
        var splitQuery = splitQuery(query);
        splitQuery.sort(this::a);

        Table table = null;
        for (var subQuery : splitQuery) {
            var args = subQuery.second();
            table = subQuery.first().getFeature().parse(args, table);
        }

        return table;
    }

    private int a(Tuple<FeatureType, List<String>> first, Tuple<FeatureType, List<String>> second) {
        return first.first().ordinal() - second.first().ordinal();
    }

    private List<Tuple<FeatureType, List<String>>> splitQuery(String query) {
        List<String> subQuery = Arrays.stream(query.split("\\s|,|\\s,\\s|^$"))
                .filter(x -> !x.isEmpty())
                .toList();

//        Map<FeatureType, List<String>> splitQuery = new TreeMap<>();
        List<Tuple<FeatureType, List<String>>> splitQuery = new ArrayList<>();
        FeatureType currentFeature = null;
        boolean isBracketsOpen = false;
        for (var word : subQuery) {
            if (isFeatureType(word)) {
                currentFeature = FeatureType.valueOf(word.toUpperCase());
                splitQuery.add(new Tuple<>(currentFeature, new ArrayList<>()));
            } else {
                var index = findFeatureIndex(splitQuery, currentFeature);
                if (isConcatOperation(word)) {
                    var operation = splitOperations(word);
                    splitQuery.get(index).second().addAll(operation);
                } else if (isParameter(word)) {
                    var parameter = getParameter(word);
                    splitQuery.get(index).second().addAll(parameter);
                    isBracketsOpen = !isBracketsOpen;
                } else {
                    splitQuery.get(index).second().add(word);
                }
            }
        }

        if (isBracketsOpen) {
            throw new IllegalArgumentException("Invalid brackets");
        }

        return splitQuery;
    }

    private int findFeatureIndex(List<Tuple<FeatureType, List<String>>> list, FeatureType current) {
        var filterList = list.stream().filter(x -> x.first() == current).toList();
        var curr = filterList.get(filterList.size() - 1);
        return list.indexOf(curr);
    }

    private List<String> getParameter(String word) {
        var words = Arrays.stream(word.split("[()]"))
                .filter(x -> !x.isEmpty())
                .toList()
                .toArray(new String[0]);
        List<String> params = new ArrayList<>();

        if (word.contains("(") && word.contains(")")) {
            params.add("(");
            params.addAll(Arrays.asList(words));
            params.add(")");
        } else if (word.contains("(")) {
            if (words.length == 1) {
                params.add("(");
                params.addAll(Arrays.asList(words));
            } else {
                insertBrackets(words, params, "(");
            }
        } else if (word.contains(")")) {
            if (words.length == 1) {
                params.addAll(Arrays.asList(words));
                params.add(")");
            } else {
                insertBrackets(words, params, ")");
            }
        }

        return params;
    }

    private void insertBrackets(String[] words, List<String> params, String bracketSymbol) {
        switch (words.length) {
            case 0 -> params.add(bracketSymbol);
            case 2 -> {
                params.add(words[0]);
                params.add(bracketSymbol);
                params.add(words[1]);
            }
            default -> throw new IllegalArgumentException();
        }
    }

    private boolean isParameter(String word) {

        return word.contains("(") || word.contains(")");
    }

    private boolean isConcatOperation(String word) {
        if (word.length() == 1) {
            return false;
        } else if (ListExtension.containsIgnoreCase(Constants.AVAILABLE_OPERATION, word)) {
            return false;
        }
        for (String operation : Constants.AVAILABLE_OPERATION) {
            if (word.contains(operation)) {
                return true;
            }
        }

        return false;
    }

    private List<String> splitOperations(String word) {
        List<String> result = new ArrayList<>();
        for (String operation : Constants.AVAILABLE_OPERATION) {
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
