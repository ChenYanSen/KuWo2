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
    *  �õ�ָ�����ֵ�ƴ��
    *
    * */

    public static String getPinYin(String hanzi) {
        String pinyin = "";

        //������ƴ���ĸ�ʽ��
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);                   //ת������ȫ��д��ʽ���
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);               //ת��֮����û����������ʽ���

        //�Ե������ֽ���ת��
        char[] arr = hanzi.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (Character.isWhitespace(arr[i]))
                continue;//����ǿո��򲻴��������´α���
            if (arr[i] > 127) {//������2���ֽڴ���127
                try {
                    String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(arr[i], format);
                    if (pinyinArr != null) {
                        pinyin += pinyinArr[0];
                    } else {
                        pinyin += pinyinArr[i];
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                    //������ȷ�ĺ���
                    pinyin += arr[i];
                }
            } else {
                //���Ǻ���
                pinyin += arr[i];
            }
        }
        //������2���ֽڴ洢���϶�����127�����Դ���127�ͽ��д���
        return pinyin;
    }

    public static String getHeadChar(String str) {//������һ�����֣�ͨ���÷�������ȡ����ĸ

        String convert = "";

        char word = str.charAt(0);               //�õ���һ������

        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);   //�ѵ�һ���ֱ��ƴ��

        if (pinyinArray != null) {
            convert += pinyinArray[0].charAt(0);
        } else {
            convert += word;
        }
        return convert.toUpperCase();
    }

    //�õ���������ĸ��д
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
