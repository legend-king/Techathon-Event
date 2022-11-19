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
import com.example.techathon.Models.TrackOrder;
import com.example.techathon.R;

import java.util.List;

public class TrackOrderAdapter extends RecyclerView.Adapter<TrackOrderAdapter.ViewHolder> {
    private Context context;
    private List<TrackOrder> data;
    private String id;
    private int work_id;

    public TrackOrderAdapter(Context context, List<TrackOrder> data) {
        this.context = context;
        this.data = data;
        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getData();
        cursor.moveToNext();
        id = cursor.getString(0);
    }

    @NonNull
    @Override
    public TrackOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_track_order,
                parent, false);
        return new TrackOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackOrderAdapter.ViewHolder holder, int position) {
        TrackOrder x = data.get(position);
//        holder.ordertype.setText("Order : " + x.getOrderType());
        String a=x.getOrderType();
        if (a.equals("1")){
            holder.ordertype.setText("Order : BreakFast" );
        }
        else if (a.equals("2")){
            holder.ordertype.setText("Order : Lunch" );
        }
        else{
            holder.ordertype.setText("Order : Dinner" );
        }
        holder.acceptedby.setText("Accepted By : "+x.getAcceptedBy());
        holder.acceptedmobile.setText("Mobile Number : "+x.getMobile());
        holder.status.setText("Status : "+x.getStatus());
//        holder.activity.setText(x.getActivity());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = x.getId();
                try {
                    ApiClasses.PostCancelOrder postRemoveDish = new ApiClasses.PostCancelOrder(id);
                    postRemoveDish.execute();
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getId() == id) {
                            data.remove(i);
                            break;
                        }
                    }
                    notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView ordertype, acceptedby, acceptedmobile, status;
        public Button cancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            ordertype = itemView.findViewById(R.id.rcType);
            acceptedby = itemView.findViewById(R.id.rcAcceptedBy);
            acceptedmobile = itemView.findViewById(R.id.rcAcceptedByNumber);
            status = itemView.findViewById(R.id.rcStatus);
            cancel = itemView.findViewById(R.id.rcCancelOrder);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            TrackOrder x = data.get(position);
        }
    }
}