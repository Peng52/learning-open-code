package org.apache.rocketmq.test.peng;

import org.apache.commons.lang3.ThreadUtils;
import org.apache.rocketmq.common.message.MessageClientIDSetter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

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


    /**
     * File 文件操作
     */
    @Test
    public void testFile() throws IOException {
        File tempFile = File.createTempFile("99999999", "99999999");
        System.out.println("临时文件路径: " + tempFile.getAbsolutePath());
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile, "rw")) {
            String aaa = "testtesttesttesttesttesttesttest";
            byte[] bytes = aaa.getBytes(StandardCharsets.UTF_8);
            randomAccessFile.write(bytes);
        }
        Map<String, String> getenv = System.getenv();
        System.out.println(getenv);
        // 反复的挂载删除文件请求，可能导致内存泄漏。
        // 所有资源释放，这个方法才有用，否则删不掉。
        tempFile.deleteOnExit();
        //boolean delete = tempFile.delete();
        //System.out.println(delete);
    }


    /**
     * RandomAccessFile
     */
    @Test
    public void testRandomAccessFile() throws IOException {

        File file = new File("G:\\learn-proj\\rocketmq\\test\\src\\test\\java\\org\\apache\\rocketmq\\test\\peng\\temp.txt");
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
            String aaa = "test";
            byte[] bytes = aaa.getBytes(StandardCharsets.UTF_8);
            randomAccessFile.write(bytes);
            // 任意位置写入
            randomAccessFile.seek(7);
            byte[] t = "y".getBytes(StandardCharsets.UTF_8);
            randomAccessFile.write(t);
            randomAccessFile.seek(20);
            long length = randomAccessFile.length();
            System.out.println("当前文件长度：" + length);

            /***************** 设置负值会报错
             * */
            randomAccessFile.seek(-100);
            byte[] t211 = "y".getBytes(StandardCharsets.UTF_8);
            randomAccessFile.write(t211);
            System.out.println("当前文件长度：" + randomAccessFile.length());
        }
    }


    /**
     * sdfs
     */
    @Test
    public void test22() throws IOException {
        File file = new File("G:\\learn-proj\\rocketmq\\test\\src\\test\\java\\org\\apache\\rocketmq\\test\\peng\\temp_new.txt");
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
            randomAccessFile.setLength(1024 * 1024);
            System.out.println(randomAccessFile.length());
        }
    }

    /**
     * FileChannel
     */
    @Test
    public void testFileChannel() throws Exception {
        String fileName = "G:\\learn-proj\\rocketmq\\test\\src\\test\\java\\org\\apache\\rocketmq\\test\\peng\\temp_1608.txt";
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.putChar('A');
        // 写模式切换成读模式 ; limit = position ; position = 0;
        byteBuffer.flip();
        int writeBytes = fileChannel.write(byteBuffer);
        System.out.println("写入磁盘数：" + writeBytes);
        // 强制刷盘
        fileChannel.force(true);
        fileChannel.close();
        randomAccessFile.close();
    }

    /**
     * ByteBuffer 使用
     */
    @Test
    public void testByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        printByteBuffer(byteBuffer);
        byteBuffer.putInt(1);
        printByteBuffer(byteBuffer);
        // flip 翻转 limit =  position;
        // 写模式转成读模式
        byteBuffer.flip();
        int anInt = byteBuffer.getInt();
        System.out.println(anInt);
        // rewind() 重置
        byteBuffer.rewind();
        int anInt1 = byteBuffer.getInt();
        System.out.println(anInt1);
        // clear() 清空

    }

    private void printByteBuffer(ByteBuffer byteBuffer) {
        int capacity = byteBuffer.capacity();
        int remaining = byteBuffer.remaining();
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        System.out.println(position + " " + remaining + " " + limit + " " + capacity);
        System.out.println("--------------------------------------------");
    }

}
