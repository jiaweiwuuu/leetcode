package multiThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadUsingLock implements Runnable {
    private List<String> list = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    @Override
    public void run() {
        while(list.size() < 40){
            lock.lock();
            if(list.size() == 0){
                list.add("A");
            }
            else if(list.get(list.size()-1).equals("A")){
                list.add("B");
            }else if(list.get(list.size()-1).equals("B")){
                list.add("C");
            }else if(list.get(list.size()-1).equals("C")){
                list.add("D");
            }else{
                list.add("A");
            }
            System.out.println(Thread.currentThread().getName());
            System.out.println(list);
            System.out.println(list.size());
            condition.signal();
            try{
                condition.await(1000, TimeUnit.MILLISECONDS);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            finally {
                lock.unlock();
            }

        }

    }
}
