package com.codecaique3296.delivrenoapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.models.object.Mechanism;
import com.codecaique3296.delivrenoapp.network.ApiClient;
import com.codecaique3296.delivrenoapp.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class TermsActivity extends AppCompatActivity {
    TextView we, mechanism;
    ImageButton imageButton1, imageButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        we = findViewById(R.id.we);
        mechanism = findViewById(R.id.mechanism);

        imageButton1 = findViewById(R.id.imgwe);
        imageButton2 = findViewById(R.id.imgmecha);

        findViewById(R.id.lwe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(we.getVisibility() == View.GONE){
                    we.setVisibility(View.VISIBLE);
                    imageButton1.setImageResource(R.drawable.row2);
                }
                else {
                    we.setVisibility(View.GONE);
                    imageButton1.setImageResource(R.drawable.row1);
                }

            }
        });

        findViewById(R.id.lmecha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mechanism.getVisibility() == View.GONE){
                    mechanism.setVisibility(View.VISIBLE);
                    imageButton2.setImageResource(R.drawable.row2);
                }
                else {
                    mechanism.setVisibility(View.GONE);
                    imageButton2.setImageResource(R.drawable.row1);
                }
            }
        });

        getWe();
        getMechanism();
    }

    public void getWe() {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Mechanism> call1 = apiService1.getWe("1");
        call1.enqueue(new Callback<Mechanism>() {
            @Override
            public void onResponse(Call<Mechanism> call, retrofit2.Response<Mechanism> response) {
                we.setText(response.body().getContent());
            }

            @Override
            public void onFailure(Call<Mechanism> call, Throwable t) {
                Toast.makeText(TermsActivity.this, "failer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getMechanism() {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Mechanism> call1 = apiService1.getMechanism("1");
        call1.enqueue(new Callback<Mechanism>() {
            @Override
            public void onResponse(Call<Mechanism> call, retrofit2.Response<Mechanism> response) {
                mechanism.setText(response.body().getContent());
            }

            @Override
            public void onFailure(Call<Mechanism> call, Throwable t) {
                Toast.makeText(TermsActivity.this, "failer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  startActivity(new Intent(this , ADashboardActivity.class));
    }
}
