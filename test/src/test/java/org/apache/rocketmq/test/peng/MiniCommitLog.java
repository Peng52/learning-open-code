package org.apache.rocketmq.test.peng;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicLong;

public class MiniCommitLog {
    // 存储目录
    private final String storePath;
    // 单个文件大小
    private final int mappedFileSize;
    // 当前写入的MappedFile
    private MappedFile mappedFile;
    // 写入位置指针
    private final AtomicLong wrotePosition = new AtomicLong(0);
    
    public MiniCommitLog(String storePath, int mappedFileSize) {
        this.storePath = storePath;
        this.mappedFileSize = mappedFileSize;
        init();
    }
    
    private void init() {
        File dir = new File(storePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 创建或加载最后一个MappedFile
        this.mappedFile = createMappedFile();
    }
    
    private MappedFile createMappedFile() {
        String fileName = storePath + File.separator + System.currentTimeMillis();
        return new MappedFile(fileName, mappedFileSize);
    }
    
    // 写入消息
    public boolean putMessage(byte[] message) {
        // 1. 检查当前文件是否有足够空间
        if (!mappedFile.isAvailable(message.length)) {
            // 创建新文件
            mappedFile = createMappedFile();
        }
        
        // 2. 写入消息
        boolean result = mappedFile.appendMessage(message, wrotePosition);
        if (result) {
            wrotePosition.addAndGet(message.length);
        }
        return result;
    }
    
    // 关闭资源
    public void shutdown() {
        if (mappedFile != null) {
            mappedFile.shutdown();
        }
    }
    
    // MappedFile封装类
    static class MappedFile {
        private final String fileName;
        private final int fileSize;
        private FileChannel fileChannel;
        private MappedByteBuffer mappedByteBuffer;
        private RandomAccessFile randomAccessFile;
        
        public MappedFile(String fileName, int fileSize) {
            this.fileName = fileName;
            this.fileSize = fileSize;
            init();
        }
        
        private void init() {
            try {
                File file = new File(fileName);
                this.randomAccessFile = new RandomAccessFile(file, "rw");
                this.fileChannel = randomAccessFile.getChannel();
                this.mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public boolean isAvailable(int length) {
            return mappedByteBuffer.position() + length <= fileSize;
        }
        
        public boolean appendMessage(byte[] message, AtomicLong wrotePosition) {
            try {
                // 获取当前写入位置
                int currentPos = mappedByteBuffer.position();
                
                // 写入消息长度(简化版，实际RocketMQ有更复杂的协议头)
                mappedByteBuffer.putInt(message.length);
                // 写入消息内容
                mappedByteBuffer.put(message);
                
                // 强制刷盘(简化版，实际有同步/异步刷盘策略)
                mappedByteBuffer.force();
                
                // 更新写入位置
                wrotePosition.set(currentPos + message.length + 4);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        
        public void shutdown() {
            try {
                if (mappedByteBuffer != null) {
                    mappedByteBuffer.force();
                    mappedByteBuffer = null;
                }
                if (fileChannel != null) {
                    fileChannel.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // 测试
    public static void main(String[] args) {
        MiniCommitLog commitLog = new MiniCommitLog("/tmp/mini-commitlog", 1024 * 1024); // 1MB文件
        
        // 模拟写入10条消息
        for (int i = 0; i < 10; i++) {
            String msg = "Hello RocketMQ " + i;
            commitLog.putMessage(msg.getBytes());
            System.out.println("Write message: " + msg);
        }
        
        commitLog.shutdown();
    }
}