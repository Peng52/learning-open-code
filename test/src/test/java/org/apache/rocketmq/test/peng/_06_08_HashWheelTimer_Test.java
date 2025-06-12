package org.apache.rocketmq.test.peng;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.apache.commons.lang3.ThreadUtils;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 测试
 *
 * @author : pengcheng
 * @since : 2025-06-08
 */
public class _06_08_HashWheelTimer_Test {


    /**
     * HashWheelTimer
     */
    @Test
    public void testHashWheelTimer() throws InterruptedException {
        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(5, TimeUnit.SECONDS);
        TimerTask time1 = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println(System.currentTimeMillis() + "   111111111   " + Thread.currentThread().getName());
                hashedWheelTimer.newTimeout(this, 5, TimeUnit.SECONDS);
            }

        };

        TimerTask time2 = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println(System.currentTimeMillis() + " 2222222222 " + Thread.currentThread().getName());
                hashedWheelTimer.newTimeout(this, 2, TimeUnit.SECONDS);
            }

        };

        hashedWheelTimer.newTimeout(time1, 2, TimeUnit.SECONDS);
        hashedWheelTimer.newTimeout(time2, 5, TimeUnit.SECONDS);
        hashedWheelTimer.start();

        ThreadUtils.sleep(Duration.ofSeconds(100000));
        System.out.println();

        ThreadFactory threadFactory = Executors.defaultThreadFactory();

    }


}
