package com.easy.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;

import com.easy.activity.BaseTitleBarActivity;
import com.easy.view.titlebar.helper.LeftDrawerToggleHelper;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/30.
 */

public class DrawerActivity extends BaseActivity {
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.left_drawer)
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarContentView(R.layout.activity_drawer);
        getTitleBar().setTitle("左边抽屉");
        mDrawerLayout.addDrawerListener(new LeftDrawerToggleHelper(
                this,mDrawerLayout,getTitleBar()));
    }
}
