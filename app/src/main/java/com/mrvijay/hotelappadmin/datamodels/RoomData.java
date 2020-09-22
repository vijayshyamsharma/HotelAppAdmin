package com.mrvijay.hotelappadmin.datamodels;

public class RoomData {


    String booked,caproom,imgroom,nameroom,priceroom,typeroom,checkindate,checkoutdate,posteddate;

    public RoomData() {
    }

    public RoomData(String booked, String caproom, String imgroom, String nameroom, String priceroom, String typeroom, String checkindate, String checkoutdate, String posteddate) {
        this.booked = booked;
        this.caproom = caproom;
        this.imgroom = imgroom;
        this.nameroom = nameroom;
        this.priceroom = priceroom;
        this.typeroom = typeroom;
        this.checkindate = checkindate;
        this.checkoutdate = checkoutdate;
        this.posteddate = posteddate;
    }

    public String getCheckindate() {
        return checkindate;
    }

    public void setCheckindate(String checkindate) {
        this.checkindate = checkindate;
    }

    public String getCheckoutdate() {
        return checkoutdate;
    }

    public void setCheckoutdate(String checkoutdate) {
        this.checkoutdate = checkoutdate;
    }

    public String getPosteddate() {
        return posteddate;
    }

    public void setPosteddate(String posteddate) {
        this.posteddate = posteddate;
    }

    public String getBooked() {
        return booked;
    }

    public void setBooked(String booked) {
        this.booked = booked;
    }

    public String getCaproom() {
        return caproom;
    }

    public void setCaproom(String caproom) {
        this.caproom = caproom;
    }

    public String getImgroom() {
        return imgroom;
    }

    public void setImgroom(String imgroom) {
        this.imgroom = imgroom;
    }

    public String getNameroom() {
        return nameroom;
    }

    public void setNameroom(String nameroom) {
        this.nameroom = nameroom;
    }

    public String getPriceroom() {
        return priceroom;
    }

    public void setPriceroom(String priceroom) {
        this.priceroom = priceroom;
    }

    public String getTyperoom() {
        return typeroom;
    }

    public void setTyperoom(String typeroom) {
        this.typeroom = typeroom;
    }
}
