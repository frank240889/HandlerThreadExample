package com.example.handlerthreadexample;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodOrderHolder extends RecyclerView.ViewHolder {
    public TextView orderName;
    public TextView orderPrice;
    public FoodOrderHolder(@NonNull View itemView) {
        super(itemView);
        orderName = itemView.findViewById(R.id.food_order_name);
        orderPrice = itemView.findViewById(R.id.food_order_price);
    }
}
