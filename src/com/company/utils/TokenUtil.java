package com.company.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @Author Dave
 * @Date 2020-09-24 14:43
 * @Description token验证，安全性
 */
public class TokenUtil {
    private final static String AES_KEY = "1234567890abcdef";

    public static String generateToken() {
        String content = Convert.toStr(System.currentTimeMillis());
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, AES_KEY.getBytes());
        String encryptHex = aes.encryptHex(content);
        System.out.println("AES encoder content: {}"+encryptHex);
        return encryptHex;
    }

    public static String generateTokenT(Long timestamp) {
        String content = Convert.toStr(timestamp);
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, AES_KEY.getBytes());
        String encryptHex = aes.encryptHex(content);
        System.out.println("AES encoder content: {}"+encryptHex);
        return encryptHex;
    }

    public static Boolean checkToken(String token) {
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, AES_KEY.getBytes());
        try {
            String decryptStr = aes.decryptStr(token, CharsetUtil.CHARSET_UTF_8);
            Instant inst1 = Instant.ofEpochMilli(Long.parseLong(decryptStr));
            Instant inst2 = Instant.now();
            long diffAsMinutes = ChronoUnit.MINUTES.between(inst2, inst1);
            return Math.abs(diffAsMinutes) < 1000;
        }catch (Exception e){

        }
        return false;
        //System.out.println("AES decode content: {}"+decryptStr);

        //System.out.println(inst2);

        //System.out.println("time between is: {}"+diffAsMinutes);

    }

    public static void main(String[] args) {
        String token = generateToken();
        //String token = generateTokenT(1601023489987L);
        Boolean check = checkToken(token);
        System.out.println(check);
    }
}
