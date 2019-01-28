
package com.codecaique3296.delivrenoapp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class EditPasswordActivity extends AppCompatActivity {

    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        final EditText textView = findViewById(R.id.re_pass_edit);
        final EditText old_pass = findViewById(R.id.old_pass);
        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setTitle(getString(R.string.kfljvnbjgcnbk));
                loading.setMessage(getString(R.string.kfkbnkfnvikfnvbkl));
                loading.show();
                editPassword(textView.getText().toString(), old_pass.getText().toString());
            }
        });
        loading = new ProgressDialog(this);
    }


    public void editPassword(String newPass, String oldPass) {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PublicResponse> call1 = apiService1.editpassword("1",
                Sesstion.getInstance(this).getTayar().getId(), newPass, oldPass);
        call1.enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                int statusCode = response.code();
                String message = response.body().getAck();
                Toast.makeText(EditPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                loading.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {
                Log.e("ASFF" , t.getMessage());
                Toast.makeText(EditPasswordActivity.this, "failer", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }
}
