package com.codecaique3296.delivrenoapp.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.models.object.Tayar;
import com.codecaique3296.delivrenoapp.models.response.ImageUpdateJson;
import com.codecaique3296.delivrenoapp.models.response.PublicResponse;
import com.codecaique3296.delivrenoapp.models.response.TayarStatusJson;
import com.codecaique3296.delivrenoapp.network.ApiClient;
import com.codecaique3296.delivrenoapp.network.ApiInterface;
import com.codecaique3296.delivrenoapp.utils.Sesstion;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.tayar_img)
    ImageView tayar_img;
    @BindView(R.id.tayar_name)
    TextView tayar_name;
    @BindView(R.id.tayar_email)
    TextView tayar_email;
    @BindView(R.id.tayar_phone1)
    TextView tayar_phone1;
    @BindView(R.id.tayar_phone2)
    TextView tayar_phone2;
    @BindView(R.id.togglestatus)
    Switch togglestatus;

    private Tayar currentTayar;

    private int PICK_IMAGE_REQUEST = 2;
    private Bitmap bitmap;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        findViewById(R.id.edit_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditPasswordActivity.class));
            }
        });

        findViewById(R.id.edit_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPhone = tayar_phone2.getText().toString();
                Intent intent = new Intent(ProfileActivity.this, EditPhoneActivity.class);
                intent.putExtra("OLDPHONE", oldPhone);
                startActivity(intent);
            }
        });
        currentTayar = Sesstion.getInstance(this).getTayar();
        Picasso.get().load(currentTayar.getImage()).placeholder(R.drawable.ert).into(tayar_img);
        tayar_name.setText(String.format("%s %s", currentTayar.getFirstname(), currentTayar.getSecondname()));
        tayar_email.setText(currentTayar.getEmail());
        tayar_phone1.setText(currentTayar.getPhone1());
        tayar_phone2.setText(currentTayar.getPhone2());
        togglestatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setOn();
                } else {
                    setof();
                }
            }
        });
        getTayarStatus();

        findViewById(R.id.tayar_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }

    private void setOn() {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PublicResponse> call1 = apiService1.setStatusOn("1", currentTayar.getId());
        call1.enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                // Toast.makeText(ProfileActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "failer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setof() {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PublicResponse> call1 = apiService1.setStatusOff("1", currentTayar.getId());
        call1.enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                // Toast.makeText(ProfileActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "failer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTayarStatus() {

        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<TayarStatusJson> call1 = apiService1.getTayarStatus("1", currentTayar.getId());
        call1.enqueue(new Callback<TayarStatusJson>() {
            @Override
            public void onResponse(Call<TayarStatusJson> call, retrofit2.Response<TayarStatusJson> response) {
                togglestatus.setChecked(response.body().isStatus());
            }

            @Override
            public void onFailure(Call<TayarStatusJson> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "failer", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                //  bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                bitmap = decodeUri(filePath);
                updateImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o);
        final int REQUIRED_SIZE = 100;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o2);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public void updateImage() {
        String uploadImage = getStringImage(bitmap);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ImageUpdateJson> call = apiService.editImage(
                "1",
                Sesstion.getInstance(this).getTayar().getId(),
                uploadImage);
        call.enqueue(new Callback<ImageUpdateJson>() {
            @Override
            public void onResponse(Call<ImageUpdateJson> call, retrofit2.Response<ImageUpdateJson> response) {
                int statusCode = response.code();
                Sesstion.getInstance(ProfileActivity.this).editProfileUser(response.body().getNewLink());
                Toast.makeText(ProfileActivity.this, response.body().getAck(), Toast.LENGTH_LONG).show();
                Picasso.get().load(response.body().getNewLink()).placeholder(R.drawable.ert).into(tayar_img);
            }

            @Override
            public void onFailure(Call<ImageUpdateJson> call, Throwable t) {
                  Toast.makeText(ProfileActivity.this, "EROROROR", Toast.LENGTH_LONG).show();
                Log.e("EROOROR", t.getStackTrace().toString());
                // getMessages();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(ProfileActivity.this , MainActivity.class));
    }

}
