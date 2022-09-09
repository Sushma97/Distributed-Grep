package com.cs425;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * API to handle sending Grep requests and responses over sockets in parallel
 */
public class GrepSocketHandler {
    /**
     * Sends a grep request and receives the response.
     * Note, the function attempts to connect the socket
     * @param host The socket of the server to connect to
     * @return the grep response from the server
     * @throws ClassNotFoundException
     */
    public static void grepRequest(String host, int port, GrepRequest request, CountDownLatch latch) throws IOException, ClassNotFoundException {
        // TODO error handling for fault tolerance if connection refused or unavailable
        ClientThread client = new ClientThread(host, port, request, latch);
        client.start();
    }

    public static void respondToGrepRequest(ServerSocket server) throws IOException, ClassNotFoundException {
        // Open resources
        Socket client = server.accept();
        //For each client connection, server starts a child thread to process the request independent of any incoming requests
        new ServerThread(client).start();
    }


}
