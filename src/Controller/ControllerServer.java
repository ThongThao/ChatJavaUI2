package Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Service.Databasehandler;

public class ControllerServer {

    @FXML
    private TextArea logArea;

    private ExecutorService threadPool;
    private final int PORT = 12345;  // The port on which the server listens for client connections
    private final ConcurrentHashMap<Socket, ObjectOutputStream> clients = new ConcurrentHashMap<>();

    public void startServer() {
        threadPool = Executors.newFixedThreadPool(10); // Allow a maximum of 10 simultaneous clients

        logMessage("Starting server on port " + PORT);

        // Run server in a separate thread
        Thread serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                logMessage("Server started and listening for connections...");

                while (true) {
                    Socket clientSocket = serverSocket.accept(); // Accept client connection
                    logMessage("New client connected: " + clientSocket.getInetAddress());

                    // Handle the client in a separate thread
                    threadPool.execute(() -> handleClient(clientSocket));
                }
            } catch (IOException e) {
                logMessage("Error starting server: " + e.getMessage());
            }
        });

        serverThread.setDaemon(true); // The server thread will terminate when the application closes
        serverThread.start();
    }

    // Helper method to log messages in the TextArea
    private void logMessage(String message) {
        Platform.runLater(() -> logArea.appendText(message + "\n"));
    }

    // This method handles each client connection
    private void handleClient(Socket clientSocket) {
        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

            // Store the output stream to send messages later
            clients.put(clientSocket, output);
            logMessage("Client handler started for: " + clientSocket.getInetAddress());

            Object received;
            while ((received = input.readObject()) != null) {
                String message = received.toString().trim();
                if (message.startsWith("LOGIN")) {
                    String[] loginInfo = message.split(" ");
                    
                    // Ensure there are at least 3 parts: LOGIN, username, and password
                    if (loginInfo.length == 3) {
                        String username = loginInfo[1];
                        String password = loginInfo[2];

                        // Validate the login credentials using Databasehandler
                        boolean loginSuccess = Databasehandler.validateLogin(username, password);

                        // Send response to client
                        if (loginSuccess) {
                            output.writeObject("SUCCESS");
                        } else {
                            output.writeObject("Invalid username or password");
                        }
                        output.flush();
                    } else {
                        // If the message format is invalid, send an error message back
                        output.writeObject("ERROR: Invalid LOGIN format. Expected 'LOGIN <username> <password>'");
                        output.flush();
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            logMessage("Client disconnected: " + clientSocket.getInetAddress());
        } finally {
            clients.remove(clientSocket); // Remove the client when disconnected
            try {
                clientSocket.close();
            } catch (IOException e) {
                logMessage("Error closing client socket: " + e.getMessage());
            }
        }
    }


    // Method to broadcast messages to all clients except the sender
    private void broadcastMessage(Socket sender, String message) {
        clients.forEach((client, output) -> {
            if (!client.equals(sender)) { // Don't send the message back to the sender
                try {
                    output.writeObject(message);
                    output.flush();
                } catch (IOException e) {
                    logMessage("Error sending message to client: " + e.getMessage());
                }
            }
        });
    }
}
