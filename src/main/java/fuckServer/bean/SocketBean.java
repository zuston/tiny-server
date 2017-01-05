package fuckServer.bean;

import java.nio.channels.SocketChannel;

/**
 * Created by zuston on 17-1-5.
 */
public class SocketBean {
    public SocketChannel socketChannel = null;
    public String info = null;
    public String request = null;
    public String response = null;

    public SocketBean(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }


}
