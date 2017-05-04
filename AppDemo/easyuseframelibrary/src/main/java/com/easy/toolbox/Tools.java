package com.easy.toolbox;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

/**
 * 常用工具
 * Created by Administrator on 2017/3/14.
 */
public class Tools {
    /**
     * 文字大小的px
     * @param context
     * @param value
     * @return
     */
    public static int Int2px(Context context, float value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value,context.getResources().getDisplayMetrics());
    }

    /**
     * 宽高尺寸的dp
     * @param context
     * @param value
     * @return
     */
    public static int Int2dp(Context context, float value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,context.getResources().getDisplayMetrics());
    }

    /**
     * 代码设置Padding
     * @param v
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setPadding(View v,int left, int top, int right, int bottom){
        left = Int2dp(v.getContext(),left);
        top = Int2dp(v.getContext(),top);
        right = Int2dp(v.getContext(),right);
        bottom = Int2dp(v.getContext(),bottom);
        v.setPadding(left,top,right,bottom);
    }
}
