package com.easy.view.image;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.easy.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 选择器适配器
 * Created by Administrator on 2017/5/9.
 */

public class ImagePickerAdapter extends BaseAdapter {
    private static Set<String> mSeletedImg = new HashSet<String>();
    /**
     * 图片目录路径
     */
    private String mDirPath;

    private List<String> mImgPath;
    private LayoutInflater mInflater;

    private Context mContext;
    public ImagePickerAdapter(Context context, List<String> mData, String dirPath){
        this.mDirPath = dirPath;
        this.mImgPath = mData;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mImgPath.size();
    }

    @Override
    public Object getItem(int position) {
        return mImgPath.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.image_picker_adapter_layout,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mImg = (ImageView) convertView.findViewById(R.id.item_image);
            viewHolder.mSelect = (ImageButton) convertView.findViewById(R.id.item_select);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mImg.setImageResource(R.drawable.mis_default_error);
        viewHolder.mSelect.setImageResource(R.drawable.mis_btn_unselected);
        viewHolder.mImg.setColorFilter(null);
        String uri = mDirPath + "/"+mImgPath.get(position);


        Glide.with(mContext).load(uri).into(viewHolder.mImg);


        final String filePath =  mDirPath+"/"+mImgPath.get(position);
        viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //已经被选择
                if (mSeletedImg.contains(filePath)){
                    mSeletedImg.remove(filePath);
                    viewHolder.mImg.setColorFilter(null);
                    viewHolder.mSelect.setImageResource(R.drawable.mis_btn_unselected);
                }else {//未被选择
                    mSeletedImg.add(filePath);
                    viewHolder.mImg.setColorFilter(Color.parseColor("#77000000"));
                    viewHolder.mSelect.setImageResource(R.drawable.mis_btn_selected);
                }
            }
        });
        if (mSeletedImg.contains(filePath)){
            viewHolder.mImg.setColorFilter(Color.parseColor("#77000000"));
            viewHolder.mSelect.setImageResource(R.drawable.mis_btn_selected);
        }
        return convertView;
    }
    private class ViewHolder{
        ImageView mImg;
        ImageButton mSelect;
    }
}
