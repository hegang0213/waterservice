package com.bdwater.waterservice.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;

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
    public static void showAlertDialog(Context context, String title, String message) {
        android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }
}
