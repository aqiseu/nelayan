package com.learn.dimdimasdim.bidfishnelayan.data.model;

/**
 * Created by dimdimasdim on 16/02/2018.
 */

public class BidFish {

    private String bidFishName;

    private String location;

    private String urlImgBid;

    private String timeCatchingFish;

    private String priceBid;

    public BidFish(String bidFishName, String location, String priceBid) {
        this.bidFishName = bidFishName;
        this.location = location;
        this.priceBid = priceBid;
    }

    public String getBidFishName() {
        return bidFishName;
    }

    public void setBidFishName(String bidFishName) {
        this.bidFishName = bidFishName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrlImgBid() {
        return urlImgBid;
    }

    public void setUrlImgBid(String urlImgBid) {
        this.urlImgBid = urlImgBid;
    }

    public String getTimeCatchingFish() {
        return timeCatchingFish;
    }

    public void setTimeCatchingFish(String timeCatchingFish) {
        this.timeCatchingFish = timeCatchingFish;
    }

    public String getPriceBid() {
        return priceBid;
    }

    public void setPriceBid(String priceBid) {
        this.priceBid = priceBid;
    }
}
