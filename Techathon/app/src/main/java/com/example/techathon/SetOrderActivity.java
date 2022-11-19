package com.example.techathon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.techathon.Models.Model;
import com.example.techathon.databinding.ActivityMain2Binding;
import com.example.techathon.databinding.ActivitySetOrderBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class SetOrderActivity extends AppCompatActivity {
    ActivitySetOrderBinding binding;
    private DatePicker datePicker;
    private Calendar calendar;
    private int hourIn, minuteIn;
    private String am_pmIn, am_pmOut;
    DBHelper db;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        db = new DBHelper(this);

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);

        calendar = Calendar.getInstance();

        hourIn = calendar.get(Calendar.HOUR_OF_DAY);
        minuteIn = calendar.get(Calendar.MINUTE);
        int x;

        if (hourIn>12){
            am_pmIn = "PM";
            am_pmOut = "PM";
            x = hourIn-12;
        }
        else{
            am_pmIn = "AM";
            am_pmOut = "AM";
            x = hourIn;
        }

        String y;

        if (minuteIn<10){
            y= "0" + minuteIn;
        }
        else{
            y=""+minuteIn;
        }


        binding.timeET.setText(new StringBuilder().append(x).append(':').append(y)
                .append(' ').append(am_pmIn));


        binding.timeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(SetOrderActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                hourIn = hourOfDay;
                                minuteIn = minute;

                                String y;

                                if (minute<10){
                                    y= "0" + minute;
                                }
                                else{
                                    y=""+minute;
                                }

                                int x = hourOfDay;
                                if (x>12){
                                    x-=12;
                                    am_pmIn = "PM";
                                }
                                else{
                                    am_pmIn = "AM";
                                }

                                binding.timeET.setText(new StringBuilder().append(x)
                                        .append(':').append(y).append(' ').append(am_pmIn));
                            }
                        }, hourIn, minuteIn, false);
                timePickerDialog.updateTime(hourIn, minuteIn);
                timePickerDialog.show();

            }
        });

        binding.set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String wantedTime = hourIn+":"+minuteIn;
                    Cursor cursor = db.getData();
                    cursor.moveToNext();
                    String email = cursor.getString(0);
                    ApiClasses.PostSetOrder postSetOrder = new ApiClasses.PostSetOrder(type, email,wantedTime );
                    postSetOrder.execute().get();
                    JSONObject jsonObject = postSetOrder.getData();
                    if (jsonObject.getInt("message")==1){
                        Toast.makeText(getApplicationContext(), "Order Set Successfully", Toast.LENGTH_LONG).show();
                        finish();

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}