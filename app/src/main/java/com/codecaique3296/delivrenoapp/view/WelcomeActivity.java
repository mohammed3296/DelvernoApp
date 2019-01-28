package com.codecaique3296.delivrenoapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.backgroundtasks.MyService;
import com.codecaique3296.delivrenoapp.utils.Sesstion;
import com.cunoraz.gifview.library.GifView;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {

    LinearLayout l1, l2;
    Button btnsub;
    Animation uptodown, downtoup;

    private static final int SPLASH_DELAY = 3000;
    private final Handler mHandler = new Handler();
    private final Launcher mLauncher = new Launcher();
    private class Launcher implements Runnable {
        @Override
        public void run() {
            launch();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        btnsub = (Button) findViewById(R.id.buttonsub);
//        l1 = (LinearLayout) findViewById(R.id.l1);
//        l2 = (LinearLayout) findViewById(R.id.l2);
    //    uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
      //  downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
//        l1.setAnimation(uptodown);
  //      l2.setAnimation(downtoup);

//        findViewById(R.id.buttonsub).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
//            }
//        });
//
//        findViewById(R.id.buttonsub).setVisibility(View.GONE);
        GifView gifView1 = (GifView) findViewById(R.id.gif1);
        gifView1.setVisibility(View.VISIBLE);
        gifView1.play();
    }



    @Override
    protected void onStop() {
        mHandler.removeCallbacks(mLauncher);
        super.onStop();
    }

    private void launch() {
        if (!isFinishing()) {
            Intent intentMain = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intentMain);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHandler.postDelayed(mLauncher, SPLASH_DELAY);
    }

}
