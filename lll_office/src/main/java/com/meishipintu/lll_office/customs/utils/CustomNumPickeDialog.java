package com.meishipintu.lll_office.customs.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.NumberPicker;

import com.meishipintu.lll_office.R;

import java.lang.reflect.Field;


/**
 * Created by Administrator on 2017/3/21.
 * <p>
 * 主要功能：
 */

public class CustomNumPickeDialog extends AlertDialog {

    private String[] show;
    private OnOkClickListener okListener;

    public CustomNumPickeDialog(Context context, @StyleRes int themeResId
            , String[] show,OnOkClickListener listener) {
        super(context, themeResId);
        this.show = show;
        this.okListener = listener;
    }

    public CustomNumPickeDialog(@NonNull Context context) {
        super(context);
    }

    public CustomNumPickeDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_num_picker);
        final NumberPicker np = (NumberPicker) findViewById(R.id.np);
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okListener.onOkClick(np.getValue());
            }
        });
        np.setDisplayedValues(show);
        np.setMinValue(0);
        np.setMaxValue(show.length-1);
        setNumberPickerDividerColor(np);
    }

    public void showAnim() {
        this.getWindow().getDecorView().startAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.dialog_in_anim));
        this.show();
    }

    public void dismissAnim() {
        this.getWindow().getDecorView().startAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.dialog_out_anim));
        this.dismiss();
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

    public interface OnOkClickListener {

        void onOkClick(int vlueChoose);
    }
}
