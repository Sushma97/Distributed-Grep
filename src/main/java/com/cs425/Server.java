package com.cs425;

import com.sun.media.sound.InvalidDataException;


import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static ServerSocket server;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length != 1){
            throw new InvalidDataException("Please input the port number server should run on");
        }
        try {
            server = new ServerSocket(Integer.parseInt(args[0]));
        }
        catch (NumberFormatException ex){
            throw new NumberFormatException("Please enter valid port number");
        }


        // Unterminating loop
        while(true) {
            // Accept a socket connection
            System.out.println("Waiting for client grep request");

            GrepSocketHandler.respondToGrepRequest(server);
        }
    }
}
