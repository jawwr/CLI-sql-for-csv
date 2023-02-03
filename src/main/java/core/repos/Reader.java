package core.repos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Reader implements FileReader {
    private final String path;
    public Reader(String path) {
        this.path = path;
    }

    @Override
    public String[] readFile(String fileName) {
        List<String> listValue;
        String[] values;
        try {
            listValue = Files.readAllLines(new File(path + File.separator + fileName).toPath());
            values = new String[listValue.size()];
            for (int i = 0; i < listValue.size(); i++) {
                values[i] = listValue.get(i);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Table with name " + fileName.split("\\.")[0] + " not exist");
        }
        return values;
    }
}
