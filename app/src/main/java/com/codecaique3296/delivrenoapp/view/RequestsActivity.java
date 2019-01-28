package com.codecaique3296.delivrenoapp.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.adapters.RequestAdapter;
import com.codecaique3296.delivrenoapp.backgroundtasks.MyService;
import com.codecaique3296.delivrenoapp.interfaces.RequestClick;
import com.codecaique3296.delivrenoapp.models.object.Request;
import com.codecaique3296.delivrenoapp.models.response.PublicResponse;
import com.codecaique3296.delivrenoapp.models.response.RequestsJson;
import com.codecaique3296.delivrenoapp.network.ApiClient;
import com.codecaique3296.delivrenoapp.network.ApiInterface;
import com.codecaique3296.delivrenoapp.utils.Sesstion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class RequestsActivity extends AppCompatActivity implements RequestClick {
    private ArrayList<Request> requests;
    private RecyclerView mRecyclerView;
    private RequestAdapter adapter;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        mLoadingIndicator = findViewById(R.id.loading_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_patients);
        adapter = new RequestAdapter(this);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }
        mRecyclerView.setHasFixedSize(true);
        getRequests(Sesstion.getInstance(this).getTayar().getId());
        startService(new Intent(RequestsActivity.this, MyService.class));
    }


    public void deleteReq(final String request_id) {
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiInterface apiService1 =
                                ApiClient.getClient().create(ApiInterface.class);
                        Call<PublicResponse> call1 = apiService1.deleteRequest("1", request_id,
                                Sesstion.getInstance(RequestsActivity.this).getTayar().getId());

                        call1.enqueue(new Callback<PublicResponse>() {
                            @Override
                            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                                int statusCode = response.code();
                                Toast.makeText(RequestsActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
                                getRequests(Sesstion.getInstance(RequestsActivity.this).getTayar().getId());
                            }

                            @Override
                            public void onFailure(Call<PublicResponse> call, Throwable t) {
                                Toast.makeText(RequestsActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
                                Log.e(">>>>>>>", t.getMessage());
                            }
                        });
                    }
                };
        showUnsavedChangesDialog(discardButtonClickListener);

    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete Request");
        builder.setPositiveButton("OK", discardButtonClickListener);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                    getRequests(Sesstion.getInstance(RequestsActivity.this).getTayar().getId());
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    public void getRequests(String tayar__id) {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        requests = new ArrayList<>();
        Call<RequestsJson> call1 = apiService1.getRequests("1", tayar__id);
        call1.enqueue(new Callback<RequestsJson>() {
            @Override
            public void onResponse(Call<RequestsJson> call, retrofit2.Response<RequestsJson> response) {
                int statusCode = response.code();
                mLoadingIndicator.setVisibility(View.GONE);
                requests = (ArrayList<Request>) response.body().getRequests();

                adapter.setRequestData(requests, RequestsActivity.this);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RequestsJson> call, Throwable t) {
                Toast.makeText(RequestsActivity.this, "failer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void accept(View v, int position) {
        acceptReq(getRequestID(position));
    }

    @Override
    public void receipt(View v, int position) {
        receiptReq(getRequestID(position));
    }

    @Override
    public void delete(View v, int position) {
        deleteReq(getRequestID(position));
    }

    private String getRequestID(int position) {
        Request request = requests.get(position);
        String req_id = request.getId();
        return req_id;
    }

    @Override
    public void deleive(View v, int position) {
        deleverdReq(getRequestID(position));
    }

    @Override
    public void undeleive(View v, int position) {
        undeleverdReq(getRequestID(position));
    }

    @Override
    public void unaccept(View v, int position) {
        unacceptReq(getRequestID(position));
    }

    @Override
    public void unreceipt(View v, int position) {
        unreceiptReq(getRequestID(position));
    }

    public void acceptReq(final String request_id) {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PublicResponse> call1 = apiService1.acceptRequest("1", request_id,
                Sesstion.getInstance(RequestsActivity.this).getTayar().getId());

        call1.enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                int statusCode = response.code();
                Toast.makeText(RequestsActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
               // getRequests(Sesstion.getInstance(RequestsActivity.this).getTayar().getId());
            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {
                Toast.makeText(RequestsActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
                Log.e(">>>>>>>", t.getMessage());
            }
        });


    }

    public void receiptReq(final String request_id) {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PublicResponse> call1 = apiService1.receiptRequest("1", request_id,
                Sesstion.getInstance(RequestsActivity.this).getTayar().getId());

        call1.enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                int statusCode = response.code();
                Toast.makeText(RequestsActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
            //    getRequests(Sesstion.getInstance(RequestsActivity.this).getTayar().getId());
            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {
                Toast.makeText(RequestsActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
                Log.e(">>>>>>>", t.getMessage());
            }
        });
    }

    public void deleverdReq(final String request_id) {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PublicResponse> call1 = apiService1.deleverdRequest("1", request_id,
                Sesstion.getInstance(RequestsActivity.this).getTayar().getId());

        call1.enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                int statusCode = response.code();
                Toast.makeText(RequestsActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
               // getRequests(Sesstion.getInstance(RequestsActivity.this).getTayar().getId());
            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {
                Toast.makeText(RequestsActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
                Log.e(">>>>>>>", t.getMessage());
            }
        });
    }

    public void unacceptReq(final String request_id) {

        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PublicResponse> call1 = apiService1.unacceptRequest("1", request_id,
                Sesstion.getInstance(RequestsActivity.this).getTayar().getId());

        call1.enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                int statusCode = response.code();
                Toast.makeText(RequestsActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
               // getRequests(Sesstion.getInstance(RequestsActivity.this).getTayar().getId());
            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {
                Toast.makeText(RequestsActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
                Log.e(">>>>>>>", t.getMessage());
            }
        });
    }

    public void unreceiptReq(final String request_id) {

        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PublicResponse> call1 = apiService1.unreceiptRequest("1", request_id,
                Sesstion.getInstance(RequestsActivity.this).getTayar().getId());

        call1.enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                int statusCode = response.code();
                Toast.makeText(RequestsActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
               // getRequests(Sesstion.getInstance(RequestsActivity.this).getTayar().getId());
            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {
                Toast.makeText(RequestsActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
                Log.e(">>>>>>>", t.getMessage());
            }
        });
    }

    public void undeleverdReq(final String request_id) {

        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PublicResponse> call1 = apiService1.undeleverdRequest("1", request_id,
                Sesstion.getInstance(RequestsActivity.this).getTayar().getId());

        call1.enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                int statusCode = response.code();
                Toast.makeText(RequestsActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
              //  getRequests(Sesstion.getInstance(RequestsActivity.this).getTayar().getId());
            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {
                Toast.makeText(RequestsActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
                Log.e(">>>>>>>", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  startActivity(new Intent(this , ADashboardActivity.class));
    }
}
