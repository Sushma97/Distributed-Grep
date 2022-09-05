package com.cs425;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket client;

    public ServerThread(Socket client) {
        this.client = client;
    }

    public void run() {

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());

        ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
        GrepRequest grepRequest = receiveGrepRequest(inputStream);
        System.out.println("Received request " + grepRequest);
        sendGrepResponse(grepRequest.runGrep(), outputStream);

        inputStream.close();
        outputStream.close();
        client.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in ServerThread for client " + client.getInetAddress());
            e.printStackTrace();
        }
    }

    private static GrepRequest receiveGrepRequest(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        return (GrepRequest) inputStream.readObject();
    }

    private static void sendGrepResponse(GrepResponse response, ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(response);
        outputStream.flush();
    }
}
