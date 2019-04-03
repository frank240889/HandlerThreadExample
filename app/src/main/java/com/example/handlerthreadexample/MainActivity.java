package com.example.handlerthreadexample;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements Handler.Callback {
    private OrderHandlerThread mOrderHandlerThread;
    private FoodRunnable mFoodRunnable;
    private Handler mUiHandler;
    private FoodOrderAdapter mFoodOrderAdapter;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mUiHandler = new Handler(this);
        mOrderHandlerThread = new OrderHandlerThread(mUiHandler);
        mOrderHandlerThread.start();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Startting orders...", Snackbar.LENGTH_LONG)
                        .setAction("Ok", null).show();
                mFoodRunnable = new FoodRunnable(mOrderHandlerThread);
                mFoodRunnable.setMaxOrderSize(20);
                mFoodRunnable.run();
            }
        });
        mFoodOrderAdapter = new FoodOrderAdapter();
        mRecyclerView = findViewById(R.id.food_order_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFoodOrderAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mOrderHandlerThread.quit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean handleMessage(Message msg) {
        FoodOrder foodOrder = (FoodOrder) msg.obj;
        mFoodOrderAdapter.onFoodOrderCreated(foodOrder);
        return true;
    }
}
