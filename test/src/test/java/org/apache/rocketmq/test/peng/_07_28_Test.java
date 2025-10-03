package org.apache.rocketmq.test.peng;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author : pengcheng
 * @since : 2025-07-28
 */
public class _07_28_Test {

    /**
     *
     */
    @Test
    public void testSlice() {
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putShort((short) 1);
        allocate.position(0);
        allocate.position(1);
        allocate.position(2);
        allocate.position(3);
        allocate.position(4);

        ByteBuffer byteBufferSlice = allocate.slice();

        int position = byteBufferSlice.position();
        int limit = byteBufferSlice.limit();
        int capacity = byteBufferSlice.capacity();
        byteBufferSlice.position(0);
        byteBufferSlice.position(1);
        byteBufferSlice.position(2);
        byteBufferSlice.position(3);

        System.out.println("");

    }


    /**
     * 10-03
     */
    @Test
    public void test1003() {
        InetSocketAddress socketAddress2 = new InetSocketAddress("255.255.255.255", 8080);
        byte[] address = socketAddress2.getAddress().getAddress();

        long a = 255;
        System.out.println(a);
    }

    /**
     * TreeMap
     */
    @Test
    public void testTreeMap() {
        TreeMap<Integer, String> msgTreeMap = new TreeMap<>();
        msgTreeMap.put(1, "111");
        msgTreeMap.put(2, "111");
        msgTreeMap.put(3, "111");

        Map.Entry<Integer, String> integerStringEntry = msgTreeMap.firstEntry();

        Map.Entry<Integer, String> integerStringEntry1 = msgTreeMap.lastEntry();

        System.out.println(integerStringEntry);
    }
}
