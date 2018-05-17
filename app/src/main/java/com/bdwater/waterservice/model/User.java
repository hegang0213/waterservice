package com.bdwater.waterservice.model;

import android.content.Context;
import android.content.Intent;

import com.bdwater.waterservice.utils.StorageUtil;
import com.google.gson.annotations.Expose;

/**
 * Created by hegang on 2018/5/3.
 */

public class User {
    public boolean isLogon = false;
    public String tel = null;
    @Expose(deserialize = false, serialize = false)
    public Customer currentCustomer;
    //@Expose(deserialize = false, serialize = false)
    public Customer[] customers;
    @Expose(deserialize = false, serialize = false)
    public static User instance = new User();
    private User() {

    }
    public boolean isEmpty() {
        return (tel == null);
    }

    public static class Customer {
        public Long customerNo;
        public String customerName;
    }

    public boolean isCurrent(User.Customer customer) {
        if(customer == null) return false;
        return this.currentCustomer.customerNo == customer.customerNo;
    }
    public boolean isCurrent(Long no) {
        return this.currentCustomer.customerNo == no;
    }
    public void setCurrentCustomer(long customerNo) {
        for(int i = 0; i < this.customers.length; i++) {
            if(this.customers[i].customerNo == customerNo) {
                this.currentCustomer = this.customers[i];
                break;
            }
        }
    }
    public void save(Context context) {
        StorageUtil.put(context, StorageUtil.USER, instance);
    }
    public void read(Context context) {
        User user = StorageUtil.get(context, StorageUtil.USER, User.class);
        if(user != null) {
            instance = user;
            if(instance.customers != null && instance.customers.length > 0)
                instance.currentCustomer = instance.customers[0];
        }
    }
}
