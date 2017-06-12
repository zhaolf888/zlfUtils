package com.zlf.zlfutils;/**
 * Created by Administrator on 2017/6/12 0012.
 */
import java.util.List;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * 作者：zhaolifeng
 * 时间：2017/06/12 19:13
 * 描述：
 */
public class NetworkUtils {
    /**
     * 判断网络连接是否已开
     * @param context
     * @return true 已打开  false 未打开
     */
    public static boolean isConn(Context context){
        if(context == null){
            return true;
        }

        boolean bisConnFlag=false;
        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if(network!=null){
            bisConnFlag=conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }



    /**
     * 打开网络设置界面，不带提示框
     * @param context
     */
    public static void setNetwork(Context context){
        Intent intent=null;
        //判断手机系统的版本  即API大于10 就是3.0或以上版本
        if(android.os.Build.VERSION.SDK_INT>10){
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        }else{
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        context.startActivity(intent);
    }


    /**
     * 检测网络是否可用
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 需要权限:android.permission.GET_TASKS 经测试比较可靠
     *
     * @param context
     * @return
     */
    public static boolean isApplicationBroughtToBackground(Context context) {
        try {
            ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (tasks != null && !tasks.isEmpty()) {
                ComponentName topActivity = tasks.get(0).topActivity;
                LogUtil.e("topActivity：" + topActivity.flattenToString());
                if (!topActivity.getPackageName().equals(
                        context.getPackageName())) {
                    LogUtil.e("App处于后台");
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.e("App处于前台");
        return false;
    }

}
