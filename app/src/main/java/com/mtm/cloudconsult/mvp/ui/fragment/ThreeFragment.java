package com.mtm.cloudconsult.mvp.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.adapter.MyFragmentPagerAdapter;
import com.mtm.cloudconsult.app.api.CloudConstant;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 第三个页面
 */
public class ThreeFragment extends Fragment {
    String[] titles = {"正在上映", "即将上映", "新片榜", "口碑榜", "Top250", "北美票房榜"};
    @BindView(R.id.tab_douban)
    TabLayout tabDouban;
    @BindView(R.id.vp_douban)
    ViewPager vpDouban;
    Unbinder unbinder;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        unbinder = ButterKnife.bind(this, view);
        initFragmentList();
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, Arrays.asList(titles));
        vpDouban.setAdapter(myAdapter);
        vpDouban.setOffscreenPageLimit(2);
        tabDouban.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabDouban.setupWithViewPager(vpDouban);
        return view;
    }

    private void initFragmentList() {
        mFragments.add(MovieListFragment.newInstance(CloudConstant.MOVIE_LIST_DEFAULT, "in_theaters"));
        mFragments.add(MovieListFragment.newInstance(CloudConstant.MOVIE_LIST_DEFAULT, "coming_soon"));
        mFragments.add(MovieListFragment.newInstance(CloudConstant.MOVIE_LIST_DEFAULT, "new_movies"));
        mFragments.add(MovieListFragment.newInstance(CloudConstant.MOVIE_LIST_US_BOX, "weekly"));
        mFragments.add(MovieListFragment.newInstance(CloudConstant.MOVIE_LIST_DEFAULT, "top250"));
        mFragments.add(MovieListFragment.newInstance(CloudConstant.MOVIE_LIST_US_BOX, "ux_box"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
