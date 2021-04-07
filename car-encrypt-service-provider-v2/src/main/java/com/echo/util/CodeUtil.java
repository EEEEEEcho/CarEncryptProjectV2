package com.echo.util;

import com.echo.encrypt.gm.sm4.SM4;

import java.util.Base64;

public class CodeUtil {

    public static String encodeToString(byte[] byteArray) {
        return Base64.getUrlEncoder().encodeToString(byteArray);
    }

    public static byte[] decodeStringToByte(String str){
        return Base64.getUrlDecoder().decode(str);
    }
}
