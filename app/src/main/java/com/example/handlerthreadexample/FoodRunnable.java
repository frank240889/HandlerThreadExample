package com.example.handlerthreadexample;

import java.util.Random;

public class FoodRunnable implements Runnable {
    private OrderHandlerThread mOrderHandlerThread;
    private int mCount;
    private int mMaxOrderSize;
    private boolean mAlive;
    public FoodRunnable(OrderHandlerThread orderHandlerThread) {
        mOrderHandlerThread = orderHandlerThread;
    }

    @Override
    public void run() {
        mAlive = true;

        while (mAlive && mCount < mMaxOrderSize) {
            mCount++;

            String foodName = getRandomOrderName();
            float price = getRandomPrice();
            mOrderHandlerThread.sendOrder(new FoodOrder(foodName, price));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private float getRandomPrice() {
        return new Random().nextFloat();
    }

    private String getRandomOrderName() {
        int random = new Random().nextInt(3);

        switch (random) {
            case 0:
                return "Chips";
            case 1:
                return "Salad";
            default:
                return "Nachos";
        }
    }

    public void setMaxOrderSize(int maxOrderSize) {
        mMaxOrderSize = maxOrderSize;
    }
}
