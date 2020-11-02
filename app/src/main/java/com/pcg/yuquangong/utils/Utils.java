package com.pcg.yuquangong.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String[] getBirthdayArray(String birthday) {
        if (TextUtils.isEmpty(birthday)) {
            return null;
        }

        birthday = birthday.trim();
        if (TextUtils.isEmpty(birthday)) {
            return null;
        }

        birthday = birthday.replaceAll(" ", "");
        if (TextUtils.isEmpty(birthday)) {
            return null;
        }

        String[] birthdayArray = birthday.split("-");
        if (birthdayArray != null && birthdayArray.length == 3) {
            return birthdayArray;
        }

        return null;
    }


    private static final int MIN_DELAY_TIME= 500;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

}
