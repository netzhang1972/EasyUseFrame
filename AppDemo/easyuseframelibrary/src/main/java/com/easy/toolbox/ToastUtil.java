package com.easy.toolbox;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/4/27.
 */

public class ToastUtil {
    private static Toast toast = null;

    public static void show(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }
}
