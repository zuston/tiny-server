package fuckServer.Bean;

import java.util.HashMap;

/**
 * Created by zuston on 17-1-5.
 */
public class HttpBean {

    public String method;
    public String url;
    public String protocol;
    public HashMap<String,String> request = new HashMap<String, String>();

    public HttpBean(String method, String url, String protocol, HashMap<String, String> params) {
        this.method = method;
        this.url = url;
        this.protocol = protocol;

        this.request = params;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }

    public HashMap<String, String> getRequest() {
        return request;
    }
}
