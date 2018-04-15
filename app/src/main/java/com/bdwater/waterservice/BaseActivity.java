package com.bdwater.waterservice;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import com.mikepenz.iconics.context.IconicsContextWrapper;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by hegang on 18/4/14.
 */

public class BaseActivity extends SwipeBackActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
