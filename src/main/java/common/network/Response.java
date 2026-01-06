package common.network;

import java.io.Serializable;

public class Response implements Serializable {
    private ResponseStatus status;
    private Object data;
    private String message;

    public Response(ResponseStatus status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
