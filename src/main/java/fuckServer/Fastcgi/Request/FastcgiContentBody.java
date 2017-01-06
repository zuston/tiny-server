package fuckServer.Fastcgi.Request;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zuston on 17/1/6.
 */
public class FastcgiContentBody implements FastcgiRequstBody{
    public int contentLength() {
        return bos.size();
    }

    public void writeTo(OutputStream os) {
        try {
            bos.writeTo(os);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ByteArrayOutputStream bos;
    public FastcgiContentBody(byte[] content) {
        bos = new ByteArrayOutputStream();
        bos.write(content, 0, content.length);
    }
}
