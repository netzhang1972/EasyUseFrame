package com.easy.view.titlebar.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.View;

import com.easy.toolbox.Tools;
import com.easy.view.titlebar.TitleBarView;


/**
 * 抽屉切换助手
 * Created by Administrator on 2017/2/8.
 */
public class LeftDrawerToggleHelper implements DrawerLayout.DrawerListener {
    TitleBarDrawerArrowDrawableToggle mSlider;

    public LeftDrawerToggleHelper(Context context, final DrawerLayout mDrawerLayout, TitleBarView view) {
        if (mSlider == null) {
            mSlider = new TitleBarDrawerArrowDrawableToggle(context);
            mSlider.setColor(view.getTextColor());
        }
        view.setLeftIcon(mSlider);
        view.setLeftIconLayoutParams(Tools.Int2dp(context,35),Tools.Int2dp(context,35));
        view.setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int drawerLockMode = mDrawerLayout.getDrawerLockMode(GravityCompat.START);
                if (mDrawerLayout.isDrawerVisible(GravityCompat.START)
                        && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        view.setLeftIconBackground(BackgroundHelper.RoundBackground(context,view.getIconLeftSize(),view.getIconPressedBackgroundColor()));
    }

    class TitleBarDrawerArrowDrawableToggle extends DrawerArrowDrawable implements DrawerToggle {

        /**
         * @param context used to get the configuration for the drawable from
         */
        public TitleBarDrawerArrowDrawableToggle(Context context) {
            super(context);
        }

        @Override
        public void setPosition(float position) {
            if (position == 1f) {
                setVerticalMirror(true);
            } else if (position == 0f) {
                setVerticalMirror(false);
            }
            setProgress(position);
        }

        @Override
        public float getPosition() {
            return getProgress();
        }
    }

    static interface DrawerToggle {

        public void setPosition(float position);

        public float getPosition();
    }

    public void setIconColor(@ColorInt int color) {
        mSlider.setColor(color);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        mSlider.setPosition(Math.min(1f, Math.max(0, slideOffset)));
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        mSlider.setPosition(1);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        mSlider.setPosition(0);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}