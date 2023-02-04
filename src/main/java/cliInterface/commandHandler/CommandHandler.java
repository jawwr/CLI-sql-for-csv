package cliInterface.commandHandler;

import core.parser.FeatureType;
import core.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandHandler {
    public static void handle(String command) {
        command = command.split(":")[1];
        switch (command) {
            case "h", "help" -> help();
            case "cd" -> changeDirectory();
            case "f", "feature" -> showAllQuery();
            default -> System.out.println("Unknown command");
        }
    }

    private static void showAllQuery() {
        List<String> list = new ArrayList<>();
        for (var command : Commands.values()) {
            list.add(command.getCommand());
        }
        int maxLength = getMaxValueLength(list);

        for (var feature : FeatureType.values()) {
            System.out.println("- " + getDelimiter(feature.getQueryName(), maxLength) + " \t " + feature.getDescription());
        }
    }

    private static int getMaxValueLength(List<String> list) {
        int size = 0;
        for (var feature : list) {
            if (feature.length() > size) {
                size = feature.length();
            }
        }

        return size;
    }

    private static String getDelimiter(String value, int size) {
        StringBuilder sb = new StringBuilder(value);
        sb.append(" ".repeat(Math.max(0, size - sb.length())));

        return sb.toString();
    }

    private static void help() {
        List<String> list = new ArrayList<>();
        for (var command : Commands.values()) {
            list.add(command.getCommand());
        }
        int maxLength = getMaxValueLength(list);

        for (var command : Commands.values()) {
            System.out.println(getDelimiter(command.getCommand(), maxLength) + " \t " + command.getDescription());
        }
    }

    private static void changeDirectory() {
        System.out.println("Input new directory path");
        Scanner scanner = new Scanner(System.in);

        Constants.PATH = scanner.nextLine();
        System.out.println("The new directory has been successfully mounted");
    }
}
