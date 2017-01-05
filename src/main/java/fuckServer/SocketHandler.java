package fuckServer;

import fuckServer.Http.Http;
import fuckServer.bean.SocketBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by zuston on 16-12-20.
 */
public class SocketHandler implements Runnable{

    public ArrayBlockingQueue<SocketBean> queue = null;

    public Selector readSelector = null;
    public Selector writeSeletor = null;
    public Http http = null;

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
        SocketBean socket = this.queue.poll();
        while (socket!=null){
            socket.socketChannel.configureBlocking(false);
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
        SocketBean socket = (SocketBean) sk.attachment();
        int size = 0 ;

        ByteBuffer tempBuffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream sos = new ByteArrayOutputStream();
        while ((size=socket.socketChannel.read(tempBuffer))>0){
            tempBuffer.flip();
            byte[] b = new byte[size];
            tempBuffer.get(b);
            sos.write(b);
            tempBuffer.clear();
        }
        socket.info = new String(sos.toByteArray());
        http = new Http(socket);
        http.parser();
        SelectionKey skWriter = socket.socketChannel.register(this.writeSeletor,SelectionKey.OP_WRITE);
        skWriter.attach(socket);
        sk.cancel();
    }

    public void write() throws IOException{
        int selectionKeyWrite = this.writeSeletor.selectNow();
        if (selectionKeyWrite>0){
            Set<SelectionKey> selectionKeyIterator = this.writeSeletor.selectedKeys();
            Iterator<SelectionKey> iter = selectionKeyIterator.iterator();
            while (iter.hasNext()){
                SelectionKey sk = iter.next();
                SocketBean socket = (SocketBean) sk.attachment();

                ByteBuffer temp = ByteBuffer.allocate(1024*1024);
                temp.put(socket.response.toString().getBytes());
                temp.flip();
                while (temp.hasRemaining()){
                    socket.socketChannel.write(temp);
                }
                temp.clear();
                socket.socketChannel.close();
                iter.remove();
            }
            selectionKeyIterator.clear();
        }
    }
}
