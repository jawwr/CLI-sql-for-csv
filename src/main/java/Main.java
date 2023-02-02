import cliInterface.CLIInterface.CliInterface;
import core.utils.Constants;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0){
            throw new Exception("Missing path in arguments");
        }
        Constants.PATH = args[0];
        CliInterface.start();
    }
}
