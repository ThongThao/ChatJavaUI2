package Service;

import java.io.*;
import java.net.Socket;

public class ClientSocketHandler {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientSocketHandler(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessage(String message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveMessage() throws IOException {
        try {
            return (String) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}
