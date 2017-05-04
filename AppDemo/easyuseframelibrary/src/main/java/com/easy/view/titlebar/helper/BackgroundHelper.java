
package com.easy.view.titlebar.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;

import com.easy.toolbox.Tools;

/**
 * Background Helper Class
 * Created by Administrator on 2017/3/27.
 */

public class BackgroundHelper {
    private Context mContext;
    private GradientDrawable gradientDrawable;
    public BackgroundHelper(Context context,int shape){
        mContext = context;
        gradientDrawable = new GradientDrawable();
        gradientDrawable.setBounds(50,50,50,50);
        gradientDrawable.setShape(shape);
        gradientDrawable.setUseLevel(false);
    }

    /**
     * set size
     * @param width
     * @param height
     * @return
     */
    public BackgroundHelper setSize(int width, int height){
        gradientDrawable.setSize(width,height);
        return this;
    }

    /**
     * set Solid Color
     * @param solidColor
     * @return
     */
    public BackgroundHelper setSolidColor(int solidColor){
        gradientDrawable.setColor(solidColor); //背景色
        return this;
    }

    /**
     * set Stroke
     * @param strokeWidth
     * @param strokeColor
     * @return
     */
    public BackgroundHelper setStroke(int strokeWidth, int strokeColor){
        gradientDrawable.setStroke(strokeWidth,strokeColor); //边框宽度，边框颜色
        return this;
    }

    /**
     * set Corners Radius
     * @param f
     * @return
     */
    public BackgroundHelper setCornersRadius(float f){
        gradientDrawable.setCornerRadius(f);
        return this;
    }

    /**
     * set Corners Radius
     * @param radii
     * @return
     */
    public BackgroundHelper setCornersRadius(float[] radii){
        gradientDrawable.setCornerRadii(radii);
        return this;
    }

    public GradientDrawable create(){
        return gradientDrawable;
    }

    /**
     * Rect Background
     * @param context
     * @param color
     * @param f
     * @return
     */
    public static Drawable RectBackground(Context context,int color,int size,float f){
        Drawable normalDraw = new BackgroundHelper(context, GradientDrawable.RECTANGLE)
                .create();
        Drawable pressedDraw = new BackgroundHelper(context, GradientDrawable.RECTANGLE)
                .setSolidColor(color)
                .setSize(size,size+10)
                .setCornersRadius(f)
                .create();
        pressedDraw.setAlpha(50);
        return getSelector(normalDraw,pressedDraw);
    }
    /**
     * Round Background
     * @param context
     * @param size
     * @param color
     * @return
     */
    public static Drawable RoundBackground(Context context,int size,int color){
        int sizes = Tools.Int2dp(context,50);
        Drawable normalDraw = new BackgroundHelper(context, GradientDrawable.OVAL)
                .create();
        Drawable pressedDraw = new BackgroundHelper(context, GradientDrawable.OVAL)
                .setSize(sizes,sizes)
                .setSolidColor(color)

                .create();
        pressedDraw.setAlpha(50);
        return getSelector(normalDraw,pressedDraw);
    }
    /**
     * set Selector
     * @param normalDraw
     * @param pressedDraw
     * @return
     */
    private static StateListDrawable getSelector(Drawable normalDraw, Drawable pressedDraw) {
        StateListDrawable stateListDrawable  = new StateListDrawable();
        stateListDrawable.addState(new int[]{ android.R.attr.state_pressed }, pressedDraw);
        stateListDrawable.addState(new int[]{ }, normalDraw);
        return stateListDrawable ;
    }
}
