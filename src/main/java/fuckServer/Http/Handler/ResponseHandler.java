package fuckServer.Http.Handler;

import java.util.Date;

/**
 * Created by zuston on 17-1-5.
 */
public class ResponseHandler {
    public String html;
    public ResponseHandler(String html) {
        this.html = html;
    }

    public String generate() {
        String data = "HTTP/1.1 200 OK\r\n" +
                "    Server: localhost\r\n" +
                "    Date: "+new Date().toString()+"\r\n" +
                "    Content-Type: text/html; charset=utf-8\r\n" +
                "    Transfer-Encoding: chunked\r\n" +
                "    Status: 200 OK\r\n" +
                "    Cache-Control: no-cache\r\n" +
                "    Vary: X-PJAX\r\n\r\n" +
                html;
        return data;
    }
}
