package org.apache.rocketmq.test.peng;

import org.apache.commons.lang3.ThreadUtils;
import org.apache.rocketmq.common.message.MessageClientIDSetter;
import org.junit.Test;

import java.time.Duration;

/**
 * @author : pengcheng
 * @since : 2025-06-12
 */
public class _06_12_Test {


    /**
     * Thread 设置
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

    /**
     *
     */
    @Test
    public void testsdfs() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            ThreadUtils.sleep(Duration.ofSeconds(1));
            String uniqID = MessageClientIDSetter.createUniqID();
            System.out.println(uniqID);
        }
    }


    /**
     * 右移：有符号右移 、无符号号右移
     */
    @Test
    public void test() {
        int a = 0b01111111_11111111_11111111_11111100;
        int a1212312 = (byte) a;
        int i111 = (a >> 2);
        int i222 = (a >>> 2);

        System.out.println(a);
        System.out.println(i111);
        System.out.println(i222);
        System.out.println("");

        byte a1 = (byte) 0b1111_1100;
        byte i2221111 = (byte) (a >> 2);
        byte i22222222 = (byte) (a >>> 2);
        System.out.println();
    }


    /**
     * 测试
     */
    @Test
    public void testMathabs() {
        int minValue = Integer.MIN_VALUE;
        long minValue2 = -(Integer.MIN_VALUE);
        int abs = Math.abs(minValue);
        System.out.println(abs);

        /***********/
        int value2 = -1;
        int value22 = -value2;
        System.out.println();

        /************/
        int i = Integer.MAX_VALUE + 1;
        System.out.println(i);
    }

}
