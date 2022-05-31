package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import edu.school21.sockets.server.Server;
import com.beust.jcommander.Parameter;

public class Main {

    @Parameters(separators = "=")
    private class Argv {
        @Parameter(names={"--port", "-p"})
        int port = -1;
    }

    public static void main(String[] args) {
        Main main = new Main();
        Argv argv = main.new Argv();
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
        Server server = new Server(argv.port);
        server.start();
    }
}
