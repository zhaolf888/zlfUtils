package com.zlf.zlfutils;/**
 * Created by Administrator on 2017/6/12 0012.
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者：zhaolifeng
 * 时间：2017/06/12 19:19
 * 描述：
 */
public class PreferenceUtils {
    public static String userinfo = "userinfo";//初始化的时候根据需要自己更改
    /**
     * 设置SharedPreferences名,不设置时默认为userinfo
     * @param key
     * @param value
     */
    public static void savePreferenceBoolean(Context context, String key, boolean value)
    {

        SharedPreferences preferences = context.getSharedPreferences(userinfo,0);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 读取一个key Boolean值方法  默认为false
     * @param key
     * @return
     */
    public static boolean getPreferenceBoolean(Context context,String key) {
        SharedPreferences preferences = context.getSharedPreferences(userinfo,
                0);
        return preferences.getBoolean(key, false);
    }

    /**
     * 设置SharedPreferences名,不设置时默认为userinfo
     * @param key
     * @param value
     */
    public  static void savePreferenceInt(Context context,String key, int value)
    {
        SharedPreferences preferences = context.getSharedPreferences(userinfo,
                -1);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 读取一个key int值方法  默认为false
     * @param key
     * @return
     */
    public  static int getPreferenceInt(Context context,String key) {
        SharedPreferences preferences = context.getSharedPreferences(userinfo,
                -1);
        return preferences.getInt(key, -1);
    }

    /**
     * 保存一个key方法
     * @param key
     * @param value
     */
    public  static void savePreferenceString(Context context,String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(userinfo,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 删除一个key
     * @param key
     */
    public  static void deleteKey(Context context,String key) {
        SharedPreferences preferences = context.getSharedPreferences(userinfo,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 读取一个key Long值方法  默认Mode 为0
     * @param key
     * @return
     */
    public  static void savePreferenceLong(Context context,String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(userinfo,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**读取一个key String值方法  默认Mode 为0
     *
     * @param key
     * @return
     */
    public  static String getPreferenceString(Context context,String key) {
        SharedPreferences preferences = context.getSharedPreferences(userinfo,
                0);
        return preferences.getString(key, "");
    }

    public  static long getPreferenceLong(Context context,String key) {
        SharedPreferences preferences = context.getSharedPreferences(userinfo,
                0);
        return preferences.getLong(key, 0L);
    }

}
