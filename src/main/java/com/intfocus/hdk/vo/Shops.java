package com.intfocus.hdk.vo;

public class Shops {
    private Integer id;

    private String shopId;

    private String shopPosition;

    private String shopFloor;

    private String shopMerName;

    private String shopMerStation;

    private String shopType;

    private String shopSecType;

    private String shopName;

    private String proId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public String getShopPosition() {
        return shopPosition;
    }

    public void setShopPosition(String shopPosition) {
        this.shopPosition = shopPosition == null ? null : shopPosition.trim();
    }

    public String getShopFloor() {
        return shopFloor;
    }

    public void setShopFloor(String shopFloor) {
        this.shopFloor = shopFloor == null ? null : shopFloor.trim();
    }

    public String getShopMerName() {
        return shopMerName;
    }

    public void setShopMerName(String shopMerName) {
        this.shopMerName = shopMerName == null ? null : shopMerName.trim();
    }

    public String getShopMerStation() {
        return shopMerStation;
    }

    public void setShopMerStation(String shopMerStation) {
        this.shopMerStation = shopMerStation == null ? null : shopMerStation.trim();
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType == null ? null : shopType.trim();
    }

    public String getShopSecType() {
        return shopSecType;
    }

    public void setShopSecType(String shopSecType) {
        this.shopSecType = shopSecType == null ? null : shopSecType.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId == null ? null : proId.trim();
    }
}