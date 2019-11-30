package com.ma.concurrency.atomic;
import com.ma.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ThreadSafe
public class AtomicUnSafeExample1 {

    private static final int THREADS_CONUT = 20;
    public static int count = 0;

    public static void increase() {
        System.out.println(count);
        count++;
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_CONUT];
        for (int i = 0; i < THREADS_CONUT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        while (Thread.activeCount() > 1) {
            System.out.println("finished count:"+Thread.activeCount());
            //yield的意思是需要执行下一个线程
            Thread.yield();
        }

        System.out.println("finished count:"+count);
    }


}
