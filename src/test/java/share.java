/**
 * Created by zuston on 16-12-18.
 */
public class share {
    public static void main(String[] args) {
        container list = new container();
        new Thread(new thread1(list)).start();
        new Thread(new thread2(list)).start();
    }
}

class container{
    public int list = 10;
    public container(){

    }
}


class thread1 implements Runnable{

    public container list = null;
    public thread1(container list){
        this.list = list;
    }


    public void run() {
        while (true){
            if(list.list>0){
                System.out.println("current value:"+list.list--);
            }else{
                try {
                    System.out.println("waiting");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


class thread2 implements Runnable {
    public container list = null;

    public thread2(container list){
        this.list = list;
    }


    public void run() {
        while (true){
            if(this.list.list<=0){
                this.list.list += 10;
            }else{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}