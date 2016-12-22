package fuckServer;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by zuston on 16-12-22.
 */
public class Socket {
    public SocketChannel socketChannel = null;
    public Long socketId;
    public String msg = null;
    public ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
    public Socket(SocketChannel sc) {
        this.socketChannel = sc;
    }



}
