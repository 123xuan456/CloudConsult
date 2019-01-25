package com.mtm.cloudconsult.mvp.ui.fragment.two;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.adapter.TRadioAdapter;
import com.mtm.cloudconsult.app.base.BaseUiFragment;
import com.mtm.cloudconsult.app.utils.SPUtils;
import com.mtm.cloudconsult.di.component.DaggerTRadioComponent;
import com.mtm.cloudconsult.di.module.TRadioModule;
import com.mtm.cloudconsult.mvp.contract.TRadioContract;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDataBean;
import com.mtm.cloudconsult.mvp.presenter.TRadioPresenter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Objects;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;
import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mtm.cloudconsult.app.api.CloudConstant.GANK_CALA;

/**
 * 电台模块
 */
public class TRadioFragment extends BaseUiFragment<TRadioPresenter> implements TRadioContract.View, OnLoadMoreListener, OnRefreshListener {


    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private int currentPage = 1;
    private View mHeaderView;
    private TRadioAdapter mGankAndroidAdapter;
    private BottomSheet.Builder builder = null;
    private boolean isLoadingMore = false;

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
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mGankAndroidAdapter = new TRadioAdapter(R.layout.item_tradio_android, new ArrayList<>());
        recyclerView.setAdapter(mGankAndroidAdapter);
        if (mHeaderView == null) {
            mHeaderView = View.inflate(getContext(), R.layout.header_item_gank_custom, null);
            mGankAndroidAdapter.addHeaderView(mHeaderView);
        }
        initHeader(mHeaderView);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getGanData(true);
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
        if (mPresenter.getPage() == 1) {
            boolean isAll = SPUtils.getString(GANK_CALA, "全部").equals("全部");
            mGankAndroidAdapter.replaceData(bean.getResults());
            mGankAndroidAdapter.setAllType(isAll);
            mGankAndroidAdapter.setNewData(bean.getResults());
            refreshLayout.finishRefresh();
        } else {
            mGankAndroidAdapter.addData(bean.getResults());
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

    private void initHeader(View mHeaderView) {
        final TextView txName = (TextView) mHeaderView.findViewById(R.id.tx_name);
        String gankCala = SPUtils.getString(GANK_CALA, "全部");
        txName.setText(gankCala);
        try {
            builder = new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog)
                    .title("选择分类")
                    .sheet(R.menu.gank_bottomsheet)
                    .listener((dialog, which) -> {
                        switch (which) {
                            case R.id.gank_all:
                                if (isOtherType("全部")) {
                                    changeContent(txName, "全部");
                                }
                                break;
                            case R.id.gank_android:
                                if (isOtherType("Android")) {
                                    changeContent(txName, "Android");
                                }
                                break;
                            case R.id.gank_ios:
                                if (isOtherType("IOS")) {
                                    changeContent(txName, "IOS");
                                }
                                break;
                            case R.id.gank_qian:
                                if (isOtherType("前端")) {
                                    changeContent(txName, "前端");
                                }
                                break;
                            case R.id.gank_app:
                                if (isOtherType("App")) {
                                    changeContent(txName, "App");
                                }
                                break;
                            case R.id.gank_movie:
                                if (isOtherType("休息视频")) {
                                    changeContent(txName, "休息视频");
                                }
                                break;
                            case R.id.gank_resouce:
                                if (isOtherType("拓展资源")) {
                                    changeContent(txName, "拓展资源");
                                }
                                break;
                            default:
                                break;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        View view = mHeaderView.findViewById(R.id.ll_choose_catalogue);
        view.setOnClickListener(v -> {
            if (builder != null) {
                builder.show();
            }
        });

    }

    private boolean isOtherType(String selectType) {
        String clickText = SPUtils.getString(GANK_CALA, "全部");
        if (clickText.equals(selectType)) {
            ArmsUtils.snackbarText("当前已经是" + selectType + "分类");
            return false;
        } else {
            // 重置XRecyclerView状态，解决 如出现刷新到底无内容再切换其他类别后，无法上拉加载的情况
            return true;
        }
    }

    private void changeContent(TextView textView, String content) {
        textView.setText(content);
        mPresenter.setPage(1);
        SPUtils.putString(GANK_CALA, content);
        getGanData(true);
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
