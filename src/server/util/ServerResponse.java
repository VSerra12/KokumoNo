package server.util;

public class ServerResponse {


    private int code;
    private HostSend data;
    private String response;

    public ServerResponse(int code, HostSend data, String response) {
        this.code = code;
        this.data = data;
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HostSend getData() {
        return data;
    }

    public void setData(HostSend data) {
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
