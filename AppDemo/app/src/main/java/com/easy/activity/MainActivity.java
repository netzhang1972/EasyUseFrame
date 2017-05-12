package com.easy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.easy.activity.BaseTitleBarActivity;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.tv01)
    TextView tv01;
    @Bind(R.id.tv02)
    TextView tv02;
    @Bind(R.id.tv03)
    TextView tv03;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarContentView(R.layout.activity_main);
        getTitleBar().setTitle("测试TitleBar");
        getTitleBar().setLeftIcon(null);
        tv01.setOnClickListener(this);
        tv02.setOnClickListener(this);
        tv03.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv01:
                startActivity(new Intent(MainActivity.this,DrawerActivity.class));
                break;
            case R.id.tv02:
                startActivity(new Intent(MainActivity.this,RightIconSetActivity.class));
                break;
            case R.id.tv03:
                startActivity(new Intent(MainActivity.this,ImagePickerActivity.class));
                break;
        }
    }
}
