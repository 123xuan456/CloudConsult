package com.mtm.cloudconsult.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mtm.cloudconsult.R;
import com.youth.banner.loader.ImageLoader;

/**
 * 首页轮播图
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object url, ImageView imageView) {

        //设置图片圆角角度
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = new RequestOptions()
                .error(R.drawable.shape_bg_loading)
                .transforms(new CenterCrop(), new RoundedCorners(10))
                .placeholder(R.drawable.shape_bg_loading);
        Glide.with(context).load(url)
                .apply(options)
                .into(imageView);
    }
}
