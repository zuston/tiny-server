package fuckServer.Http.Handler;

import fuckServer.Bean.HttpBean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zuston on 17-1-5.
 */
public class RequestHandler {
    public String header = null;

    public HashMap<String,String> params = new HashMap<String, String>();

    public RequestHandler(String info) {
        this.header = info;
    }

    public String METHOD;
    public String URL;
    public String PROTOCOL;

    public HttpBean init() {
        parse();
        HttpBean hb = new HttpBean(METHOD,URL,PROTOCOL,params);
        return hb;
    }

    public void parse(){
        String[] headers = this.header.split("\r\n");
        method(headers[0]);
        uri(headers);
        protocol(headers[0]);
    }

    private void protocol(String header) {
        PROTOCOL = header.split(" ")[2];
    }

    private void uri(String[] headers) {
        String header = headers[0];
        if(METHOD.equals("GET")){
            String originUrl = header.split(" ")[1];
            URL = originUrl.substring(0,originUrl.indexOf("?"));
            initGetParams(originUrl);
        }else{
            URL = header.split(" ")[1];
        }
    }

    private void initPostParams(String postString) {
        for (String pairs:postString.split("&")){
            String key = pairs.substring(0,pairs.indexOf("="));
            String value = pairs.substring(pairs.indexOf("=")+1,pairs.length());
            this.params.put(key,value);
        }
    }

    private void initGetParams(Object url) {
        String URL = (String) url;
        if(URL.contains("?")){
            String paramStr = URL.substring(URL.indexOf("?")+1,URL.length());
            for (String pairs:paramStr.split("&")){
                String key = pairs.substring(0,pairs.indexOf("="));
                String value = pairs.substring(pairs.indexOf("=")+1,pairs.length());
                this.params.put(key,value);
            }
        }
    }

    private void method(String header) {
        String method = header.split(" ")[0];
        METHOD = method;
    }


}
