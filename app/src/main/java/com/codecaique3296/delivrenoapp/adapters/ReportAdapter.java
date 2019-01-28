package com.codecaique3296.delivrenoapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.interfaces.ReportClick;
import com.codecaique3296.delivrenoapp.models.object.Report;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    public List<Report> data = Collections.emptyList();
    Report current;
    int currentPos = 0;

    private ReportClick lOnClickListener;

    public ReportAdapter(ReportClick listener) {
        lOnClickListener = listener;
    }

    public void setReporttData(List<Report> recipesIn, Context context) {
        data = recipesIn;
        mContext = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.static_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        ReportAdapter.MyHolder viewHolder = new ReportAdapter.MyHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ReportAdapter.MyHolder myHolder = (ReportAdapter.MyHolder) holder;
        current = data.get(position);

        myHolder.resturantAddress.setText(current.getRes_address());
        myHolder.resturantName.setText(current.getResturant_name());
        myHolder.clientAddress.setText(current.getClient_address());
        myHolder.time.setText(getDate(Long.valueOf(current.getDeliveredtime()), "EEE, d MMM yyyy HH:mm"));

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
        LinearLayout linearLayout;

        public MyHolder(View itemView) {
            super(itemView);
            resturantAddress = (TextView) itemView.findViewById(R.id.rep_res_address);
            resturantName = (TextView) itemView.findViewById(R.id.rep_res_name);
            clientAddress = (TextView) itemView.findViewById(R.id.rep_client_address);
            linearLayout = itemView.findViewById(R.id.delete_btn);
            time = (TextView) itemView.findViewById(R.id.rep_item);
        }

    }

    public void clear() {
        final int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}