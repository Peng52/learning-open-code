package org.apache.rocketmq.test.peng;

import org.junit.Test;

import java.nio.ByteBuffer;

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
}
