package com.grupo3.saloncomunal.utils;

import android.util.Log;

public class BitacoraUtil {

    public static void info(String tag, String mensaje) {
        Log.i(tag, mensaje);
    }

    public static void error(String tag, String mensaje, Exception e) {
        Log.e(tag, mensaje, e);
    }
}
