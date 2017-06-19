package com.milai.lll_teacher.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.milai.lll_teacher.R;
import com.milai.lll_teacher.custom.util.DensityUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 * <p>
 * 功能介绍：标签选择控件，一行4个，单选，可设置默认值
 */

public class CustomLabelSelectView extends ViewGroup {

    private int SPACE_HORIZANTAL ;
    private int SPACR_VERTICAL ;

    private List<String> data;
    private Context context;

    private int selectIndex = -1;        //标识当前选中的index

    public CustomLabelSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        SPACR_VERTICAL = DensityUtils.dip2px(context, 10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);        //测量高度
        int totalHeight = getPaddingTop() + getPaddingBottom();         //计算的总高度
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++) {
            //每四个转行
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);   //测量子view
            if ( i % 4 == 0) {
                int childHeight = child.getMeasuredHeight();
                totalHeight += SPACR_VERTICAL + childHeight;
            }

        }
        //初始化选中项为第一项
        selectIndex = 0;
        SPACE_HORIZANTAL = (widthSize - getChildAt(0).getMeasuredWidth() * 4) / 3;
        setMeasuredDimension(widthSize, heightMode == MeasureSpec.EXACTLY ? heightSize : totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int widthLeft = 0;  //view的左端
        int heightTop = 0;  //view的右端
        for(int i = 0; i<getChildCount(); i++) {
            final int item = i;
            View child = getChildAt(i);
            if (i != 0 && i % 4 == 0) {
                //换行
                widthLeft = 0;  //左端点归0
                heightTop += child.getMeasuredHeight() + SPACR_VERTICAL;    //上端点递增
            }
            int right = widthLeft + child.getMeasuredWidth();
            int bottom = heightTop + child.getMeasuredHeight();
            child.layout(widthLeft, heightTop, right, bottom);
            widthLeft += child.getMeasuredWidth() + SPACE_HORIZANTAL;   //修改左端点

            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelect(item);
                }
            });
        }
    }

    //添加子view
    public void setData(List<String> strings) {
        this.data = strings;
        for (int i=0;i<data.size();i++) {
            TextView childView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_lable_rect, this, false);
            childView.setText(data.get(i));
            if (i == 0) {
                childView.setBackgroundResource(R.drawable.shape_bt_radiu2_orange);
                childView.setTextColor(0xffffffff);
            }
            this.addView(childView);
        }
        requestLayout();
    }

    //修改当前选择项
    public void setSelect(int index) {
        if (getChildCount() > 0) {
            //取出之前选择项
            TextView selectBefore = (TextView) getChildAt(selectIndex);
            selectBefore.setTextColor(0xff8a8f97);
            selectBefore.setBackgroundResource(R.drawable.shape_bt_radiu2_border_grey);
            //修改当前选择项
            selectIndex = index;
            TextView selectNew = (TextView) getChildAt(selectIndex);
            selectNew.setBackgroundResource(R.drawable.shape_bt_radiu2_orange);
            selectNew.setTextColor(0xffffffff);
        }
    }

    //获取当前选择项
    public int getSelectIndex() {
        if (getChildCount() < 1) {
            return -1;
        } else {
            return selectIndex;
        }
    }
}
