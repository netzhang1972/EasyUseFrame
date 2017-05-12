package com.easy.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 许可权限助手
 * Created by Administrator on 2017/5/11.
 */

public class PermissionHelper {
    /**
     * 检测所需权限是否已授权
     * @param permission
     * @param mContext
     * @return
     */
    public static boolean checkPermission(String permission,Context mContext){
        if(Build.VERSION.SDK_INT >= 23){    //6.0以上,需要动态权限
            int permissionCode = ContextCompat.checkSelfPermission(mContext,permission);
            if(permissionCode == PackageManager.PERMISSION_GRANTED){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }

    /**
     * 请求所需权限
     * @param activity
     * @param permissions
     * @param requestCode
     */
    public static void registerPermission(Activity activity, String[] permissions, int requestCode){
        ActivityCompat.requestPermissions(activity,permissions,requestCode);
    }

}
