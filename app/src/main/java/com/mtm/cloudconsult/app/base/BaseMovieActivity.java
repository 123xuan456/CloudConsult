package com.mtm.cloudconsult.app.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.api.CloudConstant;
import com.mtm.cloudconsult.app.callback.ErrorCallback;
import com.mtm.cloudconsult.app.callback.LoadingCallback;
import com.mtm.cloudconsult.app.view.MyNestedScrollView;
import com.mtm.cloudconsult.app.view.statusbar.StatusBarUtil;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 电影详情页面
 *
 * @param <P>
 */
public abstract class BaseMovieActivity<T, P extends IPresenter> extends BaseActivity<P> implements BaseRecycleIView<T>, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {


    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_base_titlebar_bg)
    ImageView ivBaseTitlebarBg;
    @BindView(R.id.tb_base_title)
    Toolbar tbBaseTitle;
    @BindView(R.id.rl_base_titlebar)
    RelativeLayout rlBaseTitlebar;
    @BindView(R.id.sl_base)
    MyNestedScrollView slBase;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    // 这个是高斯图背景的高度
    private int imageBgHeight;
    // 滑动多少距离后标题不透明
    private int slidingDistance;
    protected int PRELOADNUMBER = 10;
    private BaseQuickAdapter mAdapter;
    private LoadService loadService;
    public boolean isPrepared = false;


    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_movie_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setToolBar();
        loadService = LoadSir.getDefault().register(slBase, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                // 点击事件
                onViewReload();
            }
        });
        showLoadSirView(CloudConstant.LoadSir.LOADING);
        mAdapter = getAdapter();
        if (getReHeaderView() != 0) {
            mAdapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(getReHeaderView(), null, false));
        }
        if (getReFooterView() != 0) {
            mAdapter.addFooterView(LayoutInflater.from(getActivity()).inflate(getReFooterView(), null, false));
        }
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mSwipeRefreshLayout.setEnabled(enableRefresh());
        if (enableRefresh()) {
            mSwipeRefreshLayout.setOnRefreshListener(this);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);
        }
        if (enableMore()) {
            mAdapter.setEnableLoadMore(true);
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        }
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);
        mRecyclerView.setLayoutAnimation(animation);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
    }


    public void setTabTitle(String title) {
        tbBaseTitle.setTitle(title);
    }

    public void setTabSubtitle(String title) {
        tbBaseTitle.setSubtitle(title);
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
            Glide.with(BaseMovieActivity.this)
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
    public void showLoadSirView(int status) {
        if (loadService != null) {
            switch (status) {
                case CloudConstant.LoadSir.SUCCESS:
                    //加载完成
                    loadService.showSuccess();
                    break;

                case CloudConstant.LoadSir.ERROR:
                    //加载失败
                    loadService.showCallback(ErrorCallback.class);
                    break;
                case CloudConstant.LoadSir.EMPTY:
                    //数据为空
                    loadService.showCallback(ErrorCallback.class);
                    break;

                case CloudConstant.LoadSir.LOADING:
                    //加载中
                    loadService.showCallback(LoadingCallback.class);
                    break;
                default:
            }
        }
    }

    @Override
    public int getReFooterView() {
        return 0;
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }


    private synchronized void initPrepare() {
        if (isPrepared) {
            onDataRefresh();
        } else {
            isPrepared = true;
        }
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.loadMoreComplete();
    }



    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        //清空所有数据
        onDataRefresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        onDataLoadMore();
    }

    @Override
    public void refreshUI(List<T> data) {
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
    public void updateUI(T data) {
        if (data != null && mAdapter != null && mAdapter.getData() != null && mAdapter.getData().size() > 0) {
            int postion = mAdapter.getData().indexOf(data);
            if (postion > -1) {
                mAdapter.setData(postion, data);
            }
        }
    }

    @Override
    public void deleteUI(T data) {
        if (data != null && mAdapter != null && mAdapter.getData() != null && mAdapter.getData().size() > 0) {
            int postion = mAdapter.getData().indexOf(data);
            if (postion > -1) {
                mAdapter.remove(postion);
            }
        }
    }

    @Override
    public void loadMore(List<T> data, boolean hasMore) {
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
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onError(String error) {
        hideLoading();
    }


    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public BaseQuickAdapter getRecyclerAdapter() {
        return mAdapter;
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
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {


    }

    @Override
    public void onViewReload() {
        showLoadSirView(CloudConstant.LoadSir.LOADING);
        onDataRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
