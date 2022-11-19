package com.example.techathon.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.techathon.ApiClasses;
import com.example.techathon.DBHelper;
import com.example.techathon.Models.Model;
import com.example.techathon.R;

import java.util.List;

public class RecyclerViewSample extends RecyclerView.Adapter<RecyclerViewSample.ViewHolder>{
    private Context context;
    private List<Model> data;
    private String id;
    private int work_id;

    public RecyclerViewSample(Context context, List<Model> data) {
        this.context = context;
        this.data = data;
        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getData();
        cursor.moveToNext();
        id = cursor.getString(0);
    }

    @NonNull
    @Override
    public RecyclerViewSample.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                        int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_recycler_view,
                parent,false);
        return new RecyclerViewSample.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewSample.ViewHolder holder, int position) {
        Model x = data.get(position);
        holder.category.setText("Dish : "+x.getDish());
//        holder.activity.setText(x.getActivity());
        holder.removeDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = x.getId();
                try{
                    ApiClasses.PostRemoveDish postRemoveDish = new ApiClasses.PostRemoveDish(id);
                    postRemoveDish.execute();
                    for (int i=0;i<data.size();i++){
                        if (data.get(i).getId()==id){
                            data.remove(i);
                            break;
                        }
                    }
                    notifyDataSetChanged();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView category, activity;
        public Button removeDish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            category = itemView.findViewById(R.id.rcDish);
            removeDish = itemView.findViewById(R.id.rcremoveDish);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Model x = data.get(position);
        }
    }
}