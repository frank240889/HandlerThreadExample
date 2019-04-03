package com.example.handlerthreadexample;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.util.Random;

public class OrderHandlerThread extends HandlerThread {
    private Handler mUiHandler;
    private Handler mBackgroundHandler;
    public OrderHandlerThread(Handler uiHandler) {
        super(OrderHandlerThread.class.getName());
        mUiHandler = uiHandler;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mBackgroundHandler = getHandler(getLooper());
    }

    private Float convertConcurrency(float priceInDollars) {
        return priceInDollars * 20f;
    }

    private String attachSideOrder() {
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

    private Handler getHandler(Looper looper) {
        return new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                FoodOrder foodOrder = (FoodOrder) msg.obj;
                foodOrder.setFoodPrice(convertConcurrency(foodOrder.getFoodPrice()));
                foodOrder.setSideOrder(attachSideOrder());
                Message processedMessage = new Message();
                processedMessage.obj = foodOrder;
                mUiHandler.sendMessage(processedMessage);
            }
        };
    }

    public void sendOrder(FoodOrder foodOrder) {
        Message message = new Message();
        message.obj = foodOrder;
        mBackgroundHandler.sendMessage(message);
    }
}
