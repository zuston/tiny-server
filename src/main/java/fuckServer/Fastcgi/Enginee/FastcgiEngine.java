package fuckServer.Fastcgi.Enginee;

import fuckServer.Fastcgi.Fastcgi;
import fuckServer.Fastcgi.Request.FastcgiRequest;
import fuckServer.Fastcgi.Response.FastcgiEndResponse;
import fuckServer.Fastcgi.Response.FastcgiResponse;
import fuckServer.Fastcgi.Response.FastcgiResponseHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by zuston on 17/1/6.
 */
public class FastcgiEngine {
    public Socket socket;
    public OutputStream os;
    public InputStream is;

    public FastcgiEngine(Socket socket) throws IOException {
        this.socket = socket;
        this.os = socket.getOutputStream();
        this.is = socket.getInputStream();
    }

    public void execute(FastcgiRequest request) {
        request.writeTo(os);
    }

    public FastcgiResponse resp() {
        FastcgiResponse response = new FastcgiResponse();
        int nRead;
        do {
            try {
                byte header[] = new byte[8];
                nRead = is.read(header, 0, 8);
                FastcgiResponseHeader headerHandler = new FastcgiResponseHeader(header);
                if (headerHandler.type == FastcgiConst.FCGI_STDOUT
                        || headerHandler.type == FastcgiConst.FCGI_STDERR) {
                    if (headerHandler.type == FastcgiConst.FCGI_STDERR) {
                        response.setState(FastcgiConst.FCGI_REP_ERROR);
                    }
                    response.setState(FastcgiConst.FCGI_REP_OK);
                    byte contentData[] = new byte[headerHandler.contentLength];
                    int nReadContent = is.read(contentData, 0, headerHandler.contentLength);
                    if (nReadContent != headerHandler.contentLength) {
                        response.setState(FastcgiConst.FCGI_REP_ERROR_CONTENT_LENGTH);
                        break;
                    }
                    response.appendRespContent(contentData);
                    if (headerHandler.paddingLength > 0) {
                        is.skip(headerHandler.paddingLength);
                    }
                }
                if (headerHandler.type == FastcgiConst.FCGI_END_REQUEST) {
                    byte endResponse[] = new byte[headerHandler.contentLength];
                    FastcgiEndResponse responseEnd = new FastcgiEndResponse(endResponse);
                    response.setEndResponse(responseEnd);
                    break;
                }

            } catch (IOException e) {
                //nothing
                response.setState(FastcgiConst.FCGI_REP_ERROR_IOEXCEPTION);
                break;
            }

        } while (nRead == 8);
        try {
            is.close();
            os.close();
            this.socket.close();
        } catch (IOException e) {
            //nothing
        }
        return response;
    }
}
