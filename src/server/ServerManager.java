package server;

import com.sun.net.httpserver.HttpServer;
import server.handlers.HandlerGame;
import server.handlers.HandlerGuest;
import server.handlers.HandlerHost;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerManager {

    private final static int HOST = 8800;
    private final static int GUEST = 8800;

    private static HttpServer server;

    private ServerManager() {
    }

    public static void start(int port){

        try {
            if(server == null) {
                server = HttpServer.create(new InetSocketAddress(port), 0);
                server.setExecutor(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.start();
    }

    public static void guestServer(){
        start(GUEST);
        server.createContext("/join", new HandlerGuest());

    }

    public static void hostServer(){
        start(HOST);

        HandlerHost handlerHost = new HandlerHost();
        HandlerGame handlerGame = new HandlerGame();

        server.createContext("/join", handlerHost);
        server.createContext("/game", handlerGame);

    }



}
