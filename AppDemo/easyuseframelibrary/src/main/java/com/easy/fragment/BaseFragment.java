package com.easy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easy.activity.BaseTitleBarActivity;
import com.easy.view.titlebar.TitleBarView;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/28.
 */

public abstract class BaseFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(onCreateLayout(), container, false);
        //初始化注释
        ButterKnife.bind(this, view);
        onInitView();
        return view;
    }
    public abstract int onCreateLayout();

    public abstract void onInitView();

    /**
     * 获取标题栏
     *
     * @return
     */
    public TitleBarView getTitleBar() {
        if(getActivity()instanceof BaseTitleBarActivity){
            return ((BaseTitleBarActivity) getActivity()).getTitleBar();
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
