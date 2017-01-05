package fuckServer.Http;

import fuckServer.Http.Handler.CoreHandler;
import fuckServer.Http.Handler.RequestHandler;
import fuckServer.Http.Handler.ResponseHandler;
import fuckServer.bean.HttpBean;
import fuckServer.bean.SocketBean;

/**
 * Created by zuston on 17-1-5.
 */
public class Http {
    public SocketBean socket = null;

    public Http(SocketBean socket) {
        this.socket = socket;
    }

    public void parser() {
        RequestHandler reqHandler = new RequestHandler(socket.info);
        HttpBean servlet = reqHandler.init();
        CoreHandler corehandler = new CoreHandler(servlet);
        String html = corehandler.init();
        ResponseHandler respHandler = new ResponseHandler(html);
        this.socket.response = respHandler.generate();
    }
}
