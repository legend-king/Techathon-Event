package com.example.techathon.Models;

public class TrackOrder {
    String status, acceptedBy, orderType, mobile;
    int id;

    public TrackOrder(int id, String status, String acceptedBy, String orderType, String mobile) {
        this.id=id;
        this.status = status;
        this.acceptedBy = acceptedBy;
        this.orderType = orderType;
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
