package com.easy.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.easy.activity.BaseTitleBarActivity;
import com.easy.toolbox.ToastUtil;

/**
 * Created by Administrator on 2017/4/21.
 */

public class RightIconSetActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitleBar().setTitle("TitleBar上右边设置图标");
        getTitleBar().setRightIcon(ContextCompat.getDrawable(this,R.drawable.btn_edit));
        getTitleBar().setRightIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(RightIconSetActivity.this,"编辑按钮");
            }
        });
    }
}
