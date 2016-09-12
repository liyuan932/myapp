package com.mycompany.myapp.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * 字符串 DESede(3DES) 加密
 * ECB模式/使用PKCS7方式填充不足位,目前给的密钥是192位 
 * 3DES（即Triple DES）是DES向AES过渡的加密算法（1999年，NIST将3-DES指定为过渡的 
 * 加密标准），是DES的一个更安全的变形。它以DES为基本模块，通过组合分组方法设计出分组加 
 * 密算法，其具体实现如下：设Ek()和Dk()代表DES算法的加密和解密过程，K代表DES算法使用的 
 * 密钥，P代表明文，C代表密表，这样， 
 * 3DES加密过程为：C=Ek3(Dk2(Ek1(P))) 
 * 3DES解密过程为：P=Dk1((EK2(Dk3(C))) 
 * */
public class DES3Util {

    private static final Logger log = LoggerFactory.getLogger(DES3Util.class);

    private static final String Algorithm = "DESede"; // 定义 加密算法,可用 DES,DESede,Blowfish

    private static final byte[] keyBytes =
        {0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD, 0x55,
            0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2}; // 24字节的密钥

    private static final String ECB = "DESede/ECB/PKCS5Padding";

    /**
     * 加密
     *
     * @param str 被加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String encrypt(String str){
        try {
            return Hex.encodeHexString(initCipher(Cipher.ENCRYPT_MODE).doFinal(str.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("encrypt error",e);
            return null;
        }
    }

    /**
     * 解密
     *
     * @param str 加密后的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decrypt(String str) throws Exception {

        try {
            return new String(initCipher(Cipher.DECRYPT_MODE).doFinal(Hex.decodeHex(str.toCharArray())));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("encrypt error",e);
            return null;
        }
    }

    /**
     * 初始化Cipher对象
     *
     * @param mode 加密解密模式
     * @return
     */
    private static Cipher initCipher(int mode) {
        try {
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            SecretKey desKey = new SecretKeySpec(keyBytes, Algorithm);
            Cipher tcipher = Cipher.getInstance(ECB);
            tcipher.init(mode, desKey);
            return tcipher;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("init cipher error",e);
        }
        return null;
    }

    /**
     * 生成密钥
     *
     * @param username
     * @return
     */
    public static byte[] hex(String username) {
        String key = "test";// 关键字
        String f = DigestUtils.md5Hex(username + key);
        byte[] bkeys = new String(f).getBytes();
        byte[] enk = new byte[24];
        for (int i = 0; i < 24; i++) {
            enk[i] = bkeys[i];
        }
        return enk;
    }

    public static void main(String[] args) throws Exception {
        String str = "中国ABCabc123";
        String strEncrypt = encrypt(str);
        System.out.println("加密后的字符串：" + strEncrypt);
        System.out.println("解密后的字符串：" + decrypt(strEncrypt));
        System.out.println(hex(str));
    }
}
