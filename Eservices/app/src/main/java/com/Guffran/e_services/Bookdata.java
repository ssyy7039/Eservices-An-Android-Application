package com.Guffran.e_services;

public class Bookdata {
    private String BookingID;
    private String Vendorname;
    private String VendorProfession;
    private String VendorContact;
    private String VendorAddress;
    private String VendorImageurl;
    private String Username;
    private String Usercontact;
    private String Useraddress;
    private String Date1;
    private String Date2;
    private String Key;
    private String Time;
    private String UID;
    private String status;


    public Bookdata() {
    }

    public String getBookingID() {
        return BookingID;
    }

    public void setBookingID(String bookingID) {
        BookingID = bookingID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bookdata(String bookingID, String vendorname, String vendorProfession, String vendorContact, String vendorAddress, String vendorImageurl, String username, String usercontact, String useraddress, String date1, String date2, String key, String time, String UID, String status) {
        BookingID = bookingID;
        Vendorname = vendorname;
        VendorProfession = vendorProfession;
        VendorContact = vendorContact;
        VendorAddress = vendorAddress;
        VendorImageurl = vendorImageurl;
        Username = username;
        Usercontact = usercontact;
        Useraddress = useraddress;
        Date1 = date1;
        Date2 = date2;
        Key = key;
        Time = time;
        this.UID = UID;
        this.status = status;
    }

    public String getVendorname() {
        return Vendorname;
    }

    public void setVendorname(String vendorname) {
        Vendorname = vendorname;
    }

    public String getVendorProfession() {
        return VendorProfession;
    }

    public void setVendorProfession(String vendorProfession) {
        VendorProfession = vendorProfession;
    }

    public String getVendorContact() {
        return VendorContact;
    }

    public void setVendorContact(String vendorContact) {
        VendorContact = vendorContact;
    }

    public String getVendorAddress() {
        return VendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        VendorAddress = vendorAddress;
    }

    public String getVendorImageurl() {
        return VendorImageurl;
    }

    public void setVendorImageurl(String vendorImageurl) {
        VendorImageurl = vendorImageurl;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUsercontact() {
        return Usercontact;
    }

    public void setUsercontact(String usercontact) {
        Usercontact = usercontact;
    }

    public String getUseraddress() {
        return Useraddress;
    }

    public void setUseraddress(String useraddress) {
        Useraddress = useraddress;
    }

    public String getDate1() {
        return Date1;
    }

    public void setDate1(String date1) {
        Date1 = date1;
    }

    public String getDate2() {
        return Date2;
    }

    public void setDate2(String date2) {
        Date2 = date2;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
