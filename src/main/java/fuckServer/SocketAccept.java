package fuckServer;

import fuckServer.Bean.SocketBean;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zuston on 16-12-20.
 */
public class SocketAccept implements Runnable{

    public int port = 0;
    public ArrayBlockingQueue queue = new ArrayBlockingQueue(1024);
    public ServerSocketChannel ssc = null;
    public Logger logger = null;

    public SocketAccept(int port, ArrayBlockingQueue requestQueue) {
        this.port = port;
        this.queue = requestQueue;
        logger = Logger.getLogger("server");
        logger.setLevel(Level.INFO);
    }

    public void run() {
        try {
            this.ssc = ServerSocketChannel.open();
            this.ssc.socket().bind(new InetSocketAddress(this.port));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while(true){
            try {
                SocketChannel sc = this.ssc.accept();
                SocketBean sb = new SocketBean(sc);
                sb.setPid();
                this.queue.add(sb);
                logger.info("["+sb.getPid()+"]accept connection");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
