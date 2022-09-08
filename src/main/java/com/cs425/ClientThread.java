package com.cs425;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

public class ClientThread extends Thread {
    private String ip;
    private Integer port;
    private GrepRequest request;

    public ClientThread(String ip, Integer port, GrepRequest request){
        this.ip = ip;
        this.port = port;
        this.request = request;
    }

    public void run(){
        GrepResponse response;
        try {
            // Open resources
            Socket server = new Socket(ip, port);
            ObjectOutputStream outputStream = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(server.getInputStream());

            sendGrepRequest(request, outputStream);
            response = receiveGrepResponse(inputStream);

            // Close resources
            inputStream.close();
            outputStream.close();
            server.close();
        } catch (ConnectException exception) {
            // Error handling for fault tolerance if connection refused or unavailable
            // Return uninitialized GrepResponse, so caller knows no connection was established
            response = new GrepResponse();
        } catch (IOException | ClassNotFoundException e) {
            // Some other error occurred
            System.out.println("Error in ClientThread for server " + ip);
            e.printStackTrace();
            return;
        }

        // Print results
        if (response.isInitialized()) {
            System.out.println(response);
        } else {
            System.out.println("Machine (IP: " + ip + ", Port: " + port + ") offline.");
        }
    }

    private static void sendGrepRequest(GrepRequest request, ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(request);
        outputStream.flush();
    }

    private static GrepResponse receiveGrepResponse(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        return (GrepResponse) inputStream.readObject();
    }
}