package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.Parameter;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    @Parameters(separators = "=")
    private static class Argv {
        @Parameter(names={"--server-port", "-- server-port"})
        int port = -1;
    }
    public static void main(String[] args) {
        Main main = new Main();
        Argv argv = new Argv();
        try {
            JCommander.newBuilder()
                    .addObject(argv)
                    .build()
                    .parse(args);
        } catch (ParameterException e) {
            System.out.println("Wrong parameters");
            return;
        }
        if (argv.port < 0) {
            System.out.println("Wrong parameters");
            return;
        }
        main.run(argv);
    }

    public void run(Argv argv) {
        try (Socket clientSocket = new Socket("localhost", argv.port);
             Scanner sc = new Scanner(System.in);
             DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())
        ) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    String message;
                    try {
                        while (true) {
                            message = in.readUTF();
                            System.out.println(message);
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            };
            thread.start();
            String msg;
            while (true) {
                msg = sc.nextLine();
                if (msg.equalsIgnoreCase("exit")) {
                    System.exit(0);
                }
                out.writeUTF(msg);
                out.flush();
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
    }
}