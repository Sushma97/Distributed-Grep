package com.cs425;

import com.sun.media.sound.InvalidDataException;

import java.io.IOException;

public class Client {
    static final MachineLocation[] list = {
            /* 1. */ new MachineLocation("0.0.0.0", 9876, "machine.i.log"),
        /* 2. */ new MachineLocation("0.0.0.0", 9876, "machine.1.log"),
        /* 3. */ new MachineLocation("0.0.0.0", 9876, "machine.2.log"),
        /* 4. */ new MachineLocation("0.0.0.0", 9876, "machine.3.log"),
        /* 5. */ new MachineLocation("0.0.0.0", 9876, "machine.4.log"),
    };


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length != 1){
            throw new InvalidDataException("Please input the regex to be searched");
        }
        // Client creates GrepRequest based on arguments
        String grepPattern = args[0];
        // Client sends out GrepRequest over sockets to each server
        // We'll need some sort of lookup table for each machine
        // We may also want to parallelize this somehow
        for (int i = 0; i < list.length; i++) {
            // Initialize grep request
            GrepRequest request = new GrepRequest(grepPattern, list[i].getLogFile());
            System.out.println("Sending the grepRequest " + request);
            // Send request and print results
            GrepSocketHandler.grepRequest(list[i].getIp(), list[i].getPort(), request);
        }
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
