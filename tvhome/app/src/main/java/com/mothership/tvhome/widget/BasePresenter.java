package com.mothership.tvhome.widget;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mothership.tvhome.R;
import com.tv.ui.metro.model.DisplayItem;

/**
 * Created by Shawn on 16/3/8.
 */
public class BasePresenter extends Presenter
{
    private static final String TAG = "BasePresenter";
    protected int mBaseWidth = 0;
    protected int mBaseHeight = 0;

    static public class VH extends ViewHolder
    {
        public ImageView mImg;
        public TextView mTitle;
        public TextView mSubTitle;

        public VH(View aView)
        {
            super(aView);
            mImg = (ImageView) aView.findViewById(R.id.di_img);
            mTitle = (TextView) aView.findViewById(R.id.di_title);
            mSubTitle = (TextView) aView.findViewById(R.id.di_subtitle);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent)
    {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View res = inf.inflate(R.layout.di_base_view, parent, false);
        VH vh = new VH(res);

//        ViewGroup.LayoutParams lpImg = vh.mImg.getLayoutParams();
//        lpImg.width = mBaseWidth;
//        lpImg.height = mBaseHeight;
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder aViewHolder, Object aItem)
    {
        VH vh = (VH) aViewHolder;
        DisplayItem di = (DisplayItem) aItem;
        vh.view.setId(di.ui_type.rows()+100000);
        if(vh.mSubTitle != null)
        {
            vh.mSubTitle.setText(di.sub_title);
        }
        if(vh.mTitle != null)
        {
            vh.mTitle.setText(di.title);
        }
        ViewGroup.LayoutParams lpImg = vh.mImg.getLayoutParams();
        if(mBaseHeight != 0 && mBaseWidth != 0)
        {
            lpImg.width = mBaseWidth;
            lpImg.height = mBaseHeight;
        }
        if(di.images != null && di.images.poster() != null)
        {
            Log.d(TAG, di.images.poster().url);
            String posterUrl = di.images.poster().url;
            if (posterUrl != null)
            {
                Glide.with(vh.mImg.getContext())
                        .load(posterUrl)
                        .thumbnail(0.1f)
                        .error(R.mipmap.ic_launcher)
                        .into(vh.mImg);
            }
        }

    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder)
    {
        VH vh = (VH)viewHolder;
        Glide.clear(vh.mImg);
    }

    public int getBaseWidth(){
        return mBaseWidth;
    };
    public int getBaseHeight(){
        return mBaseHeight;
    };
    public void setBaseSize(int w,int h){
        mBaseWidth = w;
        mBaseHeight = h;
    };
    public int getRealWidth(Context contect){
        return mBaseWidth;
    };

    public int getRealHeight(Context contect){
        return mBaseHeight+(int)contect.getResources().getDimension(R.dimen.item_text_bar_height);
    };

}
