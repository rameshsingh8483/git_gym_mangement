package com.ubxty.gymmanagement.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {


    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "totalfees")
    private String totalfees;

    @ColumnInfo(name = "pendingfees")
    private String pendingfees;

    @ColumnInfo(name = "payfees")
    private String payfees;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "joindate")
    private String joindate;


    @ColumnInfo(name = "join_month")
    private String join_month;


    @ColumnInfo(name = "payFees_status")
    private String payFees_status;



    @ColumnInfo(name = "payfull_fees_date")
    private String payfull_fees_date;


    public String getPayfull_fees_date() {
        return payfull_fees_date;
    }

    public void setPayfull_fees_date(String payfull_fees_date) {
        this.payfull_fees_date = payfull_fees_date;
    }

    public String getPayFees_status() {
        return payFees_status;
    }

    public void setPayFees_status(String payFees_status) {
        this.payFees_status = payFees_status;
    }

    public String getJoin_month() {
        return join_month;
    }

    public void setJoin_month(String join_month) {
        this.join_month = join_month;
    }

    public byte[] getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(byte[] profile_image) {
        this.profile_image = profile_image;
    }

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] profile_image;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalfees() {
        return totalfees;
    }

    public void setTotalfees(String totalfees) {
        this.totalfees = totalfees;
    }

    public String getPendingfees() {
        return pendingfees;
    }

    public void setPendingfees(String pendingfees) {
        this.pendingfees = pendingfees;
    }

    public String getPayfees() {
        return payfees;
    }

    public void setPayfees(String payfees) {
        this.payfees = payfees;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }
}