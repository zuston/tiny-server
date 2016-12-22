package fuckServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by zuston on 16-12-20.
 */
public class SocketHandler implements Runnable{

    public ArrayBlockingQueue<Socket> queue = null;

    public Selector readSelector = null;
    public Selector writeSeletor = null;

    public long messageTip = 0;
    public HashMap<Long,ByteBuffer> messageContainer = new HashMap<Long, ByteBuffer>();

    public SocketHandler(ArrayBlockingQueue requestQueue) throws IOException {
        this.queue = requestQueue;
        this.readSelector = Selector.open();
        this.writeSeletor = Selector.open();
    }

    public void run(){
        while(true){
            try {
                register();
                read();
                write();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void register() throws IOException{
        Socket socket = this.queue.poll();
        while (socket!=null){
            System.out.println("get the queue");
            socket.socketChannel.configureBlocking(false);
            socket.socketId = this.messageTip++;
            SelectionKey selectionKey = socket.socketChannel.register(this.readSelector,SelectionKey.OP_READ);
            selectionKey.attach(socket);
            socket = this.queue.poll();
        }
    }

    public void read() throws IOException{
        int readKey = this.readSelector.selectNow();
        if (readKey>0){
            Set<SelectionKey> selectionKeySet = this.readSelector.selectedKeys();
            Iterator<SelectionKey> iter = selectionKeySet.iterator();
            while (iter.hasNext()){
                SelectionKey sk = iter.next();
                readAction(sk);
                iter.remove();
            }
        }
    }

    private void readAction(SelectionKey sk) throws IOException {
        Socket socket = (Socket) sk.attachment();

        int bufferReadKey = socket.socketChannel.read(socket.byteBuffer);

        if(bufferReadKey==-1){
            sk.cancel();
            sk.channel().close();
        }else{
            SelectionKey skWriter = socket.socketChannel.register(this.writeSeletor,SelectionKey.OP_WRITE);
            skWriter.attach(socket);
        }
    }

    public void write() throws IOException{
        int selectionKeyWrite = this.writeSeletor.selectNow();
        if (selectionKeyWrite>0){
            Set<SelectionKey> selectionKeyIterator = this.writeSeletor.selectedKeys();
            Iterator<SelectionKey> iter = selectionKeyIterator.iterator();
            while (iter.hasNext()){
                SelectionKey sk = iter.next();
                Socket socket = (Socket) sk.attachment();
                socket.byteBuffer.flip();
                socket.socketChannel.write(socket.byteBuffer);
                socket.byteBuffer.clear();
                iter.remove();
            }
            selectionKeyIterator.clear();
        }
    }
}
