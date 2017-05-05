package com.easy.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.easy.R;
import com.easy.view.titlebar.TitleBarView;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/14.
 */

public abstract class BaseTitleBarActivity extends AppCompatActivity {
    /**
     * 总布局.
     */
    private RelativeLayout Total_layout = null;
    /**
     * 标题栏
     */
    private TitleBarView mTitleBar;
    /**
     * 内容布局
     */
    private RelativeLayout contentLayout = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //总布局
        Total_layout = new RelativeLayout(this);
        //标题栏
        mTitleBar = new TitleBarView(this);
        mTitleBar.setId(R.id.titleBarID);
        //内容布局
        contentLayout = new RelativeLayout(this);
        Total_layout.addView(mTitleBar);

        View line = new View(this);
        line.setId(R.id.titleBarLine);
        line.setBackgroundColor(ContextCompat.getColor(this,R.color.easy_line_gray));
        RelativeLayout.LayoutParams layoutParamsLine = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, 2
        );
        layoutParamsLine.addRule(RelativeLayout.BELOW,mTitleBar.getId());
        Total_layout.addView(line, layoutParamsLine);
        RelativeLayout.LayoutParams layoutParamsContent = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParamsContent.addRule(RelativeLayout.BELOW,line.getId());
        Total_layout.addView(contentLayout, layoutParamsContent);
        //设置ContentView
        setContentView(Total_layout,new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        ));
    }
    public TitleBarView getTitleBar(){
        return mTitleBar;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        //初始化注释
        ButterKnife.bind(this);
    }
    /**
     * 描述：用指定资源ID表示的View填充主界面.
     *
     * @param resId 指定的View的资源ID
     */
    public void setTitleBarContentView(int resId) {
        setTitleBarContentView(getLayoutInflater().inflate(resId, null));
    }
    /**
     * 描述：用指定的View填充主界面.
     *
     * @param contentView 指定的View
     */
    private void setTitleBarContentView(View contentView) {
        contentLayout.removeAllViews();
        contentLayout.addView(contentView,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        //初始化注释
        ButterKnife.bind(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    
    public void addFragment(int layoutId,Fragment newFragment) {
        getSupportFragmentManager().beginTransaction()
                .add(layoutId, newFragment)
                .commit();
    }
}
