package fuckServer.Http.Handler;

import fuckServer.Bean.FastcgiBean;
import fuckServer.Bean.HttpBean;
import fuckServer.Fastcgi.Fastcgi;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zuston on 17-1-5.
 */
public class CoreHandler {
    public HttpBean servlet;
    public String method;
    public String uri;
    public HashMap<String,String> params = new HashMap<String, String>();
    public FastcgiBean fastcgiBean = null;
    public CoreHandler(HttpBean servlet) {
        this.servlet = servlet;
        this.method = servlet.getMethod();
        this.uri = servlet.getUrl();
        this.params = servlet.getRequest();

        this.fastcgiBean = new FastcgiBean(19999
                ,"127.0.0.1"
                ,"/Users/zuston/dev/fuckServer/PhpApp"
                ,1);
    }

    public CoreHandler() {

    }

    public String initPhp() throws IOException {
        Fastcgi client = new Fastcgi("127.0.0.1",19999);
        String content = contentGenerate(this.params);
        String uri = this.uri;
        Map<String, String> params = new HashMap<String, String>();
        String documentRoot = "/Users/zuston/dev/fuckServer/PhpApp";
        params.put("GATEWAY_INTERFACE", "FastCGI/1.0");
        params.put("REQUEST_METHOD", "POST");
        params.put("SCRIPT_FILENAME", documentRoot + "/index.php");
        params.put("SCRIPT_NAME","/index.php");
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
        return parseFastcgi(client.init(params,content).toString());
    }

    public String contentGenerate(HashMap<String,String> hm){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,String> md:hm.entrySet()){
            sb.append(md.getKey());
            sb.append("=");
            sb.append(md.getValue());
            sb.append("&");
        }
        String res = sb.substring(0,sb.lastIndexOf("&"));
        return res;
    }

    public String parseFastcgi(String content){
        String d = content.split("\r\n\r\n")[1];
        int len = d.split("\n").length;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String s:d.split("\n")){
            if(i==len-1){
                break;
            }
            i++;
            sb.append(s);
            sb.append("\n");
        }

        return sb.toString();
    }

    // TODO: 17/1/6 完成java的框架
    public String initJava(){
        return null;
    }

}
