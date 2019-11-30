package com.ma.concurrency.atomic;
import com.ma.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

@Slf4j
@ThreadSafe
public class AtomicUnSafeExample2 {

    private static final int THREADS_CONUT = 20;
    public static volatile int count = 0;

    public static void increase() {
        count++;
    }

    public static void main(String[] args) {

        Unsafe unsafe = Unsafe.getUnsafe();

        Thread[] threads = new Thread[THREADS_CONUT];

        for (int i = 0; i < THREADS_CONUT; i++) {

            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        increase();
                    }
                }
            });
            threads[i].start();

        }

        while (Thread.activeCount() > 1) {

            Thread.yield();

        }


        System.out.println(count);
    }


}
