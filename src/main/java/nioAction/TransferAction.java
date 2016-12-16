package nioAction;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Created by zuston on 16-12-16.
 */
public class TransferAction {

    public static void main(String[] args) throws IOException {
        RandomAccessFile file1 = new RandomAccessFile("data/a.txt","rw");
        RandomAccessFile file2 = new RandomAccessFile("data/b.txt","rw");


        FileChannel channel1 = file1.getChannel();
        FileChannel channel2 = file2.getChannel();

        long position = 0;
        long count = channel1.size();

        channel2.transferFrom(channel1,position,count);

//        ByteBuffer br = ByteBuffer.allocate(48);
//        int brFlag = channel2.read(br);
//        while (brFlag!=-1){
//            br.flip();
//            while (br.hasRemaining()){
//                System.out.println((char)br.get());
//            }
//
//            br.clear();
//            brFlag = channel2.read(br);
//        }
        file1.close();
        file2.close();
    }
}
