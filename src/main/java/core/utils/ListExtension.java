package core.utils;

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
}
