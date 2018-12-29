package com.mtm.cloudconsult.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.di.component.DaggerTRecommendComponent;
import com.mtm.cloudconsult.di.module.TRecommendModule;
import com.mtm.cloudconsult.mvp.contract.TRecommendContract;
import com.mtm.cloudconsult.mvp.presenter.TRecommendPresenter;


import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 推荐页面
 */
public class TRecommendFragment extends BaseFragment<TRecommendPresenter> implements TRecommendContract.View {

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

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trecommend, container, false);
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
