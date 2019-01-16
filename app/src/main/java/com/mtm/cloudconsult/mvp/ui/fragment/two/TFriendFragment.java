package com.mtm.cloudconsult.mvp.ui.fragment.two;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.adapter.TFriendAdapter;
import com.mtm.cloudconsult.app.base.BaseRecyFragment;
import com.mtm.cloudconsult.di.component.DaggerTFriendComponent;
import com.mtm.cloudconsult.di.module.TFriendModule;
import com.mtm.cloudconsult.mvp.contract.TFriendContract;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDataBean;
import com.mtm.cloudconsult.mvp.presenter.TFriendPresenter;
import com.mtm.cloudconsult.mvp.ui.activity.ViewBigImageActivity;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 朋友页面
 */
public class TFriendFragment extends BaseRecyFragment<TFriendPresenter> implements TFriendContract.View , OnLoadMoreListener, OnRefreshListener {
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private int currentPage = 1;
    private TFriendAdapter tFriendAdapter;
    private boolean isLoadingMore = false;
    private ArrayList<String> imgList = new ArrayList<>();
    private ArrayList<String> imgTitleList = new ArrayList<>();
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
    public int getContentViewId() {
        return R.layout.fragment_base_recy;
    }

    @Override
    public void findView(View rootView) {
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        final MaterialHeader materialHeader = (MaterialHeader) rootView.findViewById(R.id.header);
        materialHeader.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);
        //提升滑动流畅度
        recyclerView.setNestedScrollingEnabled(false);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(false);
        //瀑布流列表
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //解决item跳动
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(manager);
        tFriendAdapter = new TFriendAdapter(R.layout.item_tfriend_android, new ArrayList<>());
        recyclerView.setAdapter(tFriendAdapter);
        tFriendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ViewBigImageActivity.startImageList(getContext(), position, imgList, imgTitleList);
            }
        });

    }
    /**
     * 获取数据
     *
     * @param isRefresh false 读取缓存
     *                  true 读取网络
     */
    private void getGanData(Boolean isRefresh) {
        /**
         * false ：有缓存读取缓存数据
         * true : 读取新数据
         */
        boolean isEvictCache = false;
        if (mPresenter != null) {
            if (isRefresh) {
                if (isLoadingMore) {
                    isEvictCache = true;
                }
                currentPage = 1;
            } else {
                currentPage = mPresenter.getPage();
                currentPage++;
            }
            mPresenter.setPage(currentPage);
            mPresenter.loadCustomData(isEvictCache);
        }
        isLoadingMore = false;
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getGanData(true);
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
    public void setGankIoData(GankIoDataBean bean) {
        assert mPresenter != null;
        if (mPresenter.getPage() == 1) {
            tFriendAdapter.setNewData(bean.getResults());
            refreshLayout.finishRefresh();
        } else {
            tFriendAdapter.addData(bean.getResults());
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void showListNoMoreLoading() {
        //完成加载，标记没有更多数据
        refreshLayout.finishLoadMoreWithNoMoreData();
    }

    /**
     * 访问出错
     */
    @Override
    public void showListError() {
        isLoadingMore = false;
        refreshLayout.finishLoadMore(false);
        refreshLayout.finishRefresh(false);
    }

    @Override
    public void setImageList(ArrayList<ArrayList<String>> arrayLists) {
        if (arrayLists != null && arrayLists.size() == 2) {
            imgList.addAll(arrayLists.get(0));
            imgTitleList.addAll(arrayLists.get(1));
        }
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getGanData(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isLoadingMore = true;
        getGanData(true);

    }
}
