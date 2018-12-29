package com.mtm.cloudconsult.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 中间页面
 */
public class TwoFragment extends Fragment {
    @BindView(R.id.tab_gank)
    TabLayout tabGank;
    @BindView(R.id.vp_gank)
    ViewPager vpGank;
    Unbinder unbinder;
    private ArrayList<String> mTitleList = new ArrayList<>(3);
    private ArrayList<Fragment> mFragments = new ArrayList<>(3);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        unbinder = ButterKnife.bind(this, view);
        initFragmentList();
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        vpGank.setAdapter(myAdapter);
        vpGank.setOffscreenPageLimit(2);
        tabGank.setTabMode(TabLayout.MODE_FIXED);
        tabGank.setupWithViewPager(vpGank);
        return view;
    }

    private void initFragmentList() {
        mTitleList.clear();
        mTitleList.add("推荐");
        mTitleList.add("朋友");
        mTitleList.add("电台");
        mFragments.add(new TRecommendFragment());
        mFragments.add(new TFriendFragment());
        mFragments.add(new TRadioFragment());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
