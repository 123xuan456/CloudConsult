package com.mtm.cloudconsult.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jess.arms.base.App;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.mtm.cloudconsult.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by MTM on 2019/1/8.
 * 功能包括加载图片，圆形图片，圆角图片，指定圆角图片，模糊图片，灰度图片等等。
 *
 * @author QSX
 */
public class GlideUtils {

    /*
     *加载图片(默认)
     */
    public static void loadImage(Context context, String url, ImageView imageView, int placeholder, int error) {
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions()
                .crossFade();
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholder) //占位图
                .error(error)       //错误图
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options)
                .transition(transitionOptions)//过渡动画
                .into(imageView);
    }
    /*
     *加载电影图片(默认)
     */
    public static void loadMovieImage(Context context, String url, ImageView imageView) {
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions()
                .crossFade();
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.img_default_movie) //占位图
                .error(R.drawable.img_default_movie)       //错误图
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options)
                .transition(transitionOptions)//过渡动画
                .into(imageView);
    }

    /**
     * 指定图片大小;使用override()方法指定了一个图片的尺寸。
     * Glide现在只会将图片加载成width*height像素的尺寸，而不会管你的ImageView的大小是多少了。
     * 如果你想加载一张图片的原始尺寸的话，可以使用Target.SIZE_ORIGINAL关键字----override(Target.SIZE_ORIGINAL)
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public static void loadImageSize(Context context, String url, ImageView imageView, int width, int height, int placeholder, int error) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholder) //占位图
                .error(error)       //错误图
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }


    /**
     * 禁用内存缓存功能
     * diskCacheStrategy()方法基本上就是Glide硬盘缓存功能的一切，它可以接收五种参数：
     * <p>
     * DiskCacheStrategy.NONE： 表示不eee缓存任何内容。
     * DiskCacheStrategy.DATA： 表示只缓存原始图片。
     * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
     * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
     * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
     */

    public static void loadImageSizekipMemoryCache(Context context, String url, ImageView imageView, int placeholder, int error) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder) //占位图
                .error(error)       //错误图S
                .skipMemoryCache(true)//禁用掉Glide的内存缓存功能
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * @param context
     * @param imageView
     * @param url                  路径
     * @param placeholder          默认图片
     * @param errorPic             错误图片
     * @param bitmapTransformation 高斯模糊
     */
    public static void loadTransformationImage(Context context, ImageView imageView, String url, int placeholder, int errorPic, BitmapTransformation bitmapTransformation) {
        AppComponent mAppComponent = ((App) context.getApplicationContext()).getAppComponent();
        ImageLoader mImageLoader = mAppComponent.imageLoader();

        ImageConfigImpl.Builder builder = ImageConfigImpl.builder();
        builder.url(url);
        builder.imageView(imageView);
        //判断网络是否为4G，4G网络不加载网络图片
        if (NetworkUtils.isMobileData()) {
            builder.cacheStrategy(5);
        }
        if (bitmapTransformation != null) {
            builder.transformation(bitmapTransformation);
        }
        if (placeholder != 0) {
            builder.placeholder(placeholder);
        }
        if (errorPic != 0) {
            builder.errorPic(errorPic);
        }
        mImageLoader.loadImage(context, builder.build());
    }

    /**
     * 加载圆形图片
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView, int placeholder, int error) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(placeholder)
                .error(error)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 预先加载图片
     * 在使用图片之前，预先把图片加载到缓存，调用了预加载之后，我们以后想再去加载这张图片就会非常快了，
     * 因为Glide会直接从缓存当中去读取图片并显示出来
     */
    public static void preloadImage(Context context, String url) {
        Glide.with(context)
                .load(url)
                .preload();

    }

    /**
     * 加载圆角图片-指定任意部分圆角（图片上、下、左、右四个角度任意定义）
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCustRoundCircleImage(Context context, String url, ImageView imageView) {

        RequestOptions options = new RequestOptions()
                .error(R.drawable.shape_bg_loading)
                .transforms(new CenterCrop(), new RoundedCorners(10))
                .placeholder(R.drawable.shape_bg_loading);

        Glide.with(context).load(url).apply(options).into(imageView);
    }


    /**
     * Glide.with(this).asGif()    //强制指定加载动态图片
     * 如果加载的图片不是gif，则asGif()会报错， 当然，asGif()不写也是可以正常加载的。
     * 加入了一个asBitmap()方法，这个方法的意思就是说这里只允许加载静态图片，不需要Glide去帮我们自动进行图片格式的判断了。
     * 如果你传入的还是一张GIF图的话，Glide会展示这张GIF图的第一帧，而不会去播放它。
     *
     * @param context
     * @param url       例如：https://image.niwoxuexi.com/blog/content/5c0d4b1972-loading.gif
     * @param imageView
     */
    private void loadGif(Context context, String url, ImageView imageView, int placeholder, int error) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)
                .error(error);
        Glide.with(context)
                .load(url)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);

    }

    /**
     * 显示随机的图片(每日推荐)
     *
     * @param imgNumber 有几张图片要显示,对应默认图
     * @param imageUrl  显示图片的url
     * @param imageView 对应图片控件
     */
    public static void displayRandom(int imgNumber, String imageUrl, ImageView imageView) {
        loadImage(imageView.getContext(), imageUrl, imageView, getMusicDefaultPic(imgNumber), getMusicDefaultPic(imgNumber));

    }

    private static int getMusicDefaultPic(int imgNumber) {
        switch (imgNumber) {
            case 1:
                return R.drawable.img_two_bi_one;
            case 2:
                return R.drawable.img_four_bi_three;
            case 3:
                return R.drawable.img_one_bi_one;
            case 4:
                return R.drawable.shape_bg_loading;
            default:
                break;
        }
        return R.drawable.img_four_bi_three;
    }


    public static int dip2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void downloadImage(final Context context, final String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FutureTarget<File> target = Glide.with(context)
                            .asFile()
                            .load(url)
                            .submit();
                    final File imageFile = target.get();
                    Log.d("logcat", "下载好的图片文件路径=" + imageFile.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 用于干货item，将gif图转换为静态图
     */
    public static void displayGif(String url, ImageView imageView) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_one_bi_one)
                .error(R.drawable.img_one_bi_one);


        Glide.with(imageView.getContext()).load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 书籍、妹子图、电影列表图
     * 默认图区别
     */
    public static void displayEspImage(String url, ImageView imageView, int type) {
        loadImage(imageView.getContext(), url, imageView, getDefaultPic(type), getDefaultPic(type));
    }

    private static int getDefaultPic(int type) {
        switch (type) {
            case 0:// 电影
                return R.drawable.img_default_movie;
            case 1:// 妹子
                return R.drawable.img_default_meizi;
            case 2:// 书籍
                return R.drawable.img_default_book;
            case 3:
                return R.drawable.shape_bg_loading;
            default:
                break;
        }
        return R.drawable.img_default_meizi;
    }

    /**
     * 图片下载
     */
    public static void saveImageToGallery(Context context, Bitmap bmp, String title) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), AppUtils.getAppName());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        if (appDir.exists()) {
            String fileName = title.replace('/', '-') + ".jpg";
            File file = new File(appDir, fileName);
            if (file.exists()) {
                ToastUtils.showShort("图片已存在");
            } else {
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 其次把文件插入到系统图库
//                try {
//                    MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                            file.getAbsolutePath(), fileName, null);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                // 最后通知图库更新
//                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsoluteFile())));
                ToastUtils.showLong("已保存至" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppUtils.getAppName());
            }
        }
//        String fileName = System.currentTimeMillis() + ".jpg";
    }
}