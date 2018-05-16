package com.bdwater.waterservice.model;

/**
 * Created by hegang on 2018/5/14.
 */

public class Customer {
    public long customerNo;         // 用户编号
    public String customerName;     //	用户名
    public String customerID;       //	用户ID
    public String address;          //	地址
    public double deposit;          //	当前预存
    public String waterPrice;       //	当前水价
    public Integer billTotalWaterQuantity;  // 总欠水量
    public double billTotalCurrentPay;      // 总欠费
}
