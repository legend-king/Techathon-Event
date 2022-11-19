package com.example.techathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.techathon.Adapters.RecyclerViewSample;
import com.example.techathon.Models.Model;
import com.example.techathon.databinding.ActivityBreakFastBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BreakFastActivity extends AppCompatActivity {
    ActivityBreakFastBinding binding;
    private RecyclerViewSample recyclerViewAdapter;
    private ArrayList<Model> workArrayList;
    DBHelper db;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBreakFastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DBHelper(this);
        Intent intent = getIntent();
        type=intent.getIntExtra("type", 0);

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(BreakFastActivity.this, AddItem.class);
                intent1.putExtra("type", type);
                startActivity(intent1);
            }
        });

        binding.ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Cursor cursor = db.getData();
                    cursor.moveToNext();
                    String email = cursor.getString(0);
                    ApiClasses.PostPlaceOrder postPlaceOrder = new ApiClasses.PostPlaceOrder(type, email);
                    postPlaceOrder.execute().get();
                    JSONObject jsonObject = postPlaceOrder.getData();
                    if (jsonObject.getInt("message")==1){
                        Toast.makeText(getApplicationContext(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        binding.setOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(BreakFastActivity.this, SetOrderActivity.class);
                intent1.putExtra("type", type);
                startActivity(intent1);
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            workArrayList = new ArrayList<>();
            try {
                Cursor cursor = db.getData();
                cursor.moveToNext();
                String email = cursor.getString(0);
                ApiClasses.PostViewDish postViewDish = new ApiClasses.PostViewDish(email, type);
                postViewDish.execute().get();
                JSONArray jsonArray = postViewDish.getData();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    workArrayList.add(new Model(jsonObject.getString("dish"), jsonObject.getInt("id")));
                }
            } catch (Exception e) {
                Log.e("error", e.toString());
            }

            recyclerViewAdapter = new RecyclerViewSample(getApplicationContext(),
                    workArrayList);
            binding.recyclerView.setAdapter(recyclerViewAdapter);
        }catch (Exception e){
            Log.e("error", e.toString());
        }
    }
}