package common.network;

import java.io.Serializable;
import java.util.Map;

public class Request implements Serializable {
    private RequestType type;
    private Map<String, Object> data;

    public Request(RequestType type, Map<String, Object> data) {
        this.type = type;
        this.data = data;
    }

    public RequestType getType() {
        return type;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
