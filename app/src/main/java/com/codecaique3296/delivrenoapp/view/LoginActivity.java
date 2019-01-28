package com.codecaique3296.delivrenoapp.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.models.object.Tayar;
import com.codecaique3296.delivrenoapp.models.response.LoginResponse;
import com.codecaique3296.delivrenoapp.network.ApiClient;
import com.codecaique3296.delivrenoapp.network.ApiInterface;
import com.codecaique3296.delivrenoapp.utils.Sesstion;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.email)
    EditText _emailText;
    @BindView(R.id.password)
    EditText _passwordText;
    @BindView(R.id.email_sign_in_button)
    Button _loginButton;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        if (Sesstion.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            return;
        }
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }


    public void onLoginSuccess(Tayar tayar) {

        try {
            Log.e(">>>>>" , tayar.toString());
            Sesstion.getInstance(this).userLogin(tayar);
            Intent startPageIntent = new Intent(LoginActivity.this, MainActivity.class);
            startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(startPageIntent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onLoginFailed() {
        Snackbar.make(_loginButton, R.string.kjdfbvkjndfjkbvnfgvjae, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString().trim();
        String password = _passwordText.getText().toString().trim();

        if (email.isEmpty() || !Patterns.PHONE.matcher(email).matches()) {
            _emailText.setError(getString(R.string.iujkdfngjdrfjfsegfewr));
            valid = false;
        } else {
            _emailText.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError(getString(R.string.iixxfvdfngjnoesifkwe));
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }


    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        final String email = _emailText.getText().toString().trim();
        final String password = _passwordText.getText().toString().trim();
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Auth...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<LoginResponse> call = apiService.getLoginTayar("1", email, password);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                    int statusCode = response.code();
                    final String message = response.body().getAck();
                    if (!response.body().isError()) {
                        final Tayar tayar = response.body().getTayar();
                        Log.e("ASDFDFJD", response.body().toString());
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        onLoginSuccess(tayar);
                                    }
                                }, 1000);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, message,
                                Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Failer", Toast.LENGTH_SHORT).show();
                    Log.e("ASDFDFJD", t.getMessage());
                    progressDialog.dismiss();
                }
            });

        } else {
            Snackbar.make(_loginButton, R.string.kjfbbvkjdffieajsiofsriu, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}

