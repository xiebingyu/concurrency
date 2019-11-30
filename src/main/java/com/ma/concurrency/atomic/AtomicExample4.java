package com.ma.concurrency.atomic;
import com.ma.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
@ThreadSafe
public class AtomicExample4 {

    private  static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {

        Unsafe unsafe = Unsafe.getUnsafe();
        Integer intNumber = 0;
        int intNumber1 = 0;

        try {
            unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        //执行count = 2
        count.compareAndSet(0,2);

        //不执行
        count.compareAndSet(0,1);

        //不执行
        count.compareAndSet(1,3);

        //执行count = 4
        count.compareAndSet(2,4);

        //不执行
        count.compareAndSet(3,5);


        log.info("count:{}",count.get());
    }
}
