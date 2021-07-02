package server.client;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import server.util.Json;
import server.util.ServerResponse;

import java.io.IOException;
import java.lang.reflect.Type;

public class ClientManager {

    private static String host;

    public static ServerResponse get(String endpoint, Type type) {

        String actualHost = host + endpoint;

        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        ServerResponse response = new ServerResponse(1, null, "");

        try {
            HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(actualHost));
            HttpResponse httpResponse = request.execute();
            response = Json.fromJson(type, httpResponse.parseAsString());

            httpResponse.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setIp(host);
        return response;
    }

    public static ServerResponse post(String endPoint, Object data, Type type){

        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        ServerResponse response = new ServerResponse(1, null, "");
        String actual = getHost() + endPoint;

        try{
            HttpContent content = ByteArrayContent.fromString(null, Json.toJson(data));
            HttpRequest httpRequest = requestFactory.buildPostRequest(new GenericUrl(actual), content);
            httpRequest.getHeaders().setContentType("application/json");

            HttpResponse httpResponse = httpRequest.execute();
            response = Json.fromJson(type, httpResponse.parseAsString());
            httpResponse.disconnect();

        }catch (Exception e){
            e.printStackTrace();
        }

        return response;

    }

    public static void setIp(String ip) {
        host = ip;
    }

    public static String getHost() {
        return host;
    }
}
