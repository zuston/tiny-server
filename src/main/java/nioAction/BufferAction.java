package nioAction;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by zuston on 16-12-16.
 */
public class BufferAction {

    public static void main(String[] args) throws IOException {
        //获得一个文件读取对象
        RandomAccessFile file = new RandomAccessFile("data/a.txt","rw");
        //实例一个nio channel 对象
        FileChannel inChinnel = file.getChannel();
        //获取一个buffer缓冲块，设置大小
        ByteBuffer buf = ByteBuffer.allocate(10);
        //一次从channel中读取数据至buffer中
        int bytesRead = inChinnel.read(buf);

        while (bytesRead!=-1){
            //切换buffer模式为读模式，position设置为0,limit为position value
            buf.flip();
            while (buf.hasRemaining()){
                System.out.println((char)buf.get());
//                int bytesWritten = inChinnel.write(buf);
//                if(bytesWritten!=-1){
//                    System.out.println("写入成功");
//                }
            }
            //清空整个buffer,position和limit均重置
            buf.clear();
            bytesRead = inChinnel.read(buf);
        }
        file.close();
    }


}
