package com.example.handlerthreadexample;

public class FoodOrder {
    private String mFoodName;
    private float mFoodPrice;
    private String mSideOrder;

    public FoodOrder(String foodName, float price){
        mFoodName = foodName;
        mFoodPrice = price;
    }
    public String getFoodName() {
        return mFoodName;
    }

    public void setFoodname(String foodname) {
        mFoodName = foodname;
    }

    public float getFoodPrice() {
        return mFoodPrice;
    }

    public void setFoodPrice(float foodPrice) {
        this.mFoodPrice = foodPrice;
    }

    public String getSideOrder() {
        return mSideOrder;
    }

    public void setSideOrder(String sideOrder) {
        this.mSideOrder = sideOrder;
    }
}
