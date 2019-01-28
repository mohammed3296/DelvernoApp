package com.codecaique3296.delivrenoapp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.models.response.PublicResponse;
import com.codecaique3296.delivrenoapp.network.ApiClient;
import com.codecaique3296.delivrenoapp.network.ApiInterface;
import com.codecaique3296.delivrenoapp.utils.Sesstion;

import retrofit2.Call;
import retrofit2.Callback;

public class EditPhoneActivity extends AppCompatActivity {


    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_phone);
        final TextView textView = findViewById(R.id.edit_phone);
        String old_status = getIntent().getExtras().get("OLDPHONE").toString();
        textView.setText(old_status);
        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = textView.getText().toString();
                if (status.isEmpty() || status.length() < 11 || status.length() > 11) {
                    textView.setError(getString(R.string.udfdhsvhjdfvhr));
                } else {
                    loading.setTitle(getString(R.string.kjkfbvnfnvjhf));
                    loading.setMessage(getString(R.string.dskskxgvfhudsbfvh));
                    loading.show();
                    editPhone(textView.getText().toString());
                }
            }
        });

        loading = new ProgressDialog(this);
    }


    public void editPhone(final String asd) {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PublicResponse> call1 = apiService1.editPhone("1",
                Sesstion.getInstance(this).getTayar().getId(), asd);
        call1.enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                int statusCode = response.code();
                String message = response.body().getAck();
                Sesstion.getInstance(EditPhoneActivity.this).editPhoneUser(asd);
                loading.dismiss();
                finish();
                Intent intentSettings = new Intent(EditPhoneActivity.this, ProfileActivity.class);
                startActivity(intentSettings);
            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {
                Toast.makeText(EditPhoneActivity.this, "failer", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }
}
