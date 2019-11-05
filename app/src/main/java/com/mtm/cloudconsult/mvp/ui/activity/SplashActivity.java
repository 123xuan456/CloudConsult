package com.mtm.cloudconsult.mvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.jess.arms.utils.ArmsUtils;
import com.mtm.cloudconsult.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ArmsUtils.startActivity(MainActivity.class);
                finish();
            }
        }, 2000);

    }
}
