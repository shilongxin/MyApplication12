package com.example.counterview.CounterView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by HP on 2018/1/10.
 */

public class MyLinearLayout extends ViewGroup {

    private int marginTop = 50;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private int totalHeight = 0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            totalHeight += childAt.getMeasuredHeight();

        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int mTop = 50;
        int mLeft = 50;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            Log.e("myMessage","mTop = "+mTop);
            childAt.layout(mLeft,mTop,childAt.getMeasuredWidth()+mLeft,childAt.getMeasuredHeight()+mTop);
            mTop+= childAt.getMeasuredHeight()+marginTop;

        }

    }

    private void init() {
        //
    }
}
