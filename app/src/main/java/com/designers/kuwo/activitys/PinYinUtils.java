package com.designers.kuwo.activitys;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by PC-CWB on 2017/2/20.
 */
public class PinYinUtils {

    /*
    *  得到指定汉字的拼音
    *
    * */

    public static String getPinYin(String hanzi) {
        String pinyin = "";

        //下面是拼音的格式化
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);                   //转换后以全大写方式输出
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);               //转换之后以没有声调的形式输出

        //对单个汉字进行转换
        char[] arr = hanzi.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (Character.isWhitespace(arr[i]))
                continue;//如果是空格则不处理，进行下次遍历
            if (arr[i] > 127) {//汉字是2个字节大于127
                try {
                    String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(arr[i], format);
                    if (pinyinArr != null) {
                        pinyin += pinyinArr[0];
                    } else {
                        pinyin += pinyinArr[i];
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                    //不是正确的汉字
                    pinyin += arr[i];
                }
            } else {
                //不是汉字
                pinyin += arr[i];
            }
        }
        //汉字是2个字节存储，肯定大于127，所以大于127就进行处理
        return pinyin;
    }

    public static String getHeadChar(String str) {//传过来一个汉字，通过该方法来获取首字母

        String convert = "";

        char word = str.charAt(0);               //拿到第一个汉字

        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);   //把第一个字变成拼音

        if (pinyinArray != null) {
            convert += pinyinArray[0].charAt(0);
        } else {
            convert += word;
        }
        return convert.toUpperCase();
    }

    //得到中文首字母缩写
    public static String getPinYinHeadChar(String str) {

        String convert = "";

        for (int j = 0; j < str.length(); j++) {

            char word = str.charAt(j);

            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);

            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert.toUpperCase();
    }
}
