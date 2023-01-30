package core.utils;

import java.util.ArrayList;
import java.util.List;

public class ListExtension {
    public static boolean containsIgnoreCase(List<String> list, String word) {
        for (String item : list) {
            if (item.equalsIgnoreCase(word)) {
                return true;
            }
        }

        return false;
    }

    public static int indexOfIgnoreCase(List<String> list, String word) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase(word)) {
                return i;
            }
        }

        return -1;
    }

    public static List<String> getParameters(List<String> args) {
        int startIndex = indexOfIgnoreCase(args, "(") + 1;
        int endIndex = indexOfIgnoreCase(args, ")");

        List<String> params = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) {
            params.add(args.get(i));
        }

        return params;
    }
}
