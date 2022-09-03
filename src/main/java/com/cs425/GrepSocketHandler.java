package com.cs425;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.cs425.GrepRequest.GrepResponse;

/**
 * API to handle sending Grep requests and responses over sockets
 */
public class GrepSocketHandler {

    /**
     * Sends a grep request and receives the response.
     * Note, the function attempts to connect the socket
     * @param server The socket of the server to connect to
     * @return the grep response from the server
     * @throws ClassNotFoundException
     */
    public static GrepResponse grepRequest(String host, int port, GrepRequest request) throws IOException, ClassNotFoundException {
        // TODO error handling for fault tolerance if connection refused or unavailable
        Socket server = new Socket("0.0.0.0", 9876);
        ObjectOutputStream outputStream = new ObjectOutputStream(server.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(server.getInputStream());

        sendGrepRequest(request, outputStream);
        GrepResponse response = receiveGrepReponse(inputStream);

        inputStream.close();
        outputStream.close();
        server.close();

        return response;
    }

    public static void respondToGrepRequest(ServerSocket server) throws IOException, ClassNotFoundException {
        Socket client = server.accept();
        ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());

        GrepRequest grepRequest = receiveGrepRequest(inputStream);
        sendGrepResponse(grepRequest.runGrep(), outputStream);

        inputStream.close();
        outputStream.close();
        client.close();
    }

    /********* Private *********/

    private static void sendGrepRequest(GrepRequest request, ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(request);
    }

    private static GrepRequest receiveGrepRequest(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        return (GrepRequest) inputStream.readObject();
    }

    private static void sendGrepResponse(GrepResponse response, ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(response);
    }

    private static GrepResponse receiveGrepReponse(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        return (GrepResponse) inputStream.readObject();
    }
    
}
