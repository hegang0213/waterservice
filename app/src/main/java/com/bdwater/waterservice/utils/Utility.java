package com.bdwater.waterservice.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.view.View;

import com.bdwater.waterservice.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hegang on 2018/5/3.
 */

public class Utility {
    /**
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvailiable(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();

        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
    private static AlertDialog.Builder buildAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }
    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = buildAlertDialog(context);
        builder = builder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }
    public static void showRetryAlertDialog(Context context, String title, String message, String buttonText, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = buildAlertDialog(context);
        builder = builder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonText, listener);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }
    public static void showAlertDialog(View view, final Context context, final String title, final String message) {
        view.post(new Runnable() {
            @Override
            public void run() {
                showAlertDialog(context, title, message);
            }
        });
    }
}
