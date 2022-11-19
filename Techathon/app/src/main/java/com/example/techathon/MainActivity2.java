package com.example.techathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.techathon.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.breakfastCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, BreakFastActivity.class);
                intent.putExtra("type",1 );
                startActivity(intent);
            }
        });

        binding.lunchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, BreakFastActivity.class);
                intent.putExtra("type",2 );
                startActivity(intent);
            }
        });

        binding.dinnerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, BreakFastActivity.class);
                intent.putExtra("type",3 );
                startActivity(intent);
            }
        });

        binding.trackCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, TrackOrderActivity.class);
                startActivity(intent);
            }
        });


    }
}