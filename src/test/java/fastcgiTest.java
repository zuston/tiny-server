import fuckServer.Fastcgi.Fastcgi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zuston on 17/1/6.
 */
public class fastcgiTest {
    public static void main(String[] args) throws IOException {

        Fastcgi client = new Fastcgi("127.0.0.1",19999);
        String content = "name=zuston&age=23";
        String uri = "/index.php";
        Map<String, String> params = new HashMap<String, String>();
        String documentRoot = "/Users/zuston/dev/fuckServer/PhpApp";
        params.put("GATEWAY_INTERFACE", "FastCGI/1.0");
        params.put("REQUEST_METHOD", "POST");
        params.put("SCRIPT_FILENAME", documentRoot + uri);
        params.put("SCRIPT_NAME", uri);
        params.put("QUERY_STRING", "");
        params.put("REQUEST_URI", uri);
        params.put("DOCUMENT_ROOT", documentRoot);
        params.put("REMOTE_ADDR", "127.0.0.1");
        params.put("REMOTE_PORT", "9985");
        params.put("SERVER_ADDR", "127.0.0.1");
        params.put("SERVER_NAME", "localhost");
        params.put("SERVER_PORT", "80");
        params.put("SERVER_PROTOCOL", "HTTP/1.1");
        params.put("CONTENT_TYPE", "application/x-www-form-urlencoded");
        params.put("CONTENT_LENGTH", content.length() + "");
        System.out.println(client.init(params,content));
    }
}
