package com.codecaique3296.delivrenoapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.models.response.PublicResponse;
import com.codecaique3296.delivrenoapp.network.ApiClient;
import com.codecaique3296.delivrenoapp.network.ApiInterface;
import com.codecaique3296.delivrenoapp.utils.Sesstion;

import retrofit2.Call;
import retrofit2.Callback;

public class ProblemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);
        final EditText editText = findViewById(R.id.problem_details);
        findViewById(R.id.problem_buton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendProblem(editText.getText().toString());
            }
        });

    }

    private void sendProblem(String content) {

        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PublicResponse> call1 = apiService1.addProblem("1", content,
                Sesstion.getInstance(ProblemsActivity.this).getTayar().getId());
        call1.enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                Toast.makeText(ProblemsActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {
                Toast.makeText(ProblemsActivity.this, "failer", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
