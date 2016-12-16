package nioAction;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by zuston on 16-12-16.
 */
public class ServerSocketChannelAction {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel scc = ServerSocketChannel.open();
        scc.socket().bind(new InetSocketAddress(19999));
        scc.configureBlocking(false);

        System.out.println("========web server listenning========");
        while (true){
            SocketChannel sc = scc.accept();
            if(sc!=null){
                System.out.println(sc.socket().getInetAddress()+" connect");
                Thread t = new Thread(new listener(sc));
                t.start();
            }
        }


    }

}

class listener implements Runnable{

    public SocketChannel sc;

    public listener(SocketChannel sc){
        this.sc = sc;
    }

    public void run() {
        ByteBuffer buf = ByteBuffer.allocate(10);
        int rf = 0;
        try {
            rf = this.sc.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (rf!=-1){
            StringBuilder data = new StringBuilder();
            buf.flip();
            while (buf.hasRemaining()){
                char onchar = (char)buf.get();
                data.append(String.valueOf(onchar));
                if (onchar=='0'){
                    System.out.println(this.sc.socket().getInetAddress()+" exit");
                    try {
                        this.sc.close();
                        Thread.currentThread().stop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(data);
            buf.clear();
            try {
                rf = this.sc.read(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}