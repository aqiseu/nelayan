package com.learn.dimdimasdim.bidfishnelayan.data.model;

/**
 * Created by lenovo on 18/02/2018.
 */

public class Auctioneer {

    String id;
    String email;
    String password;
    String name;
    String photoUrl;
    String address;
    String phone;

    String gender;

    public Auctioneer (){

    }

    public Auctioneer(String id, String email, String name, String gender, String address, String phone, String password, String photoUrl){
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.photoUrl = photoUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }


}
