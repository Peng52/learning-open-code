package org.apache.rocketmq.test.peng;

import org.apache.rocketmq.common.BoundaryType;
import org.apache.rocketmq.common.utils.NetworkUtil;
import org.junit.Test;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;

/**
 * @author : pengcheng
 * @since : 2025-04-02
 */
public class _04_02_Main_Test {

    /**
     *
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
    }

    /**
     *
     */
    @Test
    public void testsfsd() {
        String STRING_CANONICAL_NAME = String.class.getCanonicalName();
        System.out.println(STRING_CANONICAL_NAME);

        String DOUBLE_CANONICAL_NAME_1 = Double.class.getCanonicalName();
        System.out.println(DOUBLE_CANONICAL_NAME_1);

        String DOUBLE_CANONICAL_NAME_2 = double.class.getCanonicalName();
        System.out.println(DOUBLE_CANONICAL_NAME_2);

        String INTEGER_CANONICAL_NAME_1 = Integer.class.getCanonicalName();
        System.out.println(INTEGER_CANONICAL_NAME_1);

        String INTEGER_CANONICAL_NAME_2 = int.class.getCanonicalName();
        System.out.println(INTEGER_CANONICAL_NAME_2);

        String LONG_CANONICAL_NAME_1 = Long.class.getCanonicalName();
        System.out.println(LONG_CANONICAL_NAME_1);

        String LONG_CANONICAL_NAME_2 = long.class.getCanonicalName();
        System.out.println(LONG_CANONICAL_NAME_2);

        String BOOLEAN_CANONICAL_NAME_1 = Boolean.class.getCanonicalName();
        System.out.println(BOOLEAN_CANONICAL_NAME_1);

        String BOOLEAN_CANONICAL_NAME_2 = boolean.class.getCanonicalName();
        System.out.println(BOOLEAN_CANONICAL_NAME_2);

        String BOUNDARY_TYPE_CANONICAL_NAME = BoundaryType.class.getCanonicalName();
        System.out.println(BOUNDARY_TYPE_CANONICAL_NAME);
        System.out.println(DOUBLE_CANONICAL_NAME_1);

    }

    /**
     * NetworkUtil 工具类使用
     */
    @Test
    public void testNetworkUtil() throws SocketException {
        // 获取本地地址，优先取公网地址
        String localAddress = NetworkUtil.getLocalAddress();
        System.out.println(localAddress);

        // 获取了所有本地网卡地址
        List<InetAddress> localInetAddressList = NetworkUtil.getLocalInetAddressList();
        System.out.println(localInetAddressList);
    }




}
