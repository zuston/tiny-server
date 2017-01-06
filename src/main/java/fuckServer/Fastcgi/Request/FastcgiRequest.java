package fuckServer.Fastcgi.Request;

import fuckServer.Fastcgi.Enginee.FastcgiConst;
import fuckServer.Fastcgi.Fastcgi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zuston on 17/1/6.
 */
public class FastcgiRequest {
    public static class Builder{
        private int version;
        private int type;
        private int requestId;
        private int contentLength;
        private int paddingLength;
        private int reserved;
        private FastcgiRequstBody contentData;
        private byte[] paddingData;
        private Map<String, String> fcgiParams;

        public Builder() {
            this.version = FastcgiConst.FCGI_VERSION;
            this.reserved = (byte) 0;
        }

        public FastcgiRequest.Builder version(int version) {
            this.version = (byte) version;
            return this;
        }

        public FastcgiRequest.Builder requestId(int requestId) {
            this.requestId = requestId;
            return this;
        }

        public FastcgiRequest.Builder type(int type) {
            this.type = (byte) type;
            if (FastcgiConst.FCGI_PARAMS == type) {
                fcgiParams = new HashMap<String, String>();
            }
            return this;
        }

        public FastcgiRequest.Builder content(FastcgiRequstBody contentData) {
            if (contentData != null) {
                this.contentData = contentData;
                this.contentLength = contentData.contentLength();
            } else {
                this.contentLength = 0;
            }
            return this;
        }

        public FastcgiRequest build() {
            return new FastcgiRequest(this);
        }
    }


    private int version;
    private int type;
    private int requestIdB1;
    private int requestIdB0;
    private int contentLengthB1;
    private int contentLengthB0;
    private int paddingLength;
    private int reserved;
    private FastcgiRequstBody contentData;
    private byte[] paddingData;
    private ByteArrayOutputStream out;

    public FastcgiRequest(FastcgiRequest.Builder builder){
        this.version = builder.version;
        this.type = builder.type;
        this.requestIdB1 = (byte) ((builder.requestId >> 8) & 0xFF);
        this.requestIdB0 = (byte) (builder.requestId & 0xFF);
        this.contentLengthB1 = (byte) ((builder.contentLength >> 8) & 0xFF);
        this.contentLengthB0 = (byte) builder.contentLength;
        this.paddingLength = (byte) builder.paddingLength;
        this.reserved = (byte) builder.reserved;
        if (builder.paddingData != null) {
            this.paddingData = builder.paddingData;
        }
        this.contentData = builder.contentData;
        out = new ByteArrayOutputStream();
        this.transform2Stream();
    }

    private void transform2Stream() {
        out.write(this.version);
        out.write(this.type);
        out.write(this.requestIdB1);
        out.write(this.requestIdB0);
        out.write(this.contentLengthB1);
        out.write(this.contentLengthB0);
        out.write(0);
        out.write(0);
        if (this.contentData != null) {
            this.contentData.writeTo(out);
        }
    }

    public boolean writeTo(OutputStream os) {
        try {
            out.writeTo(os);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
