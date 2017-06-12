package com.zlf.zlfutils;/**
 * Created by Administrator on 2017/6/12 0012.
 */

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：zhaolifeng
 * 时间：2017/06/12 17:36
 * 描述：
 */
public class EditTextUtil {



    public static void limitPirceEditTextSizes(final EditText et, final int limitSize, final int poiotSize) {
        if (et == null) {
            return;
        }
        //输入字符串数量限制
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String text = s.toString();
                    //如果是整数，则最多输入限制个数字
                    if (!text.contains(".")) {
                        //超过限制个数字，截取限制内的数字限制，超过的裁剪掉
                        if (text.length() > limitSize) {
                            text = text.substring(0, limitSize);
                            et.setText(text);
                            et.setSelection(text.length());
                            return;
                        }
                    }
                    // 删除“.”后面超过pointSize位后的数据
                    if (text.contains(".")) {
                        if (s.length() - 1 - s.toString().indexOf(".") > poiotSize) {
                            s = s.toString().subSequence(0,
                                    s.toString().indexOf(".") + poiotSize + 1);
                            et.setText(s);
                            et.setSelection(s.length()); // 光标移到最后
                        }
                    }
                    // 如果"."在起始位置,则起始位置自动补0
                    if (s.toString().trim().substring(0).equals(".")) {
                        s = "0" + s;
                        et.setText(s);
                        et.setSelection(2);
                    }

                    // 如果起始位置为0,且第二位跟的不是".",则无法后续输入
                    if (s.toString().startsWith("0")
                            && s.toString().trim().length() > 1) {
                        if (!s.toString().substring(1, 2).equals(".")) {
                            et.setText(s.subSequence(0, 1));
                            et.setSelection(1);
                            return;
                        }
                    }
//                    if (text.contains(".")) {
//                        if (s.toString().substring(s.toString().indexOf(".") + 1).contains(".")) {
//                            if (!s.toString().substring(s.toString().indexOf(".") + 1).equals(".")) {
//                                et.setText(s.toString().substring(0, s.toString().indexOf(".") + 2));
//                                et.setSelection(s.toString().indexOf(".") + 2);
//                            } else {
//                                et.setText(s.toString().substring(0, s.toString().indexOf(".") + 1));
//                                et.setSelection(s.toString().indexOf(".") + 1);
//                            }
//                            return;
//                        }
//                    }
                } catch (Exception e) {

                }


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }





    /**
     * 只限制小数位
     * @param et
     * @param
     * @param poiotSize
     */
    public static void limitPirceEditTextSizePoints(final EditText et,final int poiotSize) {
        if (et == null) {
            return;
        }
        //输入字符串数量限制
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String text = s.toString();
                    // 删除“.”后面超过pointSize位后的数据
                    if (text.contains(".")) {
                        if (s.length() - 1 - s.toString().indexOf(".") > poiotSize) {
                            s = s.toString().subSequence(0,
                                    s.toString().indexOf(".") + poiotSize + 1);
                            et.setText(s);
                            et.setSelection(s.length()); // 光标移到最后
                        }
                    }
                    // 如果"."在起始位置,则起始位置自动补0
                    if (s.toString().trim().substring(0).equals(".")) {
                        s = "0" + s;
                        et.setText(s);
                        et.setSelection(2);
                    }

                    // 如果起始位置为0,且第二位跟的不是".",则无法后续输入
                    if (s.toString().startsWith("0")
                            && s.toString().trim().length() > 1) {
                        if (!s.toString().substring(1, 2).equals(".")) {
                            et.setText(s.subSequence(0, 1));
                            et.setSelection(1);
                            return;
                        }
                    }
                    if (text.contains(".")) {
                        if (s.toString().substring(s.toString().indexOf(".") + 1).contains(".")) {
                            if (!s.toString().substring(s.toString().indexOf(".") + 1).equals(".")) {
                                et.setText(s.toString().substring(0, s.toString().indexOf(".") + 2));
                                et.setSelection(s.toString().indexOf(".") + 2);
                            } else {
                                et.setText(s.toString().substring(0, s.toString().indexOf(".") + 1));
                                et.setSelection(s.toString().indexOf(".") + 1);
                            }
                            return;
                        }
                    }
                } catch (Exception e) {

                }


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }





    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    public static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0)
                || (codePoint == 0x9)
                || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 判断是否是Emoji  和上面同时使用  一起判断
     * @param string
     * @return
     */
    public static boolean isEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }








}
