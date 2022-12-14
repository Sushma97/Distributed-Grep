package com.cs425;

import com.sun.media.sound.InvalidDataException;


import java.io.IOException;
import java.net.ServerSocket;

/**
 * Class that launches server and responds to client query request parallel.
 */
public class Server {
    private static ServerSocket server;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new InvalidDataException("Please input the port number server should run on");
        }
        try {
            // Create socket
            server = new ServerSocket(Integer.parseInt(args[0]));
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Please enter valid port number");
        }


        // Unterminating loop
        while (true) {
            // Accept a socket connection
            System.out.println("Waiting for client grep request");
            // Handles each request in separate thread
            GrepSocketHandler.respondToGrepRequest(server);
        }
    }
}
