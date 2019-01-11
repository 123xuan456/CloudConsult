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
import com.mtm.cloudconsult.di.component.DaggerTFriendComponent;
import com.mtm.cloudconsult.di.module.TFriendModule;
import com.mtm.cloudconsult.mvp.contract.TFriendContract;
import com.mtm.cloudconsult.mvp.presenter.TFriendPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 朋友页面
 */
public class TFriendFragment extends BaseFragment<TFriendPresenter> implements TFriendContract.View {

    public static TFriendFragment newInstance() {
        TFriendFragment fragment = new TFriendFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTFriendComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .tFriendModule(new TFriendModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tfriend, container, false);
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
