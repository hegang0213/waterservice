package com.bdwater.waterservice.component;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bdwater.waterservice.R;

import butterknife.ButterKnife;

/**
 * Created by hegang on 2018/5/4.
 */

public class BottomDialog extends Dialog {
    private View view;
    protected View getRootView() {
        return view;
    }

    public BottomDialog(@NonNull Context context, @LayoutRes int layoutId) {
        super(context, R.style.BottomDialog);
        view = getLayoutInflater().inflate(layoutId, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(view);//这行一定要写在前面
        setCancelable(true);//点击外部不可dismiss
        setCanceledOnTouchOutside(false);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }
}
