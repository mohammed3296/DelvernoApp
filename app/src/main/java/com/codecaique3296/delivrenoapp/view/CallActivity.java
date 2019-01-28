package com.codecaique3296.delivrenoapp.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.adapters.CallAdapter;
import com.codecaique3296.delivrenoapp.interfaces.CallIClick;
import com.codecaique3296.delivrenoapp.models.object.Phone;
import com.codecaique3296.delivrenoapp.models.response.PhoneJson;
import com.codecaique3296.delivrenoapp.network.ApiClient;
import com.codecaique3296.delivrenoapp.network.ApiInterface;
import com.codecaique3296.delivrenoapp.utils.Sesstion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class CallActivity extends AppCompatActivity implements CallIClick {

    private ArrayList<Phone> phones;
    private RecyclerView mRecyclerView;
    private CallAdapter adapter;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);


        mLoadingIndicator = findViewById(R.id.loading_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_patients);
        adapter = new CallAdapter(this);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }
        mRecyclerView.setHasFixedSize(true);
        getPhones();
    }

    @Override
    public void call(View v, int position) {
        Phone phone = phones.get(position);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phone.getNumber()));
        startActivity(intent);
    }


    public void getPhones() {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        phones = new ArrayList<>();
        Call<PhoneJson> call1 = apiService1.getphones("1");
        call1.enqueue(new Callback<PhoneJson>() {
            @Override
            public void onResponse(Call<PhoneJson> call, retrofit2.Response<PhoneJson> response) {
                int statusCode = response.code();
                mLoadingIndicator.setVisibility(View.GONE);
                phones = (ArrayList<Phone>) response.body().getPhones();

                adapter.setCallData(phones, CallActivity.this);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<PhoneJson> call, Throwable t) {
                Toast.makeText(CallActivity.this, getString(R.string.failer_str), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  startActivity(new Intent(this , ADashboardActivity.class));
    }
}
