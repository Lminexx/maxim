package AI;
import Rabbits.*;
import Template.Singleton;
public class BasicAI extends BaseAI {
    public BasicAI() {
        Thread thread = new Thread(){
            @Override
            public void run(){
                while(true){
                    synchronized (BasicAI.class){
                        while(isSleep()){
                            try {
                                BasicAI.class.wait();
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
                        if(rabbit instanceof BasicRabbit){
                            rabbit.jump(1);
                        }
                    }
                }
            }
        };
        thread.setDaemon(true);
        setThread(thread);
    }
    public void start() {
        getThread().start();
    }
}

