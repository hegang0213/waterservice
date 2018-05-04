package com.bdwater.waterservice.model;

/**
 * Created by hegang on 2018/5/3.
 */

public class User {
    public boolean isLogon = false;
    public String tel = "18003328885";
    public Integer customerNo = 30214;
    public static User instance = new User();
}
