package com.example.techathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.example.techathon.databinding.ActivityAddItemBinding;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddItem extends AppCompatActivity {
    ActivityAddItemBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);

        Intent intent= getIntent();
        int type = intent.getIntExtra("type", 0);
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dish = binding.dishET.getText().toString().trim();
                if (dish.equals("")){
                    binding.dishET.setError("Dish Cannot be empty");
                }
                else{
                    try{

                    Cursor cursor = db.getData();
                    cursor.moveToNext();
                    String email = cursor.getString(0);
                    ApiClasses.PostAddDish postAddDish = new ApiClasses.PostAddDish(type, dish, email);
                    postAddDish.execute().get();
                    JSONObject jsonObject = postAddDish.getData();
                    if (jsonObject.getInt("message")==1){
                            Intent intent1 = new Intent(AddItem.this, BreakFastActivity.class);
                            intent1.putExtra("type", type);
                            startActivity(intent1);
                            finish();

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                }

            }
        });
    }
}