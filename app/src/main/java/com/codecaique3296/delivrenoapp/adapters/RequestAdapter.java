package com.codecaique3296.delivrenoapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.interfaces.RequestClick;
import com.codecaique3296.delivrenoapp.models.object.Request;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    public List<Request> data = Collections.emptyList();
    Request current;
    int currentPos = 0;

    private RequestClick lOnClickListener;

    public RequestAdapter(RequestClick listener) {
        lOnClickListener = listener;
    }

    public void setRequestData(List<Request> recipesIn, Context context) {
        data = recipesIn;
        mContext = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.request_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        MyHolder viewHolder = new MyHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);

        myHolder.resturantAddress.setText(current.getResturant_address());
        myHolder.resturantName.setText(current.getResturant_name());
        myHolder.clientAddress.setText(current.getClient_address());
        myHolder.time.setText(getDate(Long.valueOf(current.getTime()), "EEE, d MMM yyyy HH:mm"));

        if (current.getAccepted().equals("Y")) {
            myHolder.accepted.setChecked(true);
        } else {
            myHolder.accepted.setChecked(false);
        }
        if (current.getDelivered().equals("Y")) {
            myHolder.delevered.setChecked(true);
        } else {
            myHolder.delevered.setChecked(false);
        }
        if (current.getReceipt().equals("Y")) {
            myHolder.receipt.setChecked(true);
        } else {
            myHolder.receipt.setChecked(false);
        }

        myHolder.accepted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lOnClickListener.accept(buttonView , position);
                }else {
                    lOnClickListener.unaccept(buttonView , position);
                }

                }
                }
        );

        myHolder.delevered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lOnClickListener.deleive(buttonView , position);
                    }else {
                    lOnClickListener.undeleive(buttonView , position);
                    }

                    }
                    }
        );


        myHolder.receipt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lOnClickListener.receipt(buttonView , position);
                    }else {
                    lOnClickListener.unreceipt(buttonView , position);
                    }

                    }
                    }
        );
        myHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lOnClickListener.delete(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView resturantAddress;
        TextView resturantName;
        TextView clientAddress;
        TextView time;
        CheckBox accepted, delevered, receipt;
        LinearLayout linearLayout;

        public MyHolder(View itemView) {
            super(itemView);
            resturantAddress = (TextView) itemView.findViewById(R.id.item_res_address);
            resturantName = (TextView) itemView.findViewById(R.id.item_res_name);
            clientAddress = (TextView) itemView.findViewById(R.id.item_client_address);
            linearLayout = itemView.findViewById(R.id.delete_btn);
            time = (TextView) itemView.findViewById(R.id.item_time);
            accepted = (CheckBox) itemView.findViewById(R.id.item_accept);
            delevered = (CheckBox) itemView.findViewById(R.id.item_delivered);
            receipt = (CheckBox) itemView.findViewById(R.id.item_receipt);

        }

    }

    public void clear() {
        final int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    private static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}