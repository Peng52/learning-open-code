package org.apache.rocketmq.test.peng;

import org.junit.Test;

/**
 * @author : pengcheng
 * @since : 2025-06-12
 */
public class _06_12_Test {


    /**
     * Thread
     */
    @Test
    public void testThread() {
        Thread thread = new Thread(() -> {
            int a = 1 / 0;
        });
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = (t, e) -> {
            System.out.println("11111111111111");
        };

        //thread.setUncaughtExceptionHandler(defaultUncaughtExceptionHandler);
        thread.start();

    }

}
