package nioAction;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by zuston on 16-12-16.
 */
public class SelectorAction {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(19999));
        ssc.configureBlocking(false);
        //流注册selector
        ssc.register(selector, SelectionKey.OP_ACCEPT);


        while (true){
            if(selector.select(3000)==0){
                System.out.print("-");
                continue;
            }
            //获取selector的迭代器
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()){
                SelectionKey sk = keyIter.next();

                //option配置
                if (sk.isAcceptable()){
                    //sk.channel()获取到的是ServerSocketChannel的值
                    SocketChannel sc = ((ServerSocketChannel)sk.channel()).accept();
                    sc.configureBlocking(false);
                    sc.register(sk.selector(),SelectionKey.OP_READ,ByteBuffer.allocate(10));
                }

                if(sk.isReadable()){
                    SocketChannel scRead = (SocketChannel) sk.channel();
                    ByteBuffer buffer = (ByteBuffer) sk.attachment();
                    int flagRead = scRead.read(buffer);
                    if(flagRead==-1){
                        scRead.close();
                    }else{
                        sk.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                    }
                }


                if(sk.isValid()&&sk.isWritable()){
                    ByteBuffer buffer = (ByteBuffer) sk.attachment();
                    buffer.flip();
                    SocketChannel sc = (SocketChannel) sk.channel();
                    sc.write(buffer);
                    if(!buffer.hasRemaining()){
                        sk.interestOps(SelectionKey.OP_READ);
                    }

                    buffer.compact();
                }

                keyIter.remove();
            }
        }
    }
}
