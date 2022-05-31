package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.services.UsersServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        UsersServiceImpl service = context.getBean(UsersServiceImpl.class);
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = server.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    output.writeUTF("Hello from Server!");
                    String msg;
                    do {
                        msg = input.readUTF();
                    } while (!msg.equals("signUp"));
                    while (true) {
                        output.writeUTF("Enter username:");
                        output.flush();
                        msg = input.readUTF();
                        if (msg.isEmpty()) {
                            output.writeUTF("Try again");
                            output.flush();
                        } else if (service.isExist(msg)) {
                            output.writeUTF("User already exists");
                            output.flush();
                        } else {
                            break;
                        }
                    }
                    String name = msg;
                    do {
                        output.writeUTF("Enter password:");
                        output.flush();
                        msg = input.readUTF();
                    } while (msg.isEmpty());
                    service.signUp(name, msg);
                    output.writeUTF("Successful!");
                    output.flush();
                } catch (SocketException | EOFException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
