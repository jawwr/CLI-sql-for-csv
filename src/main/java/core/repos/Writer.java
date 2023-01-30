package core.repos;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Writer implements FileWriter {
    private final String path;

    public Writer(String directory) {
        this.path = directory;
    }

    @Override
    public void writeFile(String[][] values, String fileName) {
        try {
            var value = convertToCsv(values);
            var file = new File(path + File.separator + fileName + ".csv").toPath();
            Files.write(file, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> convertToCsv(String[][] values) {
        List<String> value = new ArrayList<>();
        for (String[] strings : values) {
            value.add(String.join(";", strings));
        }

        return value;
    }

}
