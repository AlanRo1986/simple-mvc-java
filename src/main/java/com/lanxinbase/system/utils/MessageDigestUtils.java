package com.lanxinbase.system.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by alan.luo on 2017/7/27.
 * <p>
 * out(MessageDigestUtils.md5("123"));
 * out(MessageDigestUtils.sha1("123"));
 * <p>
 * out(MessageDigestUtils.Base64.encode("alan"));
 * out(MessageDigestUtils.Base64.encodeSafe("alan"));
 * out(MessageDigestUtils.Base64.decode("YWxhbg=="));
 * out(MessageDigestUtils.Base64.decodeSafe("YWxhbg=="));
 * <p>
 * out(MessageDigestUtils.Base64.encode("40bd001563085fc35165329ea1ff5c5ecbdbbeef"));
 * out(MessageDigestUtils.Base64.encodeSafe("40bd001563085fc35165329ea1ff5c5ecbdbbeef"));
 * out(MessageDigestUtils.Base64.decode("NDBiZDAwMTU2MzA4NWZjMzUxNjUzMjllYTFmZjVjNWVjYmRiYmVlZg=="));
 * out(MessageDigestUtils.Base64.decodeSafe("NDBiZDAwMTU2MzA4NWZjMzUxNjUzMjllYTFmZjVjNWVjYmRiYmVlZg=="));
 */
public class MessageDigestUtils {

    private static class JdkBase64Delegate {

        public byte[] encode(byte[] src) {
            return src != null && src.length != 0 ? java.util.Base64.getEncoder().encode(src) : src;
        }

        public byte[] decode(byte[] src) {
            return src != null && src.length != 0 ? java.util.Base64.getDecoder().decode(src) : src;
        }

        public byte[] encodeUrlSafe(byte[] src) {
            return src != null && src.length != 0 ? java.util.Base64.getUrlEncoder().encode(src) : src;
        }

        public byte[] decodeUrlSafe(byte[] src) {
            return src != null && src.length != 0 ? java.util.Base64.getUrlDecoder().decode(src) : src;
        }
    }

    public static class Base64 {
        private static final JdkBase64Delegate delegate = new JdkBase64Delegate();

        public static String encode(String src) {
            return new String(delegate.encode(src.getBytes()));
        }

        public static String decode(String src) {
            return new String(delegate.decode(src.getBytes()));
        }

        public static String encodeSafe(String src) {
            return new String(delegate.encodeUrlSafe(src.getBytes()));
        }

        public static String decodeSafe(String src) {
            return new String(delegate.decodeUrlSafe(src.getBytes()));
        }
    }

    public static String md5(String input) {
        StringBuffer result = new StringBuffer();

        try {
            byte[] hash = getMessageDigest(null).digest(input.getBytes());
            result = parseBytes(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result.length() == 0 ? null : result.toString();
    }

    public static String sha1(String input) {
        StringBuffer result = new StringBuffer();

        try {
            byte[] hash = getMessageDigest("SHA1").digest(input.getBytes());
            result = parseBytes(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result.length() == 0 ? null : result.toString();
    }

    private static StringBuffer parseBytes(byte[] hash) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            int v = hash[i] & 0xFF;
            if (v < 0x10) {
                result.append("0");
            }
            result.append(Integer.toHexString(hash[i] & 0xFF));
        }
        return result;
    }

    private static MessageDigest getMessageDigest(String algName) throws NoSuchAlgorithmException {
        if (algName == null) {
            algName = "MD5";
        }
        return MessageDigest.getInstance(algName);
    }



}
