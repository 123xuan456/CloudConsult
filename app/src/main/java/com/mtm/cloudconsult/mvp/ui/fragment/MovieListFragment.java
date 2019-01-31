package com.mtm.cloudconsult.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mtm.cloudconsult.app.adapter.MovieListAdapter;
import com.mtm.cloudconsult.app.api.CloudConstant;
import com.mtm.cloudconsult.app.base.BaseEntityBean;
import com.mtm.cloudconsult.app.base.BaseRecycleFragment;
import com.mtm.cloudconsult.di.component.DaggerMovieListComponent;
import com.mtm.cloudconsult.di.module.MovieListModule;
import com.mtm.cloudconsult.mvp.contract.MovieListContract;
import com.mtm.cloudconsult.mvp.model.bean.movie.MoviePhotoRequest;
import com.mtm.cloudconsult.mvp.presenter.MovieListPresenter;
import com.mtm.cloudconsult.mvp.ui.activity.ViewBigImageActivity;

import java.util.ArrayList;
import java.util.List;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * @author QSX
 * @create 2019/1/21
 * @Describe three 列表
 */
public class MovieListFragment extends BaseRecycleFragment<BaseEntityBean, MovieListPresenter> implements MovieListContract.View<BaseEntityBean> {
    private int type;
    private String extend;
    private ArrayList<String> imgTitleList;
    private ArrayList<String> imgList;

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        return fragment;
    }

    public static MovieListFragment newInstance(int type, String extend) {
        MovieListFragment fragment = new MovieListFragment();
        fragment.type = type;
        fragment.extend = extend;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMovieListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .movieListModule(new MovieListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getData(true);
    }

    @Override
    public void setData(@Nullable Object data) {

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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<MoviePhotoRequest.PhotosBean> photosBean = adapter.getData();
                imgList = new ArrayList<>();
                imgTitleList = new ArrayList<>();
                for (int i = 0; i < photosBean.size(); i++) {
                    imgList.add(photosBean.get(i).getCover());
                    imgTitleList.add(photosBean.get(i).getId());
                }
                ViewBigImageActivity.startImageList(getContext(), position, imgList, imgTitleList);
            }
        });

    }

    public void refreshSearch(String query) {
        this.extend = query;
        if (isPrepared) {
            onDataRefresh();
        }
    }

    @Override
    public void onDataRefresh() {
        getData(true);
    }

    @Override
    public void onDataLoadMore() {
        getData(false);
    }

    @Override
    public boolean enableRefresh() {
        boolean isRefresh = true;
        switch (type) {
            case CloudConstant.MOVIE_LIST_SEARCH_QUERY:
            case CloudConstant.MOVIE_LIST_SEARCH_TAG:
                isRefresh = false;
                break;
        }
        return isRefresh;
    }

    @Override
    public boolean enableMore() {
        boolean isMore = true;
        switch (type) {
            case CloudConstant.MOVIE_LIST_US_BOX:
                isMore = false;
                break;
        }
        return isMore;
    }

    @Override
    public BaseQuickAdapter<BaseEntityBean, BaseViewHolder> getAdapter() {
        return new MovieListAdapter(getActivity(), new ArrayList<>());
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        int count = 1;
        switch (type) {
            case CloudConstant.MOVIE_LIST_DEFAULT:
                break;
            case CloudConstant.MOVIE_PHOTOS_LIST:
                count = 3;
                break;
        }
        GridLayoutManager manager = new GridLayoutManager(getActivity(), count);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    public void getData(boolean pullToRefresh) {
        switch (type) {
            case CloudConstant.MOVIE_LIST_DEFAULT:
                mPresenter.getData(extend, pullToRefresh);
                break;
            case CloudConstant.MOVIE_LIST_US_BOX:
                mPresenter.getMovieUsBoxRequest(extend);
                break;
            case CloudConstant.MOVIE_PHOTOS_LIST:
                mPresenter.getMovieTypeList(extend, "photos", pullToRefresh);
                break;
            case CloudConstant.MOVIE_COMMENT_DEFAULT:
                mPresenter.getMovieTypeList(extend, "comments", pullToRefresh);
                break;
            case CloudConstant.MOVIE_COMMENT_REVIEW:
                mPresenter.getMovieTypeList(extend, "reviews", pullToRefresh);
                break;
            case CloudConstant.MOVIE_LIST_SEARCH_QUERY:
                mPresenter.getMovieSearchQuery(extend, pullToRefresh);
                break;
            case CloudConstant.MOVIE_LIST_SEARCH_TAG:
                mPresenter.getMovieSearchTag(extend, pullToRefresh);
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
