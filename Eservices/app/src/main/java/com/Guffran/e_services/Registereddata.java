package com.Guffran.e_services;

public class Registereddata {
    private String Name;
    private String Contact;
    private String Email;
    private String UID;

    public Registereddata() {
    }

    public Registereddata(String name, String contact, String email, String UID) {
        Name = name;
        Contact = contact;
        Email = email;
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
