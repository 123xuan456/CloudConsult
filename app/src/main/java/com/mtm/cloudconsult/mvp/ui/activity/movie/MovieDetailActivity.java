package com.mtm.cloudconsult.mvp.ui.activity.movie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.adapter.MovieListAdapter;
import com.mtm.cloudconsult.app.utils.GlideUtils;
import com.mtm.cloudconsult.app.utils.StringUtils;
import com.mtm.cloudconsult.app.view.MyNestedScrollView;
import com.mtm.cloudconsult.app.view.statusbar.StatusBarUtil;
import com.mtm.cloudconsult.di.component.DaggerMovieDetailComponent;
import com.mtm.cloudconsult.di.module.MovieDetailModule;
import com.mtm.cloudconsult.mvp.contract.MovieDetailContract;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieBean;
import com.mtm.cloudconsult.mvp.presenter.MovieDetailPresenter;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mtm.cloudconsult.app.api.CloudConstant.MOVIE_INFO;
import static com.mtm.cloudconsult.app.api.CloudConstant.PRELOADNUMBER;

/**
 * 豆瓣电影详情页面
 */
public class MovieDetailActivity extends BaseActivity<MovieDetailPresenter> implements MovieDetailContract.View {

    @BindView(R.id.iv_base_titlebar_bg)
    ImageView ivBaseTitlebarBg;
    @BindView(R.id.tb_base_title)
    Toolbar tbBaseTitle;
    @BindView(R.id.rl_base_titlebar)
    RelativeLayout rlBaseTitlebar;
    @BindView(R.id.img_item_bg)
    ImageView imgItemBg;
    @BindView(R.id.iv_one_photo)
    ImageView ivOnePhoto;
    @BindView(R.id.tv_one_rating_rate)
    TextView tvOneRatingRate;
    @BindView(R.id.tv_one_rating_number)
    TextView tvOneRatingNumber;
    @BindView(R.id.tv_one_directors)
    TextView tvOneDirectors;
    @BindView(R.id.tv_one_casts)
    TextView tvOneCasts;
    @BindView(R.id.tv_one_genres)
    TextView tvOneGenres;
    @BindView(R.id.tv_one_day)
    TextView tvOneDay;
    @BindView(R.id.tv_one_city)
    TextView tvOneCity;
    @BindView(R.id.ll_one_item)
    LinearLayout llOneItem;
    @BindView(R.id.sl_base)
    MyNestedScrollView slBase;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    // 这个是高斯图背景的高度
    private int imageBgHeight;
    // 滑动多少距离后标题不透明
    private int slidingDistance;
    private MovieBean movieBean;
    private BaseQuickAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMovieDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .movieDetailModule(new MovieDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_movie_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        movieBean = (MovieBean) getIntent().getSerializableExtra(MOVIE_INFO);
        //高斯模糊
        GlideUtils.loadTransformationImage(MovieDetailActivity.this, imgItemBg, movieBean.getImages().getMedium(), 95);
        tvOneRatingRate.setText("评分:" + movieBean.getRating().getAverage());
        tvOneRatingNumber.setText(movieBean.getCollect_count() + "人评分");
        tvOneDirectors.setText(StringUtils.formatName(movieBean.getDirectors()));
        tvOneCasts.setText(StringUtils.formatName(movieBean.getCasts()));
        tvOneGenres.setText("类型:" + StringUtils.formatGenres(movieBean.getGenres()));
        tvOneDay.setText("上映日期：" + movieBean.getYear());
        tvOneCity.setText("制片国家/地区：" + StringUtils.formatGenres(movieBean.getCountries()));

        initSlideShapeTheme(movieBean.getImages().getMedium(), imgItemBg);
        setToolBar();
        GlideUtils.loadMovieImage(this, movieBean.getImages().getLarge(), ivOnePhoto);
        tbBaseTitle.setTitle(movieBean.getTitle());//标题
        tbBaseTitle.setSubtitle(String.format("主演：%s", StringUtils.formatName(movieBean.getCasts())));//副标题

        initRecycleView();
        if(movieBean!=null){
            assert mPresenter != null;
            mPresenter.getMovieDetail(movieBean.getId(),true);
        }
    }
    private void initRecycleView() {
        mAdapter=getAdapter();
        //解决滑动不流畅
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }
    @Override
    public Activity getActivity() {
        return this;
    }


    @Override
    public void showLoadSirView(int status) {

    }

    @Override
    public void onViewReload() {

    }
    @Override
    public void refreshUI(List data) {
        if (ObjectUtils.isNotEmpty(data)) {
            mAdapter.setNewData(data);
            mAdapter.setEnableLoadMore(true);
            if (data.size() < PRELOADNUMBER) {
                mAdapter.loadMoreEnd(true);
            }
        } else {
            //清空数据
            mAdapter.setNewData(new ArrayList());
            mAdapter.setEnableLoadMore(true);
            if (data.size() < PRELOADNUMBER) {
                mAdapter.loadMoreEnd(true);
            }
        }
        mRecyclerView.smoothScrollToPosition(0);
        hideLoading();
    }

    @Override
    public void updateUI(Object data) {

    }

    @Override
    public void deleteUI(Object data) {

    }

    @Override
    public void onError(String error) {
        hideLoading();
    }

