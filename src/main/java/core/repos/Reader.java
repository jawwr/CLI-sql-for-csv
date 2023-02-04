package core.repos;

import core.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Reader implements FileReader {
    @Override
    public String[] readFile(String fileName) {
        List<String> listValue;
        String[] values;
        try {
            listValue = Files.readAllLines(new File(Constants.PATH + File.separator + fileName).toPath());
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
