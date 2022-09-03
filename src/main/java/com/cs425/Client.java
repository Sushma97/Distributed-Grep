package com.cs425;

import java.io.IOException;
import java.net.UnknownHostException;

import com.cs425.GrepRequest.GrepResponse;

public class Client {
    static final MachineLocation[] list = {
        /* 1. */ new MachineLocation("0.0.0.0", 9876),
        /* 2. */ new MachineLocation("0.0.0.0", 9876),
        /* 3. */ new MachineLocation("0.0.0.0", 9876),
        /* 4. */ new MachineLocation("0.0.0.0", 9876),
        /* 5. */ new MachineLocation("0.0.0.0", 9876),
    };


    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        if (args.length < 1) {
            System.err.println("Usage: java client [grepPattern]");
        }
        // Client creates GrepRequest based on arguments
        String grepPattern = args[0];

        // Client sends out GrepRequest over sockets to each server
        // We'll need some sort of lookup table for each machine
        // We may also want to parallelize this somehow
        for (int i = 0; i < list.length; i++) {
            // Initialize grep request
            String filename = "machine." + i + ".log";
            GrepRequest request = new GrepRequest(grepPattern, filename);

            // Send request and print results
            GrepResponse grepResponse = GrepSocketHandler.grepRequest(list[i].getIp(), list[i].getPort(), request);
            System.out.println(grepResponse);
        }
    }

    private static class MachineLocation {
        private String ip;
        private int port;

        public MachineLocation(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        public String getIp() {
            return ip;
        }

        public int getPort() {
            return port;
        }
    }
}
