package com.ma.concurrency.atomic;
import com.ma.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/****
 * 原子相关得操作
 *
 * 什么为线程安全：
 * 原子性：提供了互斥访问，同一时刻只能有一个线程对它进行操作
 * 可见性：一个线程对主内存的修改可以及时的被其他线程观察到
 * 有序性：一个线程观察其他线程中的指令执行顺序，由于指令重排序的存在，该观察结果一般杂乱无序
 ********************************
 *
 *  这里主要是检测 的线程准入规则 semaphore
 *  在 semaphore.acquire() 和 semaphore.release()之间的代码，
 *  同一时刻只允许制定个数的线程进入，
 *
 ****************************
 */
@Slf4j
@ThreadSafe
public class AtomicExample1 {
    //请求总数
    private static int clientTotal = 5000;
    //同时并发执行的线程数
    private static int threadTotal = 200;

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        //创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //定义200个信号
        final Semaphore semaphore = new Semaphore(threadTotal);

        //countDownLatch : 允许一个或者多个线程一直等待
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        //开辟0到5000个线程，
        for(int i = 0;i < clientTotal ; i++)
            executorService.execute(() -> {
                try {
                    //* 在 semaphore.acquire() 和 semaphore.release()之间的代码，
                    // 同一时刻只允许制定个数的线程进入，
                    //log.info("semaphore.acquire:{}",count.get());
                    semaphore.acquire();
                    add();
                    log.info("semaphore.running:{}",count.get());
                    semaphore.release();
                   // log.info("semaphore.release:{}",count.get());
                } catch (Exception e) {
                   log.error("exception",e);
                }
                countDownLatch.countDown();
            });

        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}",count.get());

    }
    public static void add(){
        count.incrementAndGet();//先自增再获取值
        // count.getAndIncrement();//先获取值再自增
    }
}
