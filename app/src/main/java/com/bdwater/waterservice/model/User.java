package com.bdwater.waterservice.model;

import android.content.Intent;

/**
 * Created by hegang on 2018/5/3.
 */

public class User {
    public boolean isLogon = false;
    public String tel = "18003328885";
    public Customer currentCustomer;
    public Customer[] customers;
    public static User instance = new User();
    private User() {

    }

    public static class Customer {
        public Long customerNo;
        public String customerName;
    }
}
