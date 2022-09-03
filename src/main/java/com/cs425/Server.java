package com.cs425;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket server;
    private static int port = 9876;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        server = new ServerSocket(port);

        // Unterminating loop
        while(true) {
            // Accept a socket connection
            System.out.println("Waiting for client grep request");
            Socket socket = server.accept();

            // Read the grep request
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            GrepRequest grepRequest = (GrepRequest) inputStream.readObject();

            // Write message to stdout
            System.out.println("Grep request received for pattern "
                                + grepRequest.getGrepPattern()
                                + " and filename "
                                + grepRequest.getFilename());

            // Perform grep and write back to socket
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            // Grep functionality has been abstracted elsewhere (see GrepRequest)
            outputStream.writeObject(grepRequest.runGrep());

            //close resources
            inputStream.close();
            outputStream.close();
            socket.close();
        }
    }
}
