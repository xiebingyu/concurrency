package com.ma.concurrency.atomic;
import com.ma.concurrency.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Administrator
 */
@Slf4j
@ThreadSafe
public class AtomicExample5 {
    private  static AtomicIntegerFieldUpdater<AtomicExample5> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class,"count");
    @Getter
    private volatile int count = 100;

    public static void main(String[] args) {
        AtomicExample5 example5  = new AtomicExample5();
        if(updater.compareAndSet(example5,100,120)){
            log.info("update sucess,{}",example5.getCount());
        }else{
            log.info("update failed,{}",example5.getCount());

        }
    }
}
