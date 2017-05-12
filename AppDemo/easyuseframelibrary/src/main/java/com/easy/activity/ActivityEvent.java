package com.easy.activity;

import android.support.annotation.NonNull;

/**
 * Activity事件
 * Created by Administrator on 2017/5/11.
 */

public interface ActivityEvent {
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
