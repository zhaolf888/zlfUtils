package com.zlf.zlfutils;/**
 * Created by Administrator on 2017/6/12 0012.
 */

import android.text.TextUtils;
import android.util.Log;

/**
 * 作者：zhaolifeng
 * 时间：2017/06/12 18:05
 * 描述：Log统一管理类
 */
public class LogUtil {

    public static boolean isShowlog = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化       默认开启
    public static String TAG = "";//自定义tag   如果为空 则取类名            默认为空 显示类名
    public static String SEPARATOR = ",";//分隔号                       默认是逗号间隔
    public static boolean isShowDetial = true;//是否显示 类名 方法名 等信息   默认显示

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isShowlog) {
            if (TextUtils.isEmpty(TAG)) {
                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
                TAG = getDefaultTag(stackTraceElement);
                if (isShowDetial) {
                    Log.i(TAG, getLogInfo(stackTraceElement) + msg);
                } else {
                    Log.i(TAG, msg);
                }
            } else {
                Log.i(TAG, msg);
            }
        }

    }

    public static void d(String msg) {
        if (isShowlog) {
            if (TextUtils.isEmpty(TAG)) {
                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
                TAG = getDefaultTag(stackTraceElement);
                if (isShowDetial) {
                    Log.d(TAG, getLogInfo(stackTraceElement) + msg);
                } else {
                    Log.d(TAG, msg);
                }
            } else {
                Log.d(TAG, msg);
            }
        }

    }

    public static void e(String msg) {
        if (isShowlog) {
            if (TextUtils.isEmpty(TAG)) {
                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
                TAG = getDefaultTag(stackTraceElement);
                if (isShowDetial) {
                    Log.e(TAG, getLogInfo(stackTraceElement) + msg);
                } else {
                    Log.e(TAG, msg);
                }
            } else {
                Log.e(TAG, msg);
            }
        }
    }

    public static void v(String msg) {
        if (isShowlog) {
            if (TextUtils.isEmpty(TAG)) {
                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
                TAG = getDefaultTag(stackTraceElement);
                if (isShowDetial) {
                    Log.v(TAG, getLogInfo(stackTraceElement) + msg);
                } else {
                    Log.v(TAG, msg);
                }
            } else {
                Log.v(TAG, msg);
            }
        }

    }

    public static void w(String msg) {
        if (isShowlog) {
            if (TextUtils.isEmpty(TAG)) {
                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
                TAG = getDefaultTag(stackTraceElement);
                if (isShowDetial) {
                    Log.w(TAG, getLogInfo(stackTraceElement) + msg);
                } else {
                    Log.w(TAG, msg);
                }
            } else {
                Log.w(TAG, msg);
            }
        }

    }


    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isShowlog) {
            if (isShowDetial) {
                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
                Log.i(tag, getLogInfo(stackTraceElement) + msg);
            } else {
                Log.i(tag, msg);
            }
        }

    }

    public static void d(String tag, String msg) {
        if (isShowlog) {
            if (isShowDetial) {
                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
                Log.d(tag, getLogInfo(stackTraceElement) + msg);
            } else {
                Log.d(tag, msg);
            }
        }

    }

    public static void e(String tag, String msg) {
        if (isShowlog) {
            if (isShowDetial) {
                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
                Log.e(tag, getLogInfo(stackTraceElement) + msg);
            } else {
                Log.e(tag, msg);
            }
        }
    }

    public static void v(String tag, String msg) {
        if (isShowlog) {
            if (isShowDetial) {
                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
                Log.v(tag, getLogInfo(stackTraceElement) + msg);
            } else {
                Log.v(tag, msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (isShowlog) {
            if (isShowDetial) {
                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
                Log.w(tag, getLogInfo(stackTraceElement) + msg);
            } else {
                Log.w(tag, msg);
            }
        }
    }


    /**
     * 获取默认的TAG名称.
     * 比如在MainActivity.java中调用了日志输出.
     * 则TAG为MainActivity
     */
    public static String getDefaultTag(StackTraceElement stackTraceElement) {
        String fileName = stackTraceElement.getFileName();
        String stringArray[] = fileName.split("\\.");
        String tag = stringArray[0];
        return tag;
    }

    /**
     * 输出日志所包含的信息
     */
    public static String getLogInfo(StackTraceElement stackTraceElement) {
        StringBuilder logInfoStringBuilder = new StringBuilder();
        // 获取线程名
        String threadName = Thread.currentThread().getName();
        // 获取线程ID
        long threadID = Thread.currentThread().getId();
        // 获取文件名.即xxx.java
        String fileName = stackTraceElement.getFileName();
        // 获取类名.即包名+类名
        String className = stackTraceElement.getClassName();
        // 获取方法名称
        String methodName = stackTraceElement.getMethodName();
        // 获取输出行数
        int lineNumber = stackTraceElement.getLineNumber();

        logInfoStringBuilder.append("[ ");
        logInfoStringBuilder.append("threadID=" + threadID).append(SEPARATOR);
        logInfoStringBuilder.append("threadName=" + threadName).append(SEPARATOR);
        logInfoStringBuilder.append("fileName=" + fileName).append(SEPARATOR);
        logInfoStringBuilder.append("className=" + className).append(SEPARATOR);
        logInfoStringBuilder.append("methodName=" + methodName).append(SEPARATOR);
        logInfoStringBuilder.append("lineNumber=" + lineNumber);
        logInfoStringBuilder.append(" ] ");
        logInfoStringBuilder.append("==>");
        return logInfoStringBuilder.toString();
    }


}
