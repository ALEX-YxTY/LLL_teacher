package com.meishipintu.lll_lib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 * <p>
 * 功能介绍：
 */

public class CustomLabelSelectView extends ViewGroup {

    private int SPACE_HORIZANTAL = 30;
    private int SPACR_VERTICAL = 30;

    private List<String> data;
    private Context context;


    public CustomLabelSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public void setData(List<String> strings) {
        this.data = strings;
        for (String string : data) {
//            LayoutInflater.from(context).inflate(R.layout.item_lable_rect);
        }
        requestLayout();
    }
}
