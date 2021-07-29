package com.nga.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5_Shiro {
    /**
     * 随机生成salt需要指定它的字符串的长度
     *
     * @param len 字符串的长度
     * @return salt
     */
    public static String generateSalt(int len) {
        // 一个byte占两字节
        int byteLen = len >> 1;
        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
        return generator.nextBytes(byteLen).toHex();
    }

    /**
     * 获得加密后的密码，使用默认hash迭代的次数1次
     *
     * @param hashAlgorithm hash算法的名称 MD2 MD5 SHA-1 SHA-256 SHA-384 SHA-512 ect
     * @param password      需要加密的密码
     * @param salt          盐
     * @return 加密后的密码
     */
    public static String encryptPassword(String hashAlgorithm, String password, String salt) {
        return encryptPassword(hashAlgorithm, password, salt, 1);
    }

    /**
     * 获得加密后的密码，使用默认hash迭代的次数1次
     *
     * @param hashAlgorithm hash算法的名称 MD2 MD5 SHA-1 SHA-256 SHA-384 SHA-512 ect
     * @param password      需要加密的密码
     * @param salt          盐
     * @return 加密后的密码
     */
    public static String encryptPassword(String hashAlgorithm, String password, String salt, int hashIterations) {
        SimpleHash simpleHash = new SimpleHash(hashAlgorithm, password, salt, hashIterations);

        return simpleHash.toString();
    }
}
