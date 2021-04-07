package com.echo.util;

import com.echo.encrypt.gm.sm4.SM4;

import java.util.Base64;

public class EncryptUtil {
    public static byte[] encryptMessageToByte(byte[] bytes, byte[] key) {
        try {
            return SM4.ecbCrypt(true, key, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encryptMessageToByte(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(str);
            return SM4.ecbCrypt(true, key, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decryptMessageToByte(byte[] bytes, byte[] key) {
        try {
            return SM4.ecbCrypt(false, key, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decryptMessageToByte(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(str);
            return SM4.ecbCrypt(false, key, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encryptMessageToString(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(str);
            byte[] ecbCrypt = SM4.ecbCrypt(true, key, bytes, 0, bytes.length);
            return Base64.getUrlEncoder().encodeToString(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encryptMessageToString(byte[] bytes, byte[] key) {
        try {
            byte[] ecbCrypt = SM4.ecbCrypt(true, key, bytes, 0, bytes.length);
            return Base64.getUrlEncoder().encodeToString(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptMessageToString(byte[] bytes, byte[] key) {
        try {
            byte[] ecbCrypt = SM4.ecbCrypt(false, key, bytes, 0, bytes.length);
            return Base64.getUrlEncoder().encodeToString(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptMessageToString(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(str);
            byte[] ecbCrypt = SM4.ecbCrypt(false, key, bytes, 0, bytes.length);
            return Base64.getUrlEncoder().encodeToString(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptMessageToNoneUrlString(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getDecoder().decode(str);
            byte[] ecbCrypt = SM4.ecbCrypt(false, key, bytes, 0, bytes.length);
            return Base64.getEncoder().encodeToString(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String encryptMessageToNoneUrlString(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getDecoder().decode(str);
            byte[] ecbCrypt = SM4.ecbCrypt(true, key, bytes, 0, bytes.length);
            return Base64.getEncoder().encodeToString(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
