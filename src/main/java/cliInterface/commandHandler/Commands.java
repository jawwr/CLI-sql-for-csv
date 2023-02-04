package cliInterface.commandHandler;

public enum Commands {
    HELP(":h, :help", "Show all available commands"),
    CHANGE_DIRECTORY(":cd", "Change working directory"),
    QUERY(":f, :feature", "Show all available feature");

    private final String command;
    private final String description;

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    Commands(String command, String description) {
        this.command = command;
        this.description = description;
    }
}
