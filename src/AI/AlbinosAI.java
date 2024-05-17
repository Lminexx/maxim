package AI;
import Rabbits.*;
import Template.Singleton;
public class AlbinosAI extends BaseAI {
    public AlbinosAI() {
        Thread thread = new Thread(() -> {
            while(true){
                synchronized (AlbinosAI.class){
                    while(isSleep()){
                        try {
                            AlbinosAI.class.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (Rabbit rabbit : Singleton.getInstance()){
                    if(rabbit instanceof AlbinosRabbit){
                        rabbit.jump(1);
                    }
                }
            }
        });
        thread.setDaemon(true);
        setThread(thread);
    }
    public void start() {
        getThread().start();
    }
}

