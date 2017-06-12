package com.zlf.zlfutils;/**
 * Created by Administrator on 2017/6/12 0012.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * 作者：zhaolifeng
 * 时间：2017/06/12 17:16
 * 描述：一些公共的方法
 */
public class PublicUtils {

    //TextView赋值  核心类   只为减少 java代码
    /**
     * @param view     目标TextView
     * @param text     目标文本
     * @param defValue 默认文本
     */
    public static void setTextStr(TextView view, String text, String defValue) {
        if (null == view) {
            return;
        }
        if (!TextUtils.isEmpty(defValue)) {
            view.setText(defValue);
            return;
        }else{
            if (!TextUtils.isEmpty(text)) {
                view.setText(text);
                return;
            }
        }
        view.setText("");
    }

    //TextView赋值
    /**
     * @param view 目标TextView
     * @param text 目标文本
     */
    public static void setTextStr(TextView view, String text) {
        setTextStr(view, text, "");
    }


    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }

        return false;
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }


    /*判断中文字符个数*/
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }



}
