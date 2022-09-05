package com.cs425;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static ServerSocket server;
    private static int port = 9876;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        server = new ServerSocket(port);

        // Unterminating loop
        while(true) {
            // Accept a socket connection
            System.out.println("Waiting for client grep request");

            GrepSocketHandler.respondToGrepRequest(server);
        }
    }
}
