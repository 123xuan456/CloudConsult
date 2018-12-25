package com.mtm.cloudconsult.mvp.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.base.BaseListFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BaseListFragment {


    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return null;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void setData(@Nullable Object o) {

    }
}
