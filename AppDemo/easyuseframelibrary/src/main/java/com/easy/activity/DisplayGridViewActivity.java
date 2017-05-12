package com.easy.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.R;
import com.easy.view.image.FolderBean;
import com.easy.view.image.ImagePickerAdapter;
import com.easy.view.image.ListImageDirPopupWindow;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/11.
 */

public class DisplayGridViewActivity extends BaseTitleBarActivity{
    private GridView mGridView;
    private ImagePickerAdapter adapter;
    private List<String> mImgs;

    private TextView mDirName;
    private TextView mDirCount;
    private File mCurrentDir;
    private int mMaxCount;
    private RelativeLayout bottomBar;
    /**
     * 进度条
     */
    private ProgressDialog mProgressDialog;

    private List<FolderBean> mFolderBeans = new ArrayList<FolderBean>();

    private static final int DATA_LOADED = 0x110;

    private ListImageDirPopupWindow mDirPopupWindow;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == DATA_LOADED){
                mProgressDialog.dismiss();
                //绑定数据到View中
                data2View();
                initDirPopupWindow();
            }
        }
    };

    private void initDirPopupWindow() {
        mDirPopupWindow = new ListImageDirPopupWindow(this,mFolderBeans);
        mDirPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    /**
     * 内容区域变量
     */
    private void lightOn() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }
    /**
     * 内容区域变暗
     */
    private void lightOff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }
    private void data2View() {
        if (mCurrentDir == null){
            Toast.makeText(this,"未扫描到任何图片",Toast.LENGTH_SHORT).show();
            return;
        }
        mImgs = Arrays.asList(mCurrentDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".jpg")||name.endsWith(".jpeg")||name.endsWith(".png")) {
                    return true;
                }
                return false;
            }
        }));
        adapter = new ImagePickerAdapter(this,mImgs,mCurrentDir.getAbsolutePath());
        mGridView.setAdapter(adapter);
        mDirCount.setText(mMaxCount+"");
//        mDirName.setText(mCurrentDir.getName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarContentView(R.layout.activity_display_grid_view);
        getTitleBar().setTitle("图片和视频");
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        bottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDirPopupWindow.setAnimationStyle(R.style.CustomDialogStyle);
                mDirPopupWindow.showAsDropDown(bottomBar,0,0);
                lightOff();
            }
        });
    }



    /**
     * 利用ContentProvider扫描手机中的所有图片
     */
    private void initData() {
        //扫描存储卡
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this,"当前存储卡不可用",Toast.LENGTH_SHORT).show();
            return;
        }
        mProgressDialog = ProgressDialog.show(this,null,"正在加载...");
        new Thread(){
            @Override
            public void run() {
                Uri mImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver cr = DisplayGridViewActivity.this.getContentResolver();
                String[] projection = {MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID,
                        MediaStore.Images.Thumbnails.DATA};
                //获取游标
                Cursor cursor = cr.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection,
                        null, null, null);
                Set<String> mDirPaths = new HashSet<String>();
                while (cursor.moveToNext()){
                    //当前图片的路径
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null){
                        continue;
                    }
                    //文件夹路径
                    String dirPath = parentFile.getAbsolutePath();
                    FolderBean folderBean = null;

                    if (mDirPaths.contains(dirPath)){
                        continue;
                    }else {
                        mDirPaths.add(dirPath);
                        folderBean = new FolderBean();
                        folderBean.setDir(dirPath);
                        folderBean.setFirstImgPath(path);
                    }
                    if (parentFile.list() == null){
                        continue;
                    }
                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            if (name.endsWith(".jpg")||name.endsWith(".jpeg")||name.endsWith(".png")) {
                                return true;
                            }
                            return false;
                        }
                    }).length;
                    folderBean.setCount(picSize);
                    mFolderBeans.add(folderBean);
                    if (picSize > mMaxCount){
                        mMaxCount = picSize;
                        mCurrentDir = parentFile;
                    }
                }
                cursor.close();
                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(DATA_LOADED);
            }
        }.start();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mGridView = (GridView) findViewById(R.id.picker_grid_view);
        mDirName = (TextView) findViewById(R.id.dir_name);
        mDirCount = (TextView)findViewById(R.id.dir_count);
        bottomBar = (RelativeLayout)findViewById(R.id.bottom_bar);
    }
}
