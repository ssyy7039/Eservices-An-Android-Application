package com.Guffran.e_services;

public class Userdata {
    private  String Name;
    private  String ContacNo;
    private  String Profession;
    private  String Location;
    private String Imageurl;
    private String Key;


    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public Userdata() {
    }

    public Userdata(String name, String contacNo, String profession, String location, String imageurl, String key) {
        Name = name;
        ContacNo = contacNo;
        Profession = profession;
        Location = location;
        Imageurl = imageurl;
        Key = key;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContacNo() {
        return ContacNo;
    }

    public void setContacNo(String contacNo) {
        ContacNo = contacNo;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
