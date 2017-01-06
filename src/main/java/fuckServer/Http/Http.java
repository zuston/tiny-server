package fuckServer.Http;

import fuckServer.Http.Handler.CoreHandler;
import fuckServer.Http.Handler.RequestHandler;
import fuckServer.Http.Handler.ResponseHandler;
import fuckServer.Bean.HttpBean;
import fuckServer.Bean.SocketBean;

import java.io.IOException;

/**
 * Created by zuston on 17-1-5.
 */
public class Http {
    public SocketBean socket = null;

    public Http(SocketBean socket) {
        this.socket = socket;
    }

    public void parser() throws IOException {
        RequestHandler reqHandler = new RequestHandler(socket.info);
        HttpBean servlet = reqHandler.init();
        CoreHandler corehandler = new CoreHandler(servlet);
        String html = corehandler.initPhp();
        ResponseHandler respHandler = new ResponseHandler(html);
        this.socket.response = respHandler.generate();
    }
}
