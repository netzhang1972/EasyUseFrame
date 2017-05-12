package com.easy.view.imagepicker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.easy.R;
import com.easy.abs.BasePopupWindow;
import com.easy.activity.ActivityEvent;
import com.easy.helper.PermissionHelper;
import com.easy.toolbox.ToastUtil;

/**
 * 图片选择器弹出按钮
 * Created by Administrator on 2017/5/12.
 */
public class ImagePickerPopupWindow extends BasePopupWindow implements View.OnClickListener{

    private static final int REGISTER_STORAGE_PERMISSION_CODE = 0;

    private static final int REGISTER_CAMERA_PERMISSION_CODE = 1;

    public ImagePickerPopupWindow(Context context) {
        super(context);
        initView();
        initEvent();
    }
    private void initView() {
        //取消按钮
        getContentView().findViewById(R.id.btn_cancel).setOnClickListener(this);
        //相机按钮
        getContentView().findViewById(R.id.btn_camera).setOnClickListener(this);
        //相册按钮
        getContentView().findViewById(R.id.btn_album).setOnClickListener(this);
    }
    private void initEvent() {
        getActivity().setActivityEvent(new ActivityEvent() {
            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                if (requestCode == REGISTER_CAMERA_PERMISSION_CODE) {
                    if (grantResults.length > 0) {
                        boolean cameraSuccess = true;
                        boolean storageSuccess = true;
                        for (int i = 0, size = permissions.length; i < size; i++) {
                            String permission = permissions[i];
                            if (permission.equals(Manifest.permission.CAMERA)) {
                                if (grantResults.length > i) {
                                    int grantResult = grantResults[i];
                                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                                        cameraSuccess = false;
                                    }
                                } else {
                                    cameraSuccess = false;
                                }
                            } else if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                if (grantResults.length > i) {
                                    int grantResult = grantResults[i];
                                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                                        storageSuccess = false;
                                    }
                                } else {
                                    storageSuccess = false;
                                }
                            }
                        }
                        if (cameraSuccess && storageSuccess) {  //都获取到权限了
                            fromCamera();
                        } else if (!cameraSuccess) {
                            ToastUtil.show(getContext(),getActivity().getString(R.string.camera_function_is_not_enabled));
                        } else {
                            ToastUtil.show(getContext(),getActivity().getString(R.string.the_storage_function_is_not_open));
                        }
                    } else {
                        ToastUtil.show(getContext(),getActivity().getString(R.string.camera_function_is_not_enabled));
                    }
                } else if (requestCode == REGISTER_STORAGE_PERMISSION_CODE) {
                    String permission = permissions[0];
                    if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        int grantResult = grantResults[0];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            ToastUtil.show(getContext(),getActivity().getString(R.string.the_storage_function_is_not_open));
                        } else {
                            fromPicture();
                        }
                    } else {
                        ToastUtil.show(getContext(),getActivity().getString(R.string.the_storage_function_is_not_open));
                    }
                }
            }
        });
    }



    @Override
    public View onCreateContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.image_picker_popup_window_layout, null);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //取消按钮
        if (id == R.id.btn_cancel){
            dismiss();
        }
        //相册按钮
        if (id == R.id.btn_album){
            //检测读取外部存储
            boolean hasStoragePermission = PermissionHelper.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, getContext());
            if (!hasStoragePermission) {
                //申请读取外部存储
                String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
                PermissionHelper.registerPermission(getActivity(), permissions, REGISTER_STORAGE_PERMISSION_CODE);
            } else {
                fromPicture();
            }
            dismiss();
        }
        //相机按钮
        if (id == R.id.btn_camera){
            //检测读取外部存储
            boolean hasStoragePermission = PermissionHelper.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, getContext());
            //检测相机
            boolean hasCameraPermission = PermissionHelper.checkPermission(Manifest.permission.CAMERA, getContext());
            if (!hasCameraPermission || !hasStoragePermission) {
                //申请读取外部存储和相机
                String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
                PermissionHelper.registerPermission(getActivity(), permissions, REGISTER_CAMERA_PERMISSION_CODE);
            } else {
                fromCamera();
            }
            dismiss();
        }
    }

    /**
     * 图片
     */
    private void fromPicture() {
    }

    /**
     * 相机
     */
    private void fromCamera() {
    }
}
