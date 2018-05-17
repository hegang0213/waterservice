package com.bdwater.waterservice.model;

import android.content.Intent;

import com.google.gson.annotations.Expose;

/**
 * Created by hegang on 2018/5/3.
 */

public class User {
    public boolean isLogon = false;
    public String tel = "18003328885";
    public Customer currentCustomer;
    public Customer[] customers;
    @Expose(deserialize = false, serialize = false)
    public static User instance = new User();
    private User() {

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
}
