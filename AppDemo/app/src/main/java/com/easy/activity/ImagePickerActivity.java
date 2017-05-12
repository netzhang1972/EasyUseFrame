package com.easy.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.easy.view.imagepicker.ImagePickerPopupWindow;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/5.
 */

public class ImagePickerActivity extends BaseActivity{
    private static final String TAG = "ImagePickerActivity";
    @Bind(R.id.btn_01)
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarContentView(R.layout.android_image_picker);
        getTitleBar().setTitle("选择图片");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ==========================");
                new ImagePickerPopupWindow(ImagePickerActivity.this);
//                ImagePickerPopupWindow.getInstance(ImagePickerActivity.this,btn);
            }
        });
    }
}
