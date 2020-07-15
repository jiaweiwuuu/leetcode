package multiThread;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        MyThread t2 = new MyThread();
MyThreadUsingLock t1 = new MyThreadUsingLock();
        new Thread(t1).start();
        new Thread(t1).start();
        new Thread(t1).start();
        new Thread(t1).start();
    }
}
