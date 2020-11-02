package com.pcg.yuquangong.utils;

import com.google.gson.Gson;

public class GsonHelper {

    private static Gson sGson;

    public static Gson getGson() {
        if (sGson == null) {
            synchronized (GsonHelper.class) {
                if (sGson == null) {
                    sGson = new Gson();
                }
            }
        }
        return sGson;
    }

}
