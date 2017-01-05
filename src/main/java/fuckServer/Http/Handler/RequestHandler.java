package fuckServer.Http.Handler;

import fuckServer.bean.HttpBean;

import java.util.HashMap;

/**
 * Created by zuston on 17-1-5.
 */
public class RequestHandler {
    public String header = null;

    public HashMap<String,Object> headers = new HashMap<String, Object>();
    public HashMap<String,String> params = new HashMap<String, String>();

    public RequestHandler(String info) {
        this.header = info;
    }

    public HttpBean init() {
        parse();
        return null;
    }

    public void parse(){
        String[] headers = this.header.split("\r\n");
        method(headers[0]);
        uri(headers[0]);
        protocol(headers[0]);
    }

    private void protocol(String header) {
        this.headers.put("protocol",header.split(" ")[2]);
    }

    private void uri(String header) {
        this.headers.put("url",header.split(" ")[1]);
        if(this.headers.containsKey("method")&&this.headers.get("method")=="GET"){
            initGetParams(this.headers.get("url"));
        }
    }

    private void initGetParams(Object url) {
        String URL = (String) url;
        if(URL.contains("?")){
            String paramStr = URL.substring(URL.indexOf("?"),URL.length());
            this.headers.put("url",URL.substring(0,URL.indexOf("?")));
            for (String pairs:paramStr.split("&")){
                String key = pairs.substring(0,pairs.indexOf("="));
                String value = pairs.substring(pairs.indexOf("=")+1,pairs.length());
                this.params.put(key,value);
            }
        }
    }

    private void method(String header) {
        String method = header.split(" ")[0];
        this.headers.put("method",method);

    }


}
