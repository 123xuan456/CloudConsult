package com.mtm.cloudconsult.mvp.ui.fragment.two;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.adapter.TRecommendAdapter;
import com.mtm.cloudconsult.di.component.DaggerTRecommendComponent;
import com.mtm.cloudconsult.di.module.TRecommendModule;
import com.mtm.cloudconsult.mvp.contract.TRecommendContract;
import com.mtm.cloudconsult.mvp.presenter.TRecommendPresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.Objects;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;
import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 推荐页面
 */
public class TRecommendFragment extends BaseFragment<TRecommendPresenter> implements TRecommendContract.View {
    private TRecommendAdapter tRecommendAdapter;
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

    @SuppressLint("ResourceAsColor")
    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trecommend, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final RefreshLayout refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);

        refreshLayout.setPrimaryColorsId(R.color.colorAccent, R.color.colorPrimaryDark);
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tRecommendAdapter=new TRecommendAdapter();
        recyclerView.setAdapter(tRecommendAdapter);
        if (mPresenter != null) {
            mPresenter.showBannerPage();
        }
        //添加Header
         View header = LayoutInflater.from(getContext()).inflate(R.layout.header_item_everyday, recyclerView, false);
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
}
