package fuckServer;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zuston on 16-12-20.
 */
public class Server {

    public int port = 0;
    public SocketAccept socketAccept = null;
    public SocketHandler socketHandler = null;
    public Server(int port){
        this.port = port;
    }

    public void init() throws IOException {
        ArrayBlockingQueue requestQueue = new ArrayBlockingQueue(1024);

        this.socketAccept = new SocketAccept(port,requestQueue);
        this.socketHandler = new SocketHandler(requestQueue);

        new Thread(this.socketAccept).start();
        new Thread(this.socketHandler).start();

        Logger logger = Logger.getLogger("server");
        logger.setLevel(Level.INFO);

        logger.info("server is running");
    }

}
