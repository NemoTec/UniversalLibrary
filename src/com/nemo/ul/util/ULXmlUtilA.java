/*********************************************************************************
 ** Copyright (C), 2010-2016, Nemo
 ** All rights reserved.
 **
 ** File: - ULXmlUtilA.java
 ** Description:
 **     XML parser and writer usage in Android.
 **
 ** Android relationship:
 ** android-7.0.0_r1\libcore\xml\src\main\java\org\xmlpull\v1
 **         XmlPullParser.java
 **         XmlPullParserException.java
 **         XmlPullParserFactory.java
 **         XmlSerializer.java
 ** android-7.0.0_r1\libcore\xml\src\main\java\org\kxml2\io
 **         KXmlParser.java
 **         KXmlSerializer.java
 ** android-7.0.0_r1\frameworks\base\core\java\android\\util
 **         Xml.java
 **         XmlPullAttributes.java
 ** android-7.0.0_r1\frameworks\base\core\java\com\android\internal\\util
 **         XmlUtils.java
 **         FastXmlSerializer.java
 ** android-7.0.0_r1\frameworks\base\core\java\android\content\res
 **         XmlResourceParser.java
 **         XmlBlock.java
 **
 ** Version: 1.0
 ** Date: 2016-02-18
 ** Author: Nemo
 **
 ** ------------------------------- Revision History: ----------------------------
 ** <author>                <date>       <version>   <desc>
 ** ------------------------------------------------------------------------------
 ** NemoTec@163.com       2016-02-18     1.0         Create this moudle.
 ********************************************************************************/

package com.nemo.ul.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;

import android.content.Context;
import android.text.TextUtils;
import android.util.Xml;
import android.content.res.XmlResourceParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.android.internal.util.XmlUtils;


public class ULXmlUtilA {
    private static final String TAG = "ULXmlUtilA";


    /**
     * write XML use XmlUtils.
     */
    public static boolean writeXMLSample(String path) {
        ULLog.d(TAG, "writeXMLSample() path #" + path);
        if (!verifyFileExist(path)) {
            return false;
        }

        File file = new File(path);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            
            /*
            (1)======XmlUtils.writeMapXml(Map val, XmlSerializer out);======
            <?xml version='1.0' encoding='utf-8' standalone='yes' ?>
            <map>
                <string name="z5">ZZZZZ</string>
                <string name="x3">XXX</string>
                <string name="y4">YYYY</string>
            </map>
            
            (2)======XmlUtils.writeMapXml(Map val, String name, XmlSerializer out);======
            <?xml version='1.0' encoding='UTF-8' standalone='yes' ?>
            <map name="mapAttr">
                <int name="33" value="456" />
                <int name="44" value="789" />
                <int name="22" value="123" />
            </map>
            
            Map<String, Integer>
            Map<String, Float>
            Map<String, Double>
            Map<String, Boolean>
            */
            Map<String, String> map = new HashMap<String, String>();
            map.put("x3", "XXX");
            map.put("y4", "YYYY");
            map.put("z5", "ZZZZZ");
            XmlUtils.writeMapXml(map, outputStream);
            
            Map<String, Integer> map2 = new HashMap<String, Integer>();
            map2.put("22", 123);
            map2.put("33", 456);
            map2.put("44", 789);
            /*XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
            serializer.startDocument(null, true);
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            XmlUtils.writeMapXml(map2, "mapAttr", serializer);
            serializer.endDocument();*/
            
            /*
            (3)======XmlUtils.writeListXml(List val, OutputStream out);======
            <?xml version='1.0' encoding='utf-8' standalone='yes' ?>
            <list>
                <string>ZZZZZ</string>
                <string>XXX</string>
                <string>YYYY</string>
            </list>
            
            (4)======XmlUtils.writeMapXml(Map val, String name, XmlSerializer out);======
            <?xml version='1.0' encoding='UTF-8' standalone='yes' ?>
            <list name="listAttr">
                <int value="456" />
                <int value="789" />
                <int value="123" />
            </list>
            
            (5)======writeSetXml(Set val, String name, XmlSerializer out)======
            <?xml version='1.0' encoding='UTF-8' standalone='yes' ?>
            <set name="setAttr">
                <string>ZZZZZ</string>
                <string>XXX</string>
                <string>YYYY</string>
            </set>

            <?xml version='1.0' encoding='UTF-8' standalone='yes' ?>
            <set name="setAttr">
                <int value="456" />
                <int value="789" />
                <int value="123" />
            </set>
            
            (6)======HashMap<String, ?> readMapXml(InputStream in)======
            (7)======ArrayList readListXml(InputStream in)======
            (8)======HashSet readSetXml(InputStream in)======
            */

            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }

