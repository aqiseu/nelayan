package com.learn.dimdimasdim.bidfishnelayan.data.model;

/**
 * Created by amaliaqis on 24/02/2018.
 */

public class Pricey {
    String bidderName;
    String bidderPrice;
    String bidderAddress;
    String bidderGender;


    String bidderDeposit;
    String bidderEmail;

    public Pricey(){

    }

    public Pricey(String bidderName, String bidderPrice){

        this.bidderName = bidderName;
        this.bidderPrice = bidderPrice;

    }

    public Pricey(String bidderName, String bidderGender, String bidderAddress, String bidderDeposit, String bidderEmail){

        this.bidderName = bidderName;
        this.bidderGender = bidderGender;
        this.bidderAddress = bidderAddress;
        this.bidderDeposit = bidderDeposit;
        this.bidderEmail = bidderEmail;
    }



    public String getBidderAddress() {
        return bidderAddress;
    }

    public void setBidderAddress(String bidderAddress) {
        this.bidderAddress = bidderAddress;
    }

    public String getBidderGender() {
        return bidderGender;
    }

    public void setBidderGender(String bidderGender) {
        this.bidderGender = bidderGender;
    }

    public String getBidderDeposit() {
        return bidderDeposit;
    }

    public void setBidderDeposit(String bidderDeposit) {
        this.bidderDeposit = bidderDeposit;
    }

    public String getBidderEmail() {
        return bidderEmail;
    }

    public void setBidderEmail(String bidderEmail) {
        this.bidderEmail = bidderEmail;
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public String getBidderPrice() {
        return bidderPrice;
    }

    public void setBidderPrice(String bidderPrice) {
        this.bidderPrice = bidderPrice;
    }
}
