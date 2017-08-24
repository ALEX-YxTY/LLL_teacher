package com.meishipintu.lll_office.customs.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.NumberPicker;

import com.meishipintu.lll_office.R;

import java.lang.reflect.Field;


/**
 * Created by Administrator on 2017/3/21.
 * <p>
 * 主要功能：
 */

public class CustomNumPickeDialog2 extends AlertDialog {

    private String[] showFirst;
    private String[] showSecond;
    private OnOk2ClickListener okListener;

    public CustomNumPickeDialog2(Context context, @StyleRes int themeResId
            , String[] showFirst, String[] showSecond, OnOk2ClickListener listener) {
        super(context, themeResId);
        this.showFirst = showFirst;
        this.showSecond = showSecond;
        this.okListener = listener;
    }

    public CustomNumPickeDialog2(@NonNull Context context) {
        super(context);
    }

    public CustomNumPickeDialog2(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_two_num_picker);
        final NumberPicker npFirst = (NumberPicker) findViewById(R.id.np_first);
        final NumberPicker npSecond = (NumberPicker) findViewById(R.id.np_second);

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okListener.onOkClick(npFirst.getValue(),npSecond.getValue());
            }
        });
        npFirst.setDisplayedValues(showFirst);
        npFirst.setMinValue(0);
        npFirst.setMaxValue(showFirst.length-1);
        npSecond.setDisplayedValues(showSecond);
        npSecond.setMinValue(0);
        npSecond.setMaxValue(0);
        npFirst.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                switch (newVal) {
                    case 0:
                        npSecond.setMaxValue(0);
                        break;
                    case 1:
                        npSecond.setMaxValue(showSecond.length-1);
                        break;
                    case 2:
                    case 3:
                        npSecond.setMaxValue(3);
                        break;
                }
            }
        });
        setNumberPickerDividerColor(npFirst);
        setNumberPickerDividerColor(npSecond);
    }

    /**
     * 自定义滚动框分隔线颜色
     */
    private void setNumberPickerDividerColor(NumberPicker number) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(number, new ColorDrawable(ContextCompat.getColor(getContext(), R.color.themeOrange)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    public interface OnOk2ClickListener {

        void onOkClick(int vlueFirst,int valueSecond);
    }
}
