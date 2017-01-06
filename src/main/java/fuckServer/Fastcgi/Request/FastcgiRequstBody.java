package fuckServer.Fastcgi.Request;

import java.io.OutputStream;

/**
 * Created by zuston on 17/1/6.
 */
public interface FastcgiRequstBody {
    int contentLength();
    void writeTo(OutputStream os);
}
