package com.example.techathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.example.techathon.Adapters.RecyclerViewSample;
import com.example.techathon.Adapters.TrackOrderAdapter;
import com.example.techathon.Models.Model;
import com.example.techathon.Models.TrackOrder;
import com.example.techathon.databinding.ActivityTrackOrderBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrackOrderActivity extends AppCompatActivity {
    ActivityTrackOrderBinding binding;
    private TrackOrderAdapter recyclerViewAdapter;
    private ArrayList<TrackOrder> workArrayList;
    DBHelper db;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrackOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DBHelper(this);
//        Cursor cursor = db.getData();
//        cursor.moveToNext();
//        email = cursor.getString(0);


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
                ApiClasses.PostTrackOrder postTrackOrder = new ApiClasses.PostTrackOrder(email);
                postTrackOrder.execute().get();
                JSONArray jsonArray = postTrackOrder.getData();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    workArrayList.add(new TrackOrder(jsonObject.getInt("id"),
                            jsonObject.getString("status"),
                            jsonObject.getString("acceptedBy"),
                            jsonObject.getString("orderType"),
                            jsonObject.getString("mobile")));
                }
            } catch (Exception e) {
                Log.e("error", e.toString());
            }

            recyclerViewAdapter = new TrackOrderAdapter(getApplicationContext(),
                    workArrayList);
            binding.recyclerView.setAdapter(recyclerViewAdapter);
        }catch (Exception e){
            Log.e("error", e.toString());
        }

    }
}