package multiThread;

import java.util.ArrayList;
import java.util.List;

public class MyThread implements Runnable {
    private final List<String> list = new ArrayList<>();
    @Override
    public void run() {
        while(list.size() < 40){
            synchronized (list){
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
                try {
                    list.notifyAll();
                    list.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
