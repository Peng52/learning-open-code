package org.apache.rocketmq.test.peng;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Ipv4Converter {

    // IPv4字符串 → 32位整数
    public static long ipv4ToLong(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        return (Long.parseLong(octets[0]) << 24) |
               (Long.parseLong(octets[1]) << 16) |
               (Long.parseLong(octets[2]) << 8) |
               Long.parseLong(octets[3]);
    }

    // 32位整数 → IPv4字符串
    public static String longToIpv4(long ip) {
        return ((ip >> 24) & 0xFF) + "." +
               ((ip >> 16) & 0xFF) + "." +
               ((ip >> 8) & 0xFF) + "." +
               (ip & 0xFF);
    }

    // 32位整数 → 字节数组
    public static byte[] intToBytes(long ip) {
        return new byte[] {
            (byte) (ip >> 24),
            (byte) (ip >> 16),
            (byte) (ip >> 8),
            (byte) ip
        };
    }

    // 字节数组 → 32位整数
    public static long bytesToInt(byte[] bytes) {
        return ((bytes[0] & 0xFFL) << 24) |
               ((bytes[1] & 0xFFL) << 16) |
               ((bytes[2] & 0xFFL) << 8) |
               (bytes[3] & 0xFFL);
    }
}