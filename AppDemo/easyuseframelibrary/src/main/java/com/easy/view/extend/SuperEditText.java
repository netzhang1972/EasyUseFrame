package com.easy.view.extend;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 超级EditText
 * 1.实现左边设置图标
 * 2.实现右边设置清除按钮、密文和明文设置按钮、获取验证码按钮
 * Created by Administrator on 2017/5/5.
 */

public class SuperEditText extends RelativeLayout{

    public SuperEditText(Context context) {
        super(context);
    }

    public SuperEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
