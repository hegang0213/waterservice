package com.bdwater.waterservice.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

import com.bdwater.waterservice.R;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

/**
 * Created by hegang on 17/11/15.
 */

public class IconicsUtil {
    public static Drawable getSmallIcon(Context context, IIcon icon) {
        return getIcon(context, icon, (int)context.getResources().getDimension(R.dimen.icon_size));
    }
    public static Drawable getNormalIcon(Context context, IIcon icon) {
        return getIcon(context, icon, (int)context.getResources().getDimension(R.dimen.icon_size_normal), 2);
    }
    public static Drawable getBigIcon(Context context, IIcon icon) {
        return getIcon(context, icon, (int)context.getResources().getDimension(R.dimen.icon_size_big));
    }
    public static Drawable getIcon(Context context, IIcon icon, int sizeDp) {
        return getIcon(context, icon, sizeDp, 0, -9999);
    }
    public static Drawable getIcon(Context context, IIcon icon, int sizeDp, int paddingDp) {
        return getIcon(context, icon, sizeDp, paddingDp, -9999);
    }
    public static Drawable getIcon(Context context, IIcon icon, int sizeDp, int paddingDp, int color) {
        IconicsDrawable drawable = new IconicsDrawable(context, icon);
        if(sizeDp != -1)
            drawable = drawable.sizeDp(sizeDp);
        drawable = drawable.paddingDp(paddingDp);
        if(color != -9999)
            drawable = drawable.color(color);
        return drawable;
    }
    public static Bitmap getBitmap(Context context, IIcon icon, int sizeDp, @ColorInt int color) {
        Drawable drawable = getNormalIcon(context, icon);
        ((IconicsDrawable)drawable).color(color);
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        Bitmap.Config.ARGB_8888);
        bitmap.setHasAlpha(true);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) sizeDp) / width;
        float scaleHeight = ((float) sizeDp) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}

