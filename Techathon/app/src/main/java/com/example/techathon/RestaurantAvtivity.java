package com.example.techathon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.techathon.databinding.ActivityRestaurantAvtivityBinding;

public class RestaurantAvtivity extends AppCompatActivity {
    ActivityRestaurantAvtivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantAvtivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}