package fuckServer.Bean;

import fuckServer.Core.Pid;

import java.nio.channels.SocketChannel;

/**
 * Created by zuston on 17-1-5.
 */
public class SocketBean {
    public SocketChannel socketChannel = null;
    public String info = null;
    public String request = null;
    public String response = null;
    public Long pid = null;

    public SocketBean(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }

    public void setPid(){
        this.pid = Pid.getPid();
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    public Long getPid() {
        return pid;
    }
}
