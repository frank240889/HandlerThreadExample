package com.example.handlerthreadexample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Startting orders...", Snackbar.LENGTH_LONG)
                        .setAction("Ok", null).show();*/
                /*mOrderHandlerThread.sendOrder(new FoodOrder());*/
                ExecutorService simpleExecutor = Executors.newSingleThreadExecutor();
                simpleExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Callable<String>> tasks = new ArrayList<>();
                        tasks.add(new Callable<String>() {
                            public String call() {
                                return getFirstDataFromNetwork();
                            }
                        });
                        tasks.add(new Callable<String>() {
                            public String call() {
                                return getSecondDataFromNetwork();
                            }
                        });
                        ExecutorService executor = Executors.newFixedThreadPool(2);
                        try {
                            List<Future<String>> futures = executor.invokeAll(tasks);
                            String mashedData = mashupResult(futures);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        executor.shutdown();
                    }
                });
            }
        });
        mFoodOrderAdapter = new FoodOrderAdapter();
        mRecyclerView = findViewById(R.id.food_order_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFoodOrderAdapter);

    }


    private String getFirstDataFromNetwork() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "first call";
    }
    private String getSecondDataFromNetwork() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "second call";
    }

    private String mashupResult(List<Future<String>> futures)
            throws ExecutionException, InterruptedException {
        for (Future<String> future : futures) {
            future.get();
        }
        return "mashhed data";
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUiHandler = new Handler(this);
        mOrderHandlerThread = new OrderHandlerThread(mUiHandler);
        mOrderHandlerThread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUiHandler.removeCallbacks(null);
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
