package com.pcg.yuquangong.model.serialport;

import com.deemons.serialportlib.SerialPort;
import com.deemons.serialportlib.SerialPortFinder;
import com.pcg.yuquangong.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class SerialPortManager {

    private SerialPort mSerialPort = null;

    private static SerialPortManager mSerialPortManager;

    // 双、左、右
    public static final String DOUBLE_CODE = "02";
    public static final String LEFT_CODE = "05";
    public static final String RIGHT_CODE = "06";

    //强：06，中:04，低：02
    public static final String HIGH_CURE = "06";
    public static final String MIDDLE_CURE = "04";
    public static final String SMALL_CURE = "02";

    // SUM 结尾
    public static final String FOOTER_CODE = "0000";

    public static final String QUERY_DEVICE_STATE = "59580708";

    public static final String DEVICE_ALARM_STATE = "5958050000";

    public static final String STOP_DOUBLE_CURE = "595805" + DOUBLE_CODE;
    public static final String STOP_LEFT_CURE = "595805" + LEFT_CODE;
    public static final String STOP_RIGHT_CURE = "595805" + RIGHT_CODE;

    public static final String START_DOUBLE_CURE = "595803" + DOUBLE_CODE;
    public static final String START_LEFT_CURE = "595803" + LEFT_CODE;
    public static final String START_RIGHT_CURE = "595803" + RIGHT_CODE;

    // 0x59 0x58 0x01 当前时间(7B) 生日时间(7B) SUM
    public static final String CALCULATE_ACUPOINT_HEADER = "595801";

    public static SerialPortManager getSerialPortManager() {
        if (mSerialPortManager == null) {
            synchronized (SerialPortManager.class) {
                if (mSerialPortManager == null) {
                    mSerialPortManager = new SerialPortManager();
                }
            }
        }
        return mSerialPortManager;
    }

    public ArrayList<String> getAllSerialPort() {
        ArrayList<String> list = new ArrayList<>();
        SerialPortFinder finder = new SerialPortFinder();
        String[] path = finder.getAllDevicesPath();
        for (String s : path) {
            list.add(s);
        }
        return list;
    }

    public SerialPort openSerailPort(String mPath) {
        try {
            mSerialPort =
                    new SerialPort(new File(mPath), 9600, 0, 8, 1, 0);
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtils.showToast("打开失败！请尝试其它串口！");
        } catch (SecurityException e) {
            e.printStackTrace();
            ToastUtils.showToast(String.format(Locale.getDefault(), "获取 root 权限失败！您可以更换串口，或者尝试修改 su 路径。一般 su 路径为：/system/bin/su 或 /system/xbin/su 。当前 su 路径: %s 您可以重新输入 su 路径", SerialPort.getSuPath()));
        }
        return mSerialPort;
    }

    public void closeSerailPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
        }
    }

}
