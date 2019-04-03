package com.example.handlerthreadexample;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodOrderAdapter extends RecyclerView.Adapter<FoodOrderHolder> {
    private List<FoodOrder> mDatasource;

    public FoodOrderAdapter(){
        mDatasource = new ArrayList<>();
    }
    @NonNull
    @Override
    public FoodOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_order_item, parent, false);
        return new FoodOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodOrderHolder holder, int position) {
        FoodOrder foodOrder = mDatasource.get(position);

        holder.orderName.setText(foodOrder.getFoodName());
        holder.orderPrice.setText(foodOrder.getFoodName());
    }

    @Override
    public int getItemCount() {
        if(mDatasource != null)
            return mDatasource.size();
        return 0;
    }

    public void onFoodOrderCreated(FoodOrder foodOrder) {
        int position = mDatasource.size();
        mDatasource.add(position, foodOrder);
        notifyItemInserted(position);
        Log.d(FoodOrderAdapter.class.getName(), "size: " + position);
    }
}
