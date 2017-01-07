package fuckServer.Bean;

/**
 * Created by zuston on 17/1/7.
 */
public class FastcgiBean {
    public int port;
    public String host = "127.0.0.1";
    public String documentRoot = null;
    public int URLPattern= 1;

    public FastcgiBean(int port, String host, String documentRoot, int URLPattern) {
        this.port = port;
        this.host = host;
        this.documentRoot = documentRoot;
        this.URLPattern = URLPattern;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String getDocumentRoot() {
        return documentRoot;
    }

    public int getURLPattern() {
        return URLPattern;
    }
}
