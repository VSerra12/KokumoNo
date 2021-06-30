package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.util.HostSend;
import server.util.Json;
import server.util.ServerResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HandlerGuest implements HttpHandler {


    private static boolean success = false;
    private static HostSend message = new HostSend(" ", " ", false,new int[3]);

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        ServerResponse response = new ServerResponse(200, null, "OK");

        String json = Json.toJson(response);

        message = Json.fromJsonInput(HostSend.class, exchange.getRequestBody());

        setMessage(message);

        //setMessage(Json.fromJson(HostSend.class, exchange.getRequestBody().toString()));

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

    public static HostSend getMessage() {
        return message;
    }

    public static void setMessage(HostSend message) {
        HandlerGuest.message = message;
    }

    public static boolean isSuccess() {
        return success;
    }

    public static void setSuccess(boolean success) {
        HandlerGuest.success = success;
    }
}
