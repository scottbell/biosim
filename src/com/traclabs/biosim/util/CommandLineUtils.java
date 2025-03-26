package com.traclabs.biosim.util;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "ModernCommandLineApp", mixinStandardHelpOptions = true, version = "1.0",
        description = "Processes command line options using Picocli")
public class CommandLineUtils implements Runnable {

    @Option(names = {"-id"}, description = "ID of this instance", defaultValue = "0")
    private int id;

    @Option(names = {"-name"}, description = "Name of this instance")
    private String name;

    @Option(names = {"-xml"}, description = "XML init file for this instance")
    private String xml;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CommandLineUtils()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Processing command line options:");
        System.out.println("ID: " + id);
        System.out.println("Name: " + (name != null ? name : "Not provided"));
        System.out.println("XML: " + (xml != null ? xml : "Not provided"));
    }
}