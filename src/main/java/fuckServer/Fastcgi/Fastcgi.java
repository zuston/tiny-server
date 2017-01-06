package fuckServer.Fastcgi;


import fuckServer.Fastcgi.Enginee.FastcgiConst;
import fuckServer.Fastcgi.Enginee.FastcgiEngine;
import fuckServer.Fastcgi.Request.*;
import fuckServer.Fastcgi.Response.FastcgiResponse;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.Random;

/**
 * Created by zuston on 17/1/5.
 */
public class Fastcgi {

    private Socket cgiSocket;

    public Fastcgi(String addr,int port){
        try {
            cgiSocket = new Socket(addr,port);
            cgiSocket.setReuseAddress(true);
            cgiSocket.setKeepAlive(true);
            cgiSocket.setSoTimeout(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public FastcgiResponse init(Map<String, String> params, String content) throws IOException {
        Random random = new Random();
        int requestId = random.nextInt((1 << 8)-1);
        FastcgiEngine engine = new FastcgiEngine(this.cgiSocket);

        FastcgiRequstBody beginRequestBody = new FastcgiBeginRequstBody.Builder()
                .role(1)
                .flag(0)
                .build();
        FastcgiRequest beginRequest = new FastcgiRequest.Builder()
                .version(FastcgiConst.FCGI_VERSION)
                .type(FastcgiConst.FCGI_BEGIN_REQUEST)
                .requestId(requestId)
                .content(beginRequestBody)
                .build();
        engine.execute(beginRequest);


        FastcgiRequstBody nameAndValueBody = new FastcgiNameValueRequestBody.Builder()
                .addParams(params)
                .build();
        FastcgiRequest nameAndValueRequest = new FastcgiRequest.Builder()
                .version(FastcgiConst.FCGI_VERSION)
                .type(FastcgiConst.FCGI_PARAMS)
                .requestId(requestId)
                .content(nameAndValueBody)
                .build();
        engine.execute(nameAndValueRequest);

        FastcgiRequest endParamsRequest = new FastcgiRequest.Builder()
                .version(FastcgiConst.FCGI_VERSION)
                .type(FastcgiConst.FCGI_PARAMS)
                .requestId(requestId)
                .content(null)
                .build();
        engine.execute(endParamsRequest);

        if (content != null && content.length() > 0) {
            FastcgiRequstBody bodyRequestBody = new FastcgiContentBody(content.getBytes());
            FastcgiRequest bodyRequest = new FastcgiRequest.Builder()
                    .version(FastcgiConst.FCGI_VERSION)
                    .type(FastcgiConst.FCGI_STDIN)
                    .requestId(requestId)
                    .content(bodyRequestBody)
                    .build();
            engine.execute(bodyRequest);
        }

        FastcgiRequest endBodyRequest = new FastcgiRequest.Builder()
                .version(FastcgiConst.FCGI_VERSION)
                .type(FastcgiConst.FCGI_STDIN)
                .requestId(requestId)
                .content(null)
                .build();
        engine.execute(endBodyRequest);

        return engine.resp();
    }
}