package com.nemo.ul.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import com.nemo.ul.util.ULLog;


public class ULMD5Util {
    private static final String TAG = "ULMD5Util";


    public ULMD5Util() {

    }

    //byte[] MD5
    public static String getByteArrayMD5(byte[] inputByteArray) {
        try {
            //MD5 or SHA1;
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return _bytes2Hex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {  
            return null;  
        }
    }

    //String MD5
    public static String getStringMD5(String inputStr) {
        return getByteArrayMD5(inputStr.getBytes());
    }

    //File MD5
    public static String getFileMd5(String filePath) {
        File f = new File(filePath);
        if (f == null || !f.exists()) {
            //ULLog.w(TAG, "!f.exists()");
            return null;
        }

        InputStream is = null;
        final byte[] buffer = new byte[1024];
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e1) {
            return null;
        }

        try {
            is = new FileInputStream(f);
            int readed = 0;
            while ((readed = is.read(buffer)) > 0) {
                byte[] tmp = new byte[readed];
                System.arraycopy(buffer, 0, tmp, 0, readed);
                md5.update(tmp);
            }
            return _bytes2Hex(md5.digest());
        } catch (FileNotFoundException e) {
            //ULLog.e(TAG, "" + e);
        } catch (IOException e) {
            //ULLog.e(TAG, "" + e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {

                }
            }
        }
        return null;
    }

    private static String _bytes2Hex(byte[] b) {
        StringBuilder builder = new StringBuilder();
        String stmp = "";

        for (int i = 0; i < b.length; i++) {
            stmp = Integer.toHexString(b[i] & 0xFF);
            if (stmp.length() == 1) {
                builder.append("0" + stmp);
            } else {
                builder.append(stmp);
            }
        }

        return builder.toString().toUpperCase(Locale.getDefault());
    }


    //File MD5  http://blog.csdn.net/xiao__gui/article/details/8148203
    public static String getFileMD5(String inputStr) {
        return getByteArrayMD5(inputStr.getBytes());
    }

}