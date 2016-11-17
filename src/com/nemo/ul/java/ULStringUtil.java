package com.nemo.ul.java;



public class ULStringUtil {

    //1. CharSequence to String
    public static String convertCharSequence2String(CharSequence charSeq) {
        return charSeq.toString();
    }

    //2. String to CharSequence
    public static CharSequence convertString2CharSequence(String str) {
        return str;
    }

    //3. byte[] to String
    public static String convertByteArray2String(byte[] byteArray) {
        return new String(byteArray);
    }

    //4. String to byte[]
    public static byte[] convertString2ByteArray(String str) {
        return str.getBytes();
    }

    public static String deleteMessyCode(String name){
        if (null == name) {
            return "";
        }

        if (name.length() > 0) {
            char firstChar = name.charAt(0);
            if (((int)firstChar) == 160) {
                name = name.substring(1);
            }
        }

        if (name.length() > 0) {
            char endChar = name.charAt(name.length()-1);
            if (((int)endChar) == 160) {
                name = name.substring(0, (name.length()-1));
            }
        }
        return name;
    }

}



//Interger.parseInt代替Interger.valueOf.


//String to byte[]
//str.getBytes();

//byte[] to String
//new String(resultCharArray);