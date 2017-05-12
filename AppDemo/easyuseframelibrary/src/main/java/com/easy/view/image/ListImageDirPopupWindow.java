package com.easy.view.image;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easy.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class ListImageDirPopupWindow extends PopupWindow {
    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHeight;
    /**
     * 布局
     */
    private View mConvertView;
    /**
     *
     */
    private ListView mListView;

    private List<FolderBean> mData;
    public ListImageDirPopupWindow(Context context,List<FolderBean> data){
        calWidthAndHeight(context);
        mConvertView = LayoutInflater.from(context).inflate(R.layout.list_popup_window,null);
        mData = data;
        setContentView(mConvertView);
        setWidth(mWidth);
        setHeight(mHeight);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE){
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        initViews(context);
        initEvent();
    }
    private void initViews(Context context) {
        mListView = (ListView) mConvertView.findViewById(R.id.list_dir);
        mListView.setAdapter(new ListDirAdapter(context,mData));
    }
    private void initEvent() {
    }



    /**
     * 计算PopupWindow的宽度和高度
     * @param context
     */
    private void calWidthAndHeight(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mWidth = outMetrics.widthPixels;
        mHeight = (int) (outMetrics.heightPixels*0.7);
    }
    private class ListDirAdapter extends ArrayAdapter<FolderBean>{

        private LayoutInflater mInflater;

        private List<FolderBean> mData;
        public ListDirAdapter(Context context,List<FolderBean> objects) {
            super(context, 0, objects);
            mInflater = LayoutInflater.from(context);
            mData = objects;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null)
            {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_popup_window,parent,false);
                holder.mImg = (ImageView) convertView.findViewById(R.id.dir_item_image);
                holder.mDirCount = (TextView)convertView.findViewById(R.id.dir_item_count);
                holder.mDirName = (TextView)convertView.findViewById(R.id.dir_item_name);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            FolderBean bean = mData.get(position);
            holder.mImg.setImageResource(R.drawable.mis_default_error);
            Glide.with(getContext()).load(bean.getFirstImgPath()).into(holder.mImg);
            holder.mDirName.setText(bean.getName());
            holder.mDirCount.setText(bean.getCount()+"");

            return convertView;
        }

        private class  ViewHolder{
            ImageView mImg;
            TextView mDirName;
            TextView mDirCount;
        }
    }
}