            return true;
        } catch (Exception e) {
            ULLog.e(TAG, "Failed to write status: " + e);
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void parserStringXmlValue(Context context, String xmlValue) {
        if (TextUtils.isEmpty(xmlValue)) {
            return;
        }

        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new StringReader(xmlValue));

            int type;
            do {
                type = parser.next();
                if (type == XmlPullParser.START_TAG) {
                    String tag = parser.getName();
                    if ("p".equals(tag)) {
                        
                    } else if ("qx".equals(tag)) {
                        
                    }
                }
            } while (type != XmlPullParser.END_DOCUMENT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * read.
     * <entity att1="x" att2="yy">ABCD</entity>
     * <list att="zzzzz" />
     */
    public static ArrayList<String> readXMLValue(String path) {
        ULLog.d(TAG, "readXMLValue() path #" + path);
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        File file = new File(path);
        if (!file.exists()) {
            ULLog.w(TAG, "readXMLValue() path #" + path + " not exist.");
            return null;
        }

        ArrayList<String> resultList = new ArrayList<String>();
        FileInputStream inputStream = null;
        try {
            //inputStream = context.getResources().getAssets().open(xmlPath);
            inputStream = new FileInputStream(file);
            //1. XmlResourceParser parser = context.getResources().getXml(R.xml.local_default_config);
            //2. XmlPullParser parser = Xml.newPullParser();
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(inputStream, null);

            int type;
            do {
                type = parser.next();
                if (type == XmlPullParser.START_TAG) {
                    String tag = parser.getName();
                    if ("entity".equals(tag)) {
                        String att1 = parser.getAttributeValue(null, "att1");
                        String att2 = parser.getAttributeValue(null, "att2");
                        String text = parser.nextText();
                        if (!TextUtils.isEmpty(text)) {
                            ULLog.d(TAG, "resultList add - " + text + ", " + att1 + ", " + att2);
                            resultList.add(text);
                        }
                    } else if ("list".equals(tag)) {

                    }
                }
            } while (type != XmlPullParser.END_DOCUMENT);

            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultList;
    }

    /**
     * write.
     * <?xml version='1.0' encoding='utf-8' standalone='yes' ?>
     * <marketlist>
     *     <market packagename="xx.xx">ABCD</market>
     *     <market packagename="xx.yy">EFGH</market>
     * </marketlist>
     */
    public static boolean writeValueToXML(String path, Map<String, String> marketMap) {
        ULLog.d(TAG, "writeValueToXML() path #" + path);
        if (!verifyFileExist(path)) {
            return false;
        }

        File file = new File(path);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            //XmlSerializer out = Xml.newSerializer();
            XmlSerializer out = XmlPullParserFactory.newInstance().newSerializer();
            out.setOutput(outputStream, "utf-8");
            out.startDocument(null, true);
            out.text("\r\n");
            out.startTag(null, "marketlist");

            for (Map.Entry<String, String> entry : marketMap.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();

                out.text("\r\n");
                out.text("\t");
                out.startTag(null, "market");
                out.attribute(null, "packagename", name);
                out.text(value);
                out.endTag(null, "market");
            }

            out.text("\r\n");
            out.endTag(null, "marketlist");
            out.text("\r\n");
            out.endDocument();

            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }

            return true;
        } catch (Exception e) {
            ULLog.e(TAG, "Failed to write status: " + e);
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static boolean verifyFileExist(String path) {
        if (TextUtils.isEmpty(path)) {
            ULLog.e(TAG, "verifyFileExist() path empty.");
            return false;
        }

        File file = new File(path);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                boolean ok = file.getParentFile().mkdirs();
                if (!ok) {
                    ULLog.e(TAG, "mkdirs() error");
                    return false;
                }
            }

            try {
                boolean ok = file.createNewFile();
                if (!ok) {
                    ULLog.e(TAG, "createNewFile() error");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return true;
    }

}