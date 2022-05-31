package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.services.MessagesService;
import edu.school21.sockets.services.UsersServiceImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Session extends Thread {
    private final Server server;
    private final Socket socket;
    private String name;
    private Long id;
    private DataInputStream input;
    private DataOutputStream output;


    public Session(Socket socketForClient, Server server) {
        this.socket = socketForClient;
        this.server = server;
    }

    private void signUp() throws IOException {
        String msg;
        while (true) {
            output.writeUTF("Enter username:");
            output.flush();
            msg = input.readUTF();
            if (msg.isEmpty()) {
                output.writeUTF("Try again");
                output.flush();
            } else if (server.getUsersService().isExist(msg)) {
                output.writeUTF("User already exists");
                output.flush();
            } else {
                break;
            }
        }
        name = msg;
        do {
            output.writeUTF("Enter password:");
            output.flush();
            msg = input.readUTF();
        } while (msg.isEmpty());
        if (!server.getUsersService().signUp(name, msg)) {
            System.out.println("ploho2");
        }
        output.writeUTF("Successful!");
        output.flush();
    }

    private boolean signIn() throws IOException {
        String msg;
        while (true) {
            output.writeUTF("Enter username:");
            output.flush();
            msg = input.readUTF();
            if (msg.isEmpty() || msg.equals("\n")) {
                output.writeUTF("Try again");
                output.flush();
            } else if (!server.getUsersService().isExist(msg)) {
                output.writeUTF("No such user");
                output.flush();
                return false;
            } else {
                break;
            }
        }
        name = msg;
        do {
            output.writeUTF("Enter password:");
            output.flush();
            msg = input.readUTF();
        } while (msg.isEmpty() || msg.equals("\n"));
        return server.getUsersService().signIn(name, msg);
    }

    private void messaging() {
        try {
            id = server.getId(name);
            output.writeUTF("Start messaging");
            output.flush();
            String msg;
            while (true) {
                msg = input.readUTF();
                if (msg.equalsIgnoreCase("exit")) {
                    break;
                } else if (!msg.isEmpty() && !msg.equals("\n")) {
                    Message message = new Message(null, id, msg, null);
                    server.saveMessage(message);
                    server.broadcast(msg);
                }
                Thread.sleep(300L);
            }
        } catch (EOFException ignored) {
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
    }

    public void sendMsg(String msg){
        try {
            output.writeUTF(String.format("%s: %s", name, msg));
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initStreams(DataInputStream input, DataOutputStream output) {
        this.input = input;
        this.output = output;
    }

    private void close() {
        server.closeSession(this);
    }

    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            output.writeUTF("Hello from Server!");
            String msg;
            while (true) {
                msg = input.readUTF();
                initStreams(input, output);
                if (msg.equals("signUp")) {
                    signUp();
                    break;
                } else if (msg.equals("signIn")) {
                    if (!signIn()) {
                        socket.close();
                        return;
                    }
                    break;
                }
            }
            messaging();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
