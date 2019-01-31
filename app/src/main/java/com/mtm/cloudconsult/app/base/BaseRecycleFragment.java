package com.mtm.cloudconsult.app.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.api.CloudConstant;

import java.util.ArrayList;
import java.util.List;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mtm.cloudconsult.app.api.CloudConstant.PRELOADNUMBER;

/**
 * 列表刷新基类
 */
public abstract class BaseRecycleFragment <T, P extends IPresenter> extends BaseUiFragment<P> implements BaseRecycleIView<T>, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener{
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseQuickAdapter mAdapter;
    protected View mYeHeaderView;
    protected View mYeFooterView;
    public boolean isPrepared=false;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }
    @Override
    public int getContentViewId() {
        return R.layout.fragment_base_recycle;
    }
    @Override
    public void findView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycle);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeLayout);
        onInitView();
    }
    public void onInitView() {
        mAdapter = getAdapter();
        if(getReHeaderView()!=0){
            mYeHeaderView = LayoutInflater.from(getActivity()).inflate(getReHeaderView(), null, false);
            mAdapter.addHeaderView(mYeHeaderView);
        }
        if(getReFooterView()!=0){
            mYeFooterView = LayoutInflater.from(getActivity()).inflate(getReFooterView(), null, false);
            mAdapter.addFooterView(mYeFooterView);
        }
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mSwipeRefreshLayout.setEnabled(enableRefresh());
        if(enableRefresh()){
            mSwipeRefreshLayout.setOnRefreshListener(this);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);
        }
        if(enableMore()){
            mAdapter.setEnableLoadMore(true);
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        }
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(),  R.anim.layout_animation_fall_down);
        mRecyclerView.setLayoutAnimation(animation);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public int getReHeaderView() {
        return 0;
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
        if(data!=null && mAdapter!=null && mAdapter.getData()!=null && mAdapter.getData().size()>0){
            int postion = mAdapter.getData().indexOf(data);
            if(postion>-1){
                mAdapter.setData(postion,data);
            }
        }
    }

    @Override
    public void deleteUI(T data) {
        if(data!=null && mAdapter!=null && mAdapter.getData()!=null && mAdapter.getData().size()>0){
            int postion = mAdapter.getData().indexOf(data);
            if(postion>-1){
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
    public void onViewReload() {
        showLoadSirView(CloudConstant.LoadSir.LOADING);
        onDataRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /***
     * 监听Fragment显示隐藏
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
            } else {
                onUserInvisible();
            }
        }
    }
    protected  void onUserVisible(){
    }

    protected void onUserInvisible(){

    }
    public void resume() {
        setUserVisibleHint(true);
    }
}
