package com.aexonic.hnrblock;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Parikshit Patil on 1/21/2016.
 */
public class SimpleCryptoNew
{
    public static String encrypt(String seed, String cleartext) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
    }

    public static String decrypt(String seed, String encrypted) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        try
        {
        /*KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;*/

            int keyLength = 128;
            byte[] keyBytes = new byte[keyLength / 8];
            // explicitly fill with zeros
            Arrays.fill(keyBytes, (byte) 0x0);

            // if password is shorter then key length, it will be zero-padded
            // to key length
            byte[] passwordBytes = seed;
            int length = passwordBytes.length < keyBytes.length ? passwordBytes.length
                    : keyBytes.length;
            System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
            return keyBytes;
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }
    }


    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        System.out.println("no");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        System.out.println("Myerror");
        byte[] decrypted = cipher.doFinal(encrypted);
        System.out.println("Myerffff");
        return decrypted;
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }
    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        System.out.println("yes");
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }
    private final static String HEX = "0123456789ABCDEF";
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }

    public static void main(String args[])
    {
        try
        {
            System.out.println(SimpleCrypto.encrypt("hnrblock123@612#","sumit"));
            System.out.println(SimpleCrypto.fromHex(SimpleCrypto.toHex(SimpleCrypto.decrypt("hnrblock123@612#","01D8C2AB5560DE2F201B1E2D3F96EBB6"))));
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
