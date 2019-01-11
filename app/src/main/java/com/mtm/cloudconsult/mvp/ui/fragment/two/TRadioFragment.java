package com.mtm.cloudconsult.mvp.ui.fragment.two;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.di.component.DaggerTRadioComponent;
import com.mtm.cloudconsult.di.module.TRadioModule;
import com.mtm.cloudconsult.mvp.contract.TRadioContract;
import com.mtm.cloudconsult.mvp.presenter.TRadioPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class TRadioFragment extends BaseFragment<TRadioPresenter> implements TRadioContract.View {

    public static TRadioFragment newInstance() {
        TRadioFragment fragment = new TRadioFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTRadioComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .tRadioModule(new TRadioModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tradio, container, false);
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
