package com.pcg.yuquangong.utils;

import android.widget.Toast;

import com.pcg.yuquangong.App;

public class ToastUtils {

    private static Toast sToast;

    public static void showToast(String msg) {
        if (sToast == null) {
            synchronized (ToastUtils.class) {
                if (sToast == null) {
                    sToast = Toast.makeText(App.getInstance(), msg, Toast.LENGTH_LONG);
                }
            }
        }
        sToast.setText(msg);
        sToast.show();
    }

    public static void showToast(int msgId) {
        if (sToast == null) {
            synchronized (ToastUtils.class) {
                if (sToast == null) {
                    sToast = Toast.makeText(App.getInstance(),
                            App.getInstance().getString(msgId), Toast.LENGTH_LONG);
                }
            }
        }
        sToast.setText(App.getInstance().getString(msgId));
        sToast.show();
    }

    public static void showToast(String msg, int duration) {
        if (sToast == null) {
            synchronized (ToastUtils.class) {
                if (sToast == null) {
                    sToast = Toast.makeText(App.getInstance(), msg, duration);
                }
            }
        }
        sToast.setText(msg);
        sToast.show();
    }

    public static void showToast(int msgId, int duration) {
        if (sToast == null) {
            synchronized (ToastUtils.class) {
                if (sToast == null) {
                    sToast = Toast.makeText(App.getInstance(),
                            App.getInstance().getString(msgId), duration);
                }
            }
        }
        sToast.setText(App.getInstance().getString(msgId));
        sToast.show();
    }

}
