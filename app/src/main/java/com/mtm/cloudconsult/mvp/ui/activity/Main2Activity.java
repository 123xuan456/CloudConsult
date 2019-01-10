package com.mtm.cloudconsult.mvp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mtm.cloudconsult.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageView iv = findViewById(R.id.iv);
        RequestOptions options = new RequestOptions()
                .error(R.drawable.shape_bg_loading)
                .transforms(new CenterCrop(), new RoundedCorners(10))
                .placeholder(R.drawable.shape_bg_loading);
        Glide.with(this).load("https://upload-images.jianshu.io/upload_images/1354448-7809c9d06fbc69bd.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240")
                .apply(options)
                .into(iv);
//        ArmsUtils.snackbarText("打包成功！！");
    }
}
