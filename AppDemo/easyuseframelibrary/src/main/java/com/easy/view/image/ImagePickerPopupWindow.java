//package com.easy.view.image;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.res.Resources;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.v4.content.FileProvider;
//import android.text.TextUtils;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewTreeObserver;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.PopupWindow;
//import android.widget.Toast;
//
//import com.easy.R;
//import com.easy.activity.ActivityEvent;
//import com.easy.activity.BaseTitleBarActivity;
//import com.easy.activity.DisplayGridViewActivity;
//import com.easy.helper.PermissionHelper;
//import com.easy.toolbox.FileUtils;
//
//import java.io.File;
//
///**
// * Created by Administrator on 2017/5/11.
// */
//
//public class ImagePickerPopupWindow extends PopupWindow implements View.OnClickListener{
//    private static final int REGISTER_STORAGE_PERMISSION_CODE = 0;
//    private static final int REGISTER_CAMERA_PERMISSION_CODE = 1;
//    private BaseTitleBarActivity mActivity;
//    private Context mContext;
//    private View mMenuView;
//    /**
//     * 取消按钮
//     */
//    private Button btn_cancel;
//    /**
//     * 相机按钮
//     */
//    private Button btn_camera;
//    /**
//     * 相册按钮
//     */
//    private Button btn_album;
//    private int preHeight = 0;
//    private int navigationBarHeight = 0;
//    private View view_parent;
//    private String imagePath;
//    private ViewTreeObserver.OnGlobalLayoutListener heightListener = new ViewTreeObserver.OnGlobalLayoutListener() {
//        @Override
//        public void onGlobalLayout() {
//            int currentHeight = view_parent.getHeight();
//            if(preHeight - currentHeight == navigationBarHeight){
//                ImagePickerPopupWindow.this.update(ViewGroup.LayoutParams.MATCH_PARENT, mMenuView.getHeight() - navigationBarHeight);
//                preHeight = currentHeight;
//            }else if (preHeight - currentHeight == -navigationBarHeight){
//                ImagePickerPopupWindow.this.update(ViewGroup.LayoutParams.MATCH_PARENT, mMenuView.getHeight() + navigationBarHeight);
//                preHeight = currentHeight;
//            }
//        }
//    };
//    public ImagePickerPopupWindow(final Context context, final View view_parent){
//        super(context);
//        if (context instanceof BaseTitleBarActivity){
//            mActivity = (BaseTitleBarActivity) context;
//            mActivity.setActivityEvent(new ActivityEvent() {
//                @Override
//                public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//                    if (requestCode == REGISTER_CAMERA_PERMISSION_CODE) {
//                        if (grantResults.length > 0) {
//                            boolean cameraSuccess = true;
//                            boolean storageSuccess = true;
//                            for (int i = 0, size = permissions.length; i < size; i++) {
//                                String permission = permissions[i];
//                                if (permission.equals(Manifest.permission.CAMERA)) {
//                                    if (grantResults.length > i) {
//                                        int grantResult = grantResults[i];
//                                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
//                                            cameraSuccess = false;
//                                        }
//                                    } else {
//                                        cameraSuccess = false;
//                                    }
//                                } else if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                                    if (grantResults.length > i) {
//                                        int grantResult = grantResults[i];
//                                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
//                                            storageSuccess = false;
//                                        }
//                                    } else {
//                                        storageSuccess = false;
//                                    }
//                                }
//                            }
//                            if (cameraSuccess && storageSuccess) {  //都获取到权限了
//                                fromCamera();
//                            } else if (!cameraSuccess) {
//                                Toast.makeText(mContext,"摄像头权限未开启,无法拍照!",Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(mContext,"存储空间权限未开启，无法拍照!",Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(mContext,"摄像头权限未开启，无法拍照",Toast.LENGTH_SHORT).show();
//                        }
//                    } else if (requestCode == REGISTER_STORAGE_PERMISSION_CODE) {
//                        String permission = permissions[0];
//                        if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                            int grantResult = grantResults[0];
//                            if (grantResult != PackageManager.PERMISSION_GRANTED) {
//                                Toast.makeText(mContext,"存储空间权限未开启，无法打开图库!",Toast.LENGTH_SHORT).show();
//                            } else {
//                                fromPicture();
//                            }
//                        } else {
//                            Toast.makeText(mContext,"存储空间权限未开启，无法打开图库!",Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                }
//            });
//        }
//        this.mContext = context;
//        this.view_parent = view_parent;
//        mMenuView = LayoutInflater.from(context).inflate(R.layout.image_picker_popup_window_layout, null);
//        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
//        //取消按钮
//        btn_cancel.setOnClickListener(this);
//        btn_camera = (Button)mMenuView.findViewById(R.id.btn_take_photo);
//        btn_camera.setOnClickListener(this);
//        btn_album = (Button)mMenuView.findViewById(R.id.btn_pick_photo);
//        btn_album.setOnClickListener(this);
//        //设置SelectPicPopupWindow的View
//        this.setContentView(mMenuView);
//        //设置SelectPicPopupWindow弹出窗体的宽
//        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        //设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        preHeight = view_parent.getHeight();
//        navigationBarHeight = getNavigationBarHeight();
//        //设置SelectPicPopupWindow弹出窗体可点击
//        this.setFocusable(true);
//        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.CustomDialogStyle);
//        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0x50000000);
//        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
//        //设置layout在PopupWindow中显示的位置
//        showAtLocation(view_parent, Gravity.TOP, 0, 0);
//        this.setOnDismissListener(new OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                view_parent.getViewTreeObserver().addOnGlobalLayoutListener(heightListener);
//            }
//        });
//        view_parent.getViewTreeObserver().addOnGlobalLayoutListener(heightListener);
//        mMenuView.setAnimation(AnimationUtils.loadAnimation(mMenuView.getContext(), R.anim.popup_in));
//        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        mMenuView.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        v.performClick();
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });
//    }
//    private int getNavigationBarHeight() {
//        Resources resources = mContext.getResources();
//        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
//        int height = 0;
//        if(resourceId > 0){
//            resources.getDimensionPixelSize(resourceId);
//        }
//        return height;
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.btn_cancel){
//            dismiss();
//        }
//        if (id == R.id.btn_pick_photo){
//            boolean hasStoragePermission = PermissionHelper.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, mContext);
//            if (!hasStoragePermission) {
//                String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
//                PermissionHelper.registerPermission(mActivity, permissions, REGISTER_STORAGE_PERMISSION_CODE);
//            } else {
//                fromPicture();
//            }
//            dismiss();
//        }
//        if(id == R.id.btn_take_photo){
//            boolean hasStoragePermission = PermissionHelper.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, mContext);
//            boolean hasCameraPermission = PermissionHelper.checkPermission(Manifest.permission.CAMERA, mContext);
//            if (!hasCameraPermission || !hasStoragePermission) {
//                String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
//                PermissionHelper.registerPermission(mActivity, permissions, REGISTER_CAMERA_PERMISSION_CODE);
//            } else {
//                fromCamera();
//            }
//            dismiss();
//        }
//    }
//    private void fromCamera() {
//        String sdStatus = Environment.getExternalStorageState();
//        if (!TextUtils.equals(sdStatus, Environment.MEDIA_MOUNTED)) {
//
//            Toast.makeText(mContext,"未检测到SD卡",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        File files = new File(FileUtils.SDPATH);
//        if (!files.exists()) {
//            files.mkdir();
//        }
//        File file = new File(FileUtils.SDPATH, String.valueOf(System.currentTimeMillis()) + ".png");
//        Uri provider;
//        if (Build.VERSION.SDK_INT >= 24) {
//            provider = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", file);
//        } else {
//            provider = Uri.fromFile(file);
//        }
//        imagePath = file.getPath();
//        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        openCameraIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, provider);
//        mActivity.startActivityForResult(openCameraIntent, 0x0011);
//    }
//    //打开图库
//    private void fromPicture() {
//        Intent intent = new Intent(mContext, DisplayGridViewActivity.class);
//        mActivity.startActivityForResult(intent, 0x0012);
//    }
//}
