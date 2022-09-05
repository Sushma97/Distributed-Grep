package com.cs425;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        try {
            Socket server = new Socket(ip, port);
            ObjectOutputStream outputStream = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(server.getInputStream());
            sendGrepRequest(request, outputStream);
            GrepResponse response = receiveGrepResponse(inputStream);
            inputStream.close();
            outputStream.close();
            server.close();
            System.out.println(response);
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in ClientThread for server " + ip);
            e.printStackTrace();
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
