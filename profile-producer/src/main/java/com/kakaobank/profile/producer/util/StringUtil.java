package com.kakaobank.profile.producer.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;

public class StringUtil {
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static boolean isEmpty(String value) {
        return value == null || value == "";
    }

    public static String randomString( int len, boolean withoutNumbers ){
        StringBuilder sb = new StringBuilder( len );
        for(int i = 0; i < len; i++ )
            sb.append( AB.charAt(getCharIndex(withoutNumbers)) );

        return sb.toString();
    }

    private static int getCharIndex(boolean withoutNumbers) {
        if(withoutNumbers)
            return ThreadLocalRandom.current().nextInt(10, AB.length());

        return ThreadLocalRandom.current().nextInt(AB.length());
    }

    public static String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
