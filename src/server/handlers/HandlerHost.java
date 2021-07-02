package server.handlers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.client.ClientManager;
import server.util.Json;
import server.util.ServerResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class  HandlerHost implements HttpHandler {

    private static boolean success = false;
    private static String port = " ";

    @Override
    public void handle(HttpExchange exchange) {

        ServerResponse response = new ServerResponse(200, null, "OK");

        String json = Json.toJson(response);

        setPort(exchange.getRemoteAddress().getHostString());

        try {
            exchange.sendResponseHeaders(200 ,json.length());
            OutputStream outStream = exchange.getResponseBody();
            outStream.write(json.getBytes(StandardCharsets.UTF_8));
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setSuccess(true);


    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        HandlerHost.port = port;
    }

    public static boolean isSuccess() {
        return success;
    }

    public static void setSuccess(boolean success) {
        HandlerHost.success = success;
    }
}
