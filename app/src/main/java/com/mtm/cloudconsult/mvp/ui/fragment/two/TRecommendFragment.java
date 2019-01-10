package com.mtm.cloudconsult.mvp.ui.fragment.two;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.adapter.TRecommendAdapter;
import com.mtm.cloudconsult.app.utils.GlideImageLoader;
import com.mtm.cloudconsult.app.utils.StringUtils;
import com.mtm.cloudconsult.di.component.DaggerTRecommendComponent;
import com.mtm.cloudconsult.di.module.TRecommendModule;
import com.mtm.cloudconsult.mvp.contract.TRecommendContract;
import com.mtm.cloudconsult.mvp.model.bean.AndroidBean;
import com.mtm.cloudconsult.mvp.model.bean.FrontpageBean;
import com.mtm.cloudconsult.mvp.presenter.TRecommendPresenter;
import com.mtm.cloudconsult.mvp.ui.activity.WebViewActivity;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;
import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 推荐页面
 */
public class TRecommendFragment extends BaseFragment<TRecommendPresenter> implements TRecommendContract.View {
    private TRecommendAdapter tRecommendAdapter;
    private Banner banner;
    private boolean isLoadBanner;
    private RecyclerView recyclerView;

    public static TRecommendFragment newInstance() {
        TRecommendFragment fragment = new TRecommendFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTRecommendComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .tRecommendModule(new TRecommendModule(this))
                .build()
                .inject(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trecommend, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final RefreshLayout refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        final MaterialHeader materialHeader = (MaterialHeader) view.findViewById(R.id.header);
        materialHeader.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);
        //提升滑动流畅度
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tRecommendAdapter=new TRecommendAdapter(new ArrayList<>());
        recyclerView.setAdapter(tRecommendAdapter);
        if (mPresenter != null) {
            mPresenter.showBannerPage(1,true);
            String year = StringUtils.getTodayTime().get(0);
            String month = StringUtils.getTodayTime().get(1);
            String day = StringUtils.getTodayTime().get(2);
            mPresenter.showRecyclerViewData(year,month,day);
        }
        //添加Header
         View header = LayoutInflater.from(getContext()).inflate(R.layout.header_item_everyday, recyclerView, false);
        banner=header.findViewById(R.id.banner);
        tRecommendAdapter.addHeaderView(header);
        tRecommendAdapter.openLoadAnimation();
        return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {



    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    }
    @Override
    public void onResume() {
        super.onResume();
        // 失去焦点，否则RecyclerView第一个item会回到顶部
        recyclerView.setFocusable(false);
        // 开始图片请求
        Glide.with(getActivity()).resumeRequests();
        if (isLoadBanner) {
            banner.startAutoPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // 停止全部图片请求 跟随着Activity
        Glide.with(getActivity()).pauseRequests();
        // 不可见时轮播图停止滚动
        if (isLoadBanner) {
            banner.stopAutoPlay();
        }
    }
    /**
     * 设置banner
     * @param mBannerImages
     * @param result
     */
    @Override
    public void showBannerView(ArrayList<String> mBannerImages, ArrayList<FrontpageBean.ResultBannerBean.FocusBean.ResultBeanX> result) {
        banner.setImages(mBannerImages).setImageLoader(new GlideImageLoader()).start();
        if (result != null) {
            banner.setOnBannerListener(position -> {
                if (result.get(position) != null && result.get(position).getCode() != null
                        && result.get(position).getCode().startsWith("http")) {
                    WebViewActivity.loadUrl(getContext(), result.get(position).getCode(), "加载中...");
                }
            });
        }
        isLoadBanner = true;
    }

    /**
     * 列表数据
     * @param lists
     */
    @Override
    public void showListView(ArrayList<List<AndroidBean>> lists) {
        tRecommendAdapter.setNewData(lists);
    }
}
