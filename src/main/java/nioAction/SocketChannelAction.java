package nioAction;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by zuston on 16-12-16.
 */
public class SocketChannelAction {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("",80));

        ByteBuffer buffer = ByteBuffer.allocate(48);
        int byread = sc.read(buffer);

        while (byread!=-1){
            // TODO: 16-12-16 read the data
            buffer.clear();
            byread = sc.read(buffer);
        }


        String data = "hello world";
        ByteBuffer br = ByteBuffer.allocate(100);
        br.clear();
        buffer.put(data.getBytes());

        while (buffer.hasRemaining()){
            sc.write(buffer);
        }

        sc.close();
    }
}
