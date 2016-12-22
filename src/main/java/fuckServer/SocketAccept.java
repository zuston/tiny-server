package fuckServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by zuston on 16-12-20.
 */
public class SocketAccept implements Runnable{

    public int port = 0;
    public ArrayBlockingQueue queue = new ArrayBlockingQueue(1024);
    public ServerSocketChannel ssc = null;

    public SocketAccept(int port, ArrayBlockingQueue requestQueue) {
        this.port = port;
        this.queue = requestQueue;
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
                System.out.println("accept the request from "+sc.socket().getRemoteSocketAddress());
                this.queue.add(new Socket(sc));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
