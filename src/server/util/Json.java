package server.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class Json {

    private static final Gson myGson = defaultGson();

    private static Gson defaultGson(){
        return new GsonBuilder()
                .create();
    }

    public static String toJson(Object data){

        return myGson.toJson(data);
    }

    public static <T> T fromJson(Type type, String json) throws FileNotFoundException {

        return myGson.fromJson(json, type);
    }
    public static <T> T fromJsonInput(Type type, InputStream json) {

        return myGson.fromJson(new InputStreamReader(json), type);
    }

}
