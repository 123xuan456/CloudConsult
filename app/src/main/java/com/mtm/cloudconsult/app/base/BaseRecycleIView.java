package com.mtm.cloudconsult.app.base;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.mvp.IView;

import java.util.List;

/**
 * recycle 接口基类
 */
public interface BaseRecycleIView<T> extends IView {
    Activity getActivity();

    View getLoadView();

    void showLoadSirView(int status);

    void onViewReload();

    void initView(View mRootView);

    int getContentViewId();

    void refreshUI(List<T> data);

    void updateUI(T data);

    void deleteUI(T data);

    void onError(String error);

    void loadMore(List<T> data, boolean hasMore);

    void onDataRefresh();

    void onDataLoadMore();

    void startLoadMore();

    void endLoadMore();

    int getReHeaderView();

    int getReFooterView();

    boolean enableRefresh();

    boolean enableMore();

    BaseQuickAdapter<T, BaseViewHolder> getAdapter();

    RecyclerView.LayoutManager getLayoutManager();


}
