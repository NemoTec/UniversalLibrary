package com.nemo.ul.java;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ULMD5Util {
    private static final String TAG = "ULMD5Util";
    private static final char[] HEX_DIGITS = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };


    public ULMD5Util() {

    }

    //String MD5
    public static String getStringMD5(String inputStr) {
        return getByteArrayMD5(inputStr.getBytes());
    }

    //File MD5  http://blog.csdn.net/xiao__gui/article/details/8148203
    public static String getFileMD5(String inputStr) {
        return getByteArrayMD5(inputStr.getBytes());
    }

    //byte[] MD5
    public static String getByteArrayMD5(byte[] inputByteArray) {
        try {
            //MD5 or SHA1;
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return _byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {  
            return null;  
        }
    }

    private static String _byteArrayToHex(byte[] byteArray) {
        char[] resultCharArray =new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {  
            resultCharArray[index++] = HEX_DIGITS[(b >>> 4) & 0xf];
            resultCharArray[index++] = HEX_DIGITS[b & 0xf];
        }
        return new String(resultCharArray);
    }

}