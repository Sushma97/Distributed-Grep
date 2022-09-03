package com.cs425;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.cs425.GrepRequest.GrepResponse;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        if (args.length < 1) {
            System.err.println("Usage: java client [grepPattern]");
        }
        // Client creates GrepRequest based on arguments
        String grepPattern = args[0];

        // Initialize sockets and streams
        Socket server = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;

        // Client sends out GrepRequest over sockets to each server
        // We'll need some sort of lookup table for each machine
        // We may also want to parallelize this somehow
        for (int i = 0; i < 5; i++) {
            // Initialize grep request
            String filename = "machine." + i + ".log";
            GrepRequest request = new GrepRequest(grepPattern, filename);

            // Connect to socket
            // TODO
            server = new Socket("0.0.0.0", 9876);
            outputStream = new ObjectOutputStream(server.getOutputStream());
            inputStream = new ObjectInputStream(server.getInputStream());

            // Stream grep request to socket
            outputStream.writeObject(request);

            // Wait for (and print) results
            GrepResponse grepResponse = (GrepResponse) inputStream.readObject();
            System.out.println(grepResponse);

            // Close resources
            server.close();
            outputStream.close();
            inputStream.close();
        }
    }
}
