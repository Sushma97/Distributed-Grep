package com.cs425;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client {

    static final MachineLocation[] list = {
            /* 1. */ new MachineLocation("0.0.0.0", 9876, "machine.i.log"),
        /* 2. */ new MachineLocation("0.0.0.0", 9876, "machine.1.log"),
        /* 3. */ new MachineLocation("0.0.0.0", 9876, "machine.2.log"),
        /* 4. */ new MachineLocation("0.0.0.0", 9876, "machine.3.log"),
        /* 5. */ new MachineLocation("0.0.0.0", 9876, "machine.4.log"),
    };


    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {
//        if (args.length != 1){
//            throw new InvalidDataException("Please input the regex to be searched");
//        }
        CommandLineInput cli = new CommandLineInput(args);
        // Client creates GrepRequest based on arguments
        String grepPattern = cli.pattern;
        // Client sends out GrepRequest over sockets to each server
        // We'll need some sort of lookup table for each machine
        // We may also want to parallelize this somehow
        for (int i = 0; i < list.length; i++) {
            // Initialize grep request
            GrepRequest request = new GrepRequest(grepPattern, list[i].getLogFile(), cli.optionList);
            System.out.println("Sending the grepRequest " + request);
            // Send request and print results
            GrepSocketHandler.grepRequest(list[i].getIp(), list[i].getPort(), request);
        }
    }

    private static class CommandLineInput {
        public List<String> optionList;
        public String pattern;

        public CommandLineInput(String[] args) throws ParseException {
            Options options = generateOptions();
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            optionList = new ArrayList<>();
            pattern = null;
            if (!cmd.hasOption("pattern")) {
                throw new ParseException("search input is a required argument");
            }

            if (cmd.hasOption("c")) {
                optionList.add("c");
            }
            if (cmd.hasOption("F")) {
                optionList.add("F");
            }
            if (cmd.hasOption("i")) {
                optionList.add("i");
            }
            if (cmd.hasOption("v")) {
                optionList.add("v");
            }
            if (cmd.hasOption("n")) {
                optionList.add("n");
            }
            if (cmd.hasOption("l")) {
                optionList.add("l");
            }
            if (cmd.hasOption("x")) {
                optionList.add("x");
            }
            if (cmd.hasOption("pattern")) {
                pattern = cmd.getOptionValue("pattern");
            }

        }
    }



    private static Options generateOptions() {
        Options options =new Options();
        options.addOption(new Option("c","grep option to count matching lines"));
        options.addOption(new Option("F", "grep option to use fixed-strings matching instead of regular expressions"));
        options.addOption(new Option("i","grep option to ignore case"));
        options.addOption(new Option("v","grep option to invert match"));
        options.addOption(new Option("n","grep option to prefix each line of output with the line number within its input file"));
        options.addOption(new Option("l","grep option to print the name of each input file"));
        options.addOption(new Option("x","grep option to match whole sentence only"));
        options.addOption(OptionBuilder.withArgName("pattern").hasArg().withDescription("*Required Option* grep string")
                .create("pattern"));
        return options;
    }



    private static class MachineLocation {
        private String ip;
        private int port;
        private String logFile;

        public MachineLocation(String ip, int port, String logFile) {
            this.ip = ip;
            this.port = port;
            this.logFile = logFile;
        }

        public String getLogFile() {
            return logFile;
        }

        public String getIp() {
            return ip;
        }

        public int getPort() {
            return port;
        }
    }
}
