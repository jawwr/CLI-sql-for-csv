package core.fileWorker;

public class Writer implements FileWriter{
    private final String path;
    public Writer(String directory) {
        this.path = directory;
    }

    @Override
    public void writeFile(String[][] values, String fileName) {
        //TODO
    }
}
