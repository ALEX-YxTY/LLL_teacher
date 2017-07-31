package com.meishipintu.lll_office.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meishipintu.lll_office.R;
import com.meishipintu.lll_office.customs.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/19.
 * <p>
 * 功能介绍：标签选择控件，一行个数不定，自动换行，只能单选，居左排列
 */

public class CustomLabelSingleSelectCenterView extends ViewGroup {

    private int SPACE_HORIZANTAL;           //水平item间距
    private int SPACE_VERTICAL ;            //竖直行间距

    private boolean isCenter;               //标识是否居中排列

    private List<String> data;
    private Context context;
    private int select;
    private CustomLabelSelectListener mListener;    //点击监听

    public CustomLabelSingleSelectCenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        isCenter = false;
        select = -1;
        SPACE_HORIZANTAL = DensityUtils.dip2px(context, 8);
        SPACE_VERTICAL = DensityUtils.dip2px(context, 10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);        //测量高度
        int totalHeight = getPaddingTop() + getPaddingBottom();         //计算的总高度
        int childCount = getChildCount();
        int lineWidth = getPaddingLeft() + getPaddingRight();           //每行的当前宽度
        for(int i=0;i<childCount;i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);   //测量子view
            int itemWidth = child.getMeasuredWidth();
            if (i == 0) {
                lineWidth += itemWidth;
                totalHeight += getChildAt(0).getMeasuredHeight(); //首元素
            }
            if (lineWidth + itemWidth + SPACE_HORIZANTAL > widthSize) {
                //超过行总宽度，加在下一行
                lineWidth = getPaddingLeft() + getPaddingRight() + itemWidth ;    //重置下一行宽度
                totalHeight += (SPACE_VERTICAL + child.getMeasuredHeight());  //高度递增
            } else {
                //加在这行
                lineWidth += (itemWidth+SPACE_HORIZANTAL);
            }
        }
        setMeasuredDimension(widthSize, heightMode == MeasureSpec.EXACTLY ? heightSize : totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        List<TextView> lineList = new ArrayList<>();      //包含每一行的对象list
        int totalWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight(); //可用总宽
        int lineWidthNow = 0;         //当前行的总宽度
        int top = 0;                  //标记当前top位置
        for(int i = 0; i<getChildCount(); i++) {
            final int item = i;
            final TextView child = (TextView) getChildAt(i);
            int itemWidth = (i == 0 ? child.getMeasuredWidth() : child.getMeasuredWidth() + SPACE_HORIZANTAL);
            if (lineWidthNow + itemWidth <= totalWidth) {
                //不超总宽度
                lineWidthNow += itemWidth;
                lineList.add(child);
            } else {
                //超过一行，先布置上一行
                int left = isCenter ? ((totalWidth - lineWidthNow) / 2 + getPaddingLeft()) : getPaddingLeft(); //计算该行左边距
                for (TextView itemText : lineList) {
                    itemText.layout(left, top, left + itemText.getMeasuredWidth(), top + itemText.getMeasuredHeight());
                    left += (itemText.getMeasuredWidth() + SPACE_HORIZANTAL);   //更新左起点
                }
                //更新状态值
                lineWidthNow = itemWidth;   //添加到下一行
                top += child.getMeasuredHeight() + SPACE_VERTICAL;
                lineList.clear();
                lineList.add(child);
            }
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                            setSelect(item, false);
                            mListener.select(item);
                    }
                }
            });
        }
        //布置最后一行
        int left = isCenter ? ((totalWidth - lineWidthNow) / 2 + getPaddingLeft()) : getPaddingLeft(); //计算该行左边距
        for (TextView itemText : lineList) {
            itemText.layout(left, top, left + itemText.getMeasuredWidth(), top + itemText.getMeasuredHeight());
            left += (itemText.getMeasuredWidth() + SPACE_HORIZANTAL);   //更新左起点
        }
    }

    //添加监听
    public void setListener(CustomLabelSelectListener listener) {
        this.mListener = listener;
    }

    //添加子view
    public void setData(List<String> strings) {
        this.data = strings;
        for (int i=0;i<data.size();i++) {
            TextView childView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_lable, this, false);
            childView.setText(data.get(i));
            this.addView(childView);
        }
        requestLayout();
    }

    //修改当前选择项
    public void setSelect(int index, boolean isSelect) {
        if (getChildCount() > 0) {
            if (select >= 0) {
                TextView lastSelect = (TextView) getChildAt(select);
                lastSelect.setTextColor(0xffD4C0B7);
                lastSelect.setBackgroundResource(R.drawable.shape_tv_lable_unselect);
            }
            TextView newSelect = (TextView) getChildAt(index);
            newSelect.setTextColor(0xffff7640);
            newSelect.setBackgroundResource(R.drawable.shape_tv_lable);
            select = index;
        }
    }

    public void clearSelect() {
        if (select >= 0) {
            TextView lastSelect = (TextView) getChildAt(select);
            lastSelect.setTextColor(0xffD4C0B7);
            lastSelect.setBackgroundResource(R.drawable.shape_tv_lable_unselect);
        }
        select = -1;
    }
}
