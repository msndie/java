package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.UsersRepositoryImpl;
import edu.school21.sockets.services.MessagesService;
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
import java.util.ArrayList;

public class Server {
    private final int port;
    private ArrayList<Session> sessions;
    private final UsersServiceImpl usersService;
    private final MessagesService messagesService;

    public Server(int port) {
        this.port = port;
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        usersService = context.getBean(UsersServiceImpl.class);
        messagesService = context.getBean(MessagesService.class);
    }

    public void start() {
        sessions = new ArrayList<>();
        Session session;
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                session = new Session(server.accept(), this);
                sessions.add(session);
                session.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UsersServiceImpl getUsersService() {
        return usersService;
    }

    public void closeSession(Session session) {
        sessions.remove(session);
    }

    public Long getId(String name) {
        return usersService.getIdByName(name);
    }

    public void saveMessage(Message message) {
        if (!messagesService.save(message)) {
            System.out.println("Message not saved");
        }
    }

    public void broadcast(String msg) {
        for (Session session : sessions) {
            session.sendMsg(msg);
        }
    }
}
