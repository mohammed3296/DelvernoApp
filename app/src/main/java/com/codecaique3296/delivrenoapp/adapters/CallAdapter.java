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
import com.codecaique3296.delivrenoapp.interfaces.CallIClick;
import com.codecaique3296.delivrenoapp.models.object.Phone;

import java.util.Collections;
import java.util.List;

public class CallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    public List<Phone> data = Collections.emptyList();
    Phone current;
    int currentPos = 0;

    private CallIClick lOnClickListener;

    public CallAdapter(CallIClick listener) {
        lOnClickListener = listener;
    }

    public void setCallData(List<Phone> recipesIn, Context context) {
        data = recipesIn;
        mContext = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.call_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        CallAdapter.MyHolder viewHolder = new CallAdapter.MyHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        CallAdapter.MyHolder myHolder = (CallAdapter.MyHolder) holder;
        current = data.get(position);
        myHolder.number.setText(current.getNumber());
        myHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lOnClickListener.call(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView number;
        LinearLayout linearLayout;

        public MyHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.call);
            number = (TextView) itemView.findViewById(R.id.number_item);
        }

    }

    public void clear() {
        final int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }
}