    @Override
    public void loadMore(List data, boolean hasMore) {
        if (ObjectUtils.isEmpty(data)) {
            mAdapter.loadMoreEnd(true);
            hideLoading();
            return;
        }
        mAdapter.addData(data);
        if (!hasMore) {
            mAdapter.loadMoreEnd(true);
        }
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onDataRefresh() {

    }

    @Override
    public void onDataLoadMore() {

    }


    @Override
    public int getReHeaderView() {
        return 0;
    }

    @Override
    public int getReFooterView() {
        return 0;
    }

    @Override
    public boolean enableRefresh() {
        return false;
    }

    @Override
    public boolean enableMore() {
        return false;
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        return new MovieListAdapter(getActivity(),new ArrayList());
    }

    /**
     * *** 初始化滑动渐变 一定要实现 ******
     *
     * @param imgUrl    header头部的高斯背景imageUrl
     * @param mHeaderBg header头部高斯背景ImageView控件
     */
    protected void initSlideShapeTheme(String imgUrl, ImageView mHeaderBg) {
        setImgHeaderBg(imgUrl);
        // toolbar 的高
        int toolbarHeight = tbBaseTitle.getLayoutParams().height;
        final int headerBgHeight = toolbarHeight + StatusBarUtil.getStatusBarHeight(this);
        // 使背景图向上移动到图片的最低端，保留（titlebar+statusbar）的高度
        ViewGroup.LayoutParams params = ivBaseTitlebarBg.getLayoutParams();
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams) ivBaseTitlebarBg.getLayoutParams();
        int marginTop = params.height - headerBgHeight;
        //设置margins
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ivBaseTitlebarBg.setImageAlpha(0);
        }

        StatusBarUtil.setTranslucentImageHeader(this, 0, tbBaseTitle);

        // 上移背景图片，使空白状态栏消失(这样下方就空了状态栏的高度)
        if (mHeaderBg != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mHeaderBg.getLayoutParams();
            layoutParams.setMargins(0, -StatusBarUtil.getStatusBarHeight(this), 0, 0);

            ViewGroup.LayoutParams imgItemBgparams = mHeaderBg.getLayoutParams();
            // 获得高斯图背景的高度
            imageBgHeight = imgItemBgparams.height;
        }

        // 变色
        initScrollViewListener();
        initNewSlidingParams();
    }

    private void setToolBar() {
        setSupportActionBar(tbBaseTitle);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }
        // 手动设置才有效果
        tbBaseTitle.setTitleTextAppearance(this, R.style.ToolBar_Title);
        tbBaseTitle.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitle);
        tbBaseTitle.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.actionbar_more));
        tbBaseTitle.setNavigationOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
        });


    }

    /**
     * 加载titlebar背景
     */
    private void setImgHeaderBg(String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {
            //高斯模糊
            Glide.with(MovieDetailActivity.this)
                    .load(imgUrl)
                    .apply(new RequestOptions()
                            //占位图
                            .placeholder(R.drawable.stackblur_default)
                            //高斯模糊值
                            .transform(new BitmapTransformation() {
                                @Override
                                public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
                                }

                                @Override
                                protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                                    return ImageUtils.fastBlur(toTransform, 0.4f, 25);
                                }
                            }))
                    //加载监听
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            tbBaseTitle.setBackgroundColor(Color.TRANSPARENT);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                ivBaseTitlebarBg.setImageAlpha(0);
                            }
                            ivBaseTitlebarBg.setVisibility(View.VISIBLE);
                            return false;
                        }
                    }).into(ivBaseTitlebarBg);


        }
    }

    private void initScrollViewListener() {
        // 为了兼容23以下
        slBase.setOnScrollChangeListener(new MyNestedScrollView.ScrollInterface() {
            @Override
            public void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollChangeHeader(scrollY);
            }
        });
    }

    private void initNewSlidingParams() {
        int titleBarAndStatusHeight = (int) (ArmsUtils.getDimens(this, R.dimen.nav_bar_height) + StatusBarUtil.getStatusBarHeight(this));
        // 减掉后，没到顶部就不透明了
        slidingDistance = imageBgHeight - titleBarAndStatusHeight - (int) (ArmsUtils.getDimens(this, R.dimen.base_header_activity_slide_more));
    }

    /**
     * 根据页面滑动距离改变Header方法
     */
    private void scrollChangeHeader(int scrolledY) {
        if (scrolledY < 0) {
            scrolledY = 0;
        }
        float alpha = Math.abs(scrolledY) * 1.0f / (slidingDistance);

        Drawable drawable = ivBaseTitlebarBg.getDrawable();

        if (drawable == null) {
            return;
        }
        if (scrolledY <= slidingDistance) {
            // title部分的渐变
            drawable.mutate().setAlpha((int) (alpha * 255));
            ivBaseTitlebarBg.setImageDrawable(drawable);
        } else {
            drawable.mutate().setAlpha(255);
            ivBaseTitlebarBg.setImageDrawable(drawable);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mAdapter.loadMoreComplete();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    /**
     * @param context      activity
     * @param positionData bean
     * @param imageView    imageView
     */
    public static void startActivity(Activity context, MovieBean positionData, ImageView imageView) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MOVIE_INFO, positionData);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        imageView, ArmsUtils.getString(context, R.string.transition_movie_img));//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

}
