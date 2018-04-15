package com.bdwater.waterservice.main;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.StringRes;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bdwater.waterservice.utils.IconicsUtil;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.typeface.IIcon;
import com.bdwater.waterservice.R;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;

/**
 * Created by hegang on 18/4/14.
 */

public class BottomNavigationCollection {
    public static final String HOME = "home";
    public static final String QUERY = "query";
    public static final String REPORT = "report";
    public static final String SITE = "site";
    public static final String NOTIFICATION = "notification";

    private Context context;
    private ArrayList<BottomNavigationItem> items = new ArrayList<BottomNavigationItem>();
    public BottomNavigationCollection(Context context) {
        this.context = context;
        addItem(HOME, R.string.menu_home, CommunityMaterial.Icon.cmd_home_outline);
        addItem(QUERY, R.string.menu_query, CommunityMaterial.Icon.cmd_account_search);
        addItem(REPORT, R.string.menu_report, CommunityMaterial.Icon.cmd_alert_outline);
        addItem(SITE, R.string.menu_site, CommunityMaterial.Icon.cmd_map_marker_outline);
        addItem(NOTIFICATION, R.string.menu_notification, CommunityMaterial.Icon.cmd_comment_text_outline);
    }
    public void addItem(String name, @StringRes Integer id, IIcon icon) {
        Resources resources = this.context.getResources();
        items.add(BottomNavigationItem.newInstance(name, resources.getString(id), icon));
    }
    public ArrayList<BottomNavigationItem> getItems() {
        return items;
    }
    public void setupWithBottomNavigation(AHBottomNavigation navigation) {
        for(BottomNavigationItem item : items) {
            AHBottomNavigationItem ahItem = new AHBottomNavigationItem(item.title,
                    IconicsUtil.getNormalIcon(this.context, item.icon),
                    Color.BLACK);
            navigation.addItem(ahItem);
        }
    }

    static class BottomNavigationItem {
        public String name;
        public String title;
        public IIcon icon;
        public static BottomNavigationItem newInstance(String name, String title, IIcon icon) {
            BottomNavigationItem item = new BottomNavigationItem();
            item.name = name;
            item.title = title;
            item.icon = icon;
            return item;
        }
    }
}
