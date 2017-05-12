package com.easy.abs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.easy.activity.BaseTitleBarActivity;

/**
 * Created by Administrator on 2017/5/12.
 */

public abstract class BasePopupWindow extends PopupWindow {
    private BaseTitleBarActivity mActivity;
    private Context mContext;
    public BasePopupWindow(Context context){
        super(context);
        mContext = context;
        if(context instanceof BaseTitleBarActivity){
            mActivity = (BaseTitleBarActivity) context;
        }
        //设置SelectPicPopupWindow的View
        this.setContentView(onCreateContentView(LayoutInflater.from(context)));
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    public Context getContext(){
        if (mContext != null) {
            return mContext;
        }
        return null;
    }
    public BaseTitleBarActivity getActivity(){
        if (mActivity != null) {
            return mActivity;
        }
        return null;
    }

    public abstract View onCreateContentView(LayoutInflater inflater);
}
