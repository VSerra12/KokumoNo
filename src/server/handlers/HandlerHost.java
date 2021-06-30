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

    @Override
    public void handle(HttpExchange exchange) {

        String remoteAddress = exchange.getRemoteAddress().getHostName();
        int port = 8500;

        ServerResponse response = new ServerResponse(200, null, "OK");

        String json = Json.toJson(response);

        try {
            exchange.sendResponseHeaders(200 ,json.length());
            OutputStream outStream = exchange.getResponseBody();
            outStream.write(json.getBytes(StandardCharsets.UTF_8));
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClientManager.setIp(remoteAddress + ":" + port);

        setSuccess(true);


    }

    public static boolean isSuccess() {
        return success;
    }

    public static void setSuccess(boolean success) {
        HandlerHost.success = success;
    }
}
