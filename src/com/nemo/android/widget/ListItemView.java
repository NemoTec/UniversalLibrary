package com.nemo.android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nemo.ul.R;


public class ListItemView extends RelativeLayout {
    private static String TAG = "ListItemView";
    private ImageView mIcon;
    private TextView mTitle;
    private ImageView mDotIcon;
    private boolean mIsDotVisible = false;


    public ListItemView(Context context) {
        super(context);
        mContext = context;
        initView(mContext);
    }

    public ListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(mContext);
    }

    public ListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initView(mContext);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_list_item, this);
        mIcon = (ImageView) findViewById(android.R.id.icon);
        mTitle = (TextView) findViewById(android.R.id.title);
        mDotIcon = (ImageView)findViewById(R.id.new_dot);
    }
    
    public void setIcon(Drawable d) {
        if (mIcon != null) {
            mIcon.setImageDrawable(d);
        }
    } 

    public void setTitle(String titleString) {
        if (mTitle != null) {
            mTitle.setText(titleString);
        }
    }

    public void setDotVisible(boolean isVisible) {
        mIsDotVisible = isVisible;
        if (mDotIcon != null) {
            mDotIcon.setVisibility(mIsDotVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public boolean isDotVisible() {
        return mIsDotVisible;
    }
}
