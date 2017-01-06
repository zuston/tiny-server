package fuckServer.Fastcgi.Request;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zuston on 17/1/6.
 */
public class FastcgiBeginRequstBody implements FastcgiRequstBody{

    public static class Builder{
        private int role;
        private int flag;

        public FastcgiBeginRequstBody.Builder role(int role){
            this.role = role;
            return this;
        }

        public FastcgiBeginRequstBody.Builder flag(int flag){
            this.flag = flag;
            return this;
        }

        public FastcgiBeginRequstBody build(){
            return new FastcgiBeginRequstBody(this);
        }
    }


    private int roleB1;
    private int roleB0;
    private int flag;
    private byte[] reserverd = {0,0,0,0,0};
    public FastcgiBeginRequstBody(FastcgiBeginRequstBody.Builder builder){
        this.roleB1 = (byte)(builder.role>>8 & 0xFF);
        this.roleB0 = (byte)(builder.role & 0xFF);
        this.flag = (byte)(builder.flag);
    }

    public int contentLength(){
        return 8;
    }

    public void writeTo(OutputStream os) {
        try {
            os.write((byte)roleB1);
            os.write((byte)roleB0);
            os.write((byte)flag);
            os.write(reserverd,0,reserverd.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
