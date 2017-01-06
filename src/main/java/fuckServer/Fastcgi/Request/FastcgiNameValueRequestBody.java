package fuckServer.Fastcgi.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by zuston on 17/1/6.
 */
public class FastcgiNameValueRequestBody implements FastcgiRequstBody{
    private ByteArrayOutputStream out;
    private String name;
    private String value;

    private Map<String, String> params;


    private FastcgiNameValueRequestBody(FastcgiNameValueRequestBody.Builder builder) {
        out = new ByteArrayOutputStream();
        params = builder.params;
        writeParams();
    }

    private void buildNameValuePairs() {
        writeNameValue(out, this.name);
        writeNameValue(out, this.value);
        out.write(this.name.getBytes(), 0, this.name.length());
        out.write(this.value.getBytes(), 0, this.value.length());
        System.out.println(new String(out.toByteArray()));
    }

    private void writeParams() {
        if (params != null && params.size() > 0) {
            Set<Map.Entry<String, String>> sets = params.entrySet();
            Iterator<Map.Entry<String, String>> it = sets.iterator();
            while (it.hasNext()) {
                Map.Entry entry = it.next();
                String pKey = (String) entry.getKey();
                String pValue = (String) entry.getValue();
                writeNameValue(out, pKey);
                writeNameValue(out, pValue);
                out.write(pKey.getBytes(), 0, pKey.length());
                out.write(pValue.getBytes(), 0, pValue.length());
            }

        }
    }

    private void writeNameValue(ByteArrayOutputStream out, String value) {
        int valueLength = value.length();
        if (valueLength < 128) {
            out.write(valueLength);
        } else {
            out.write((valueLength >> 24) | 0x80);
            out.write((valueLength >> 16) & 0xFF);
            out.write((valueLength >> 8) & 0xFF);
            out.write(valueLength & 0xFF);
        }
    }

    public int contentLength() {
        return out.size();
    }

    public void writeTo(OutputStream os) {
        try {
            out.writeTo(os);
            out.close();
        } catch (IOException e) {
        }
    }


    public static class Builder {
        private Map<String, String> params;

        public Builder() {
        }

        public FastcgiNameValueRequestBody.Builder addParam(String name, String value) {
            if (this.params == null) {
                this.params = new HashMap<String, String>();
            }
            this.params.put(name, value);
            return this;
        }

        public FastcgiNameValueRequestBody.Builder addParams(Map<String, String> params) {

            if (this.params == null) {
                this.params = new HashMap();
            }
            this.params.putAll(params);
            return this;
        }

        public FastcgiNameValueRequestBody build() {
            return new FastcgiNameValueRequestBody(this);
        }
    }
}
