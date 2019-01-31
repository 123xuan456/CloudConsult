package com.mtm.cloudconsult.app.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.IPresenter;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.api.CloudConstant;
import com.mtm.cloudconsult.app.callback.ErrorCallback;
import com.mtm.cloudconsult.app.callback.LoadingCallback;

/**
 * Created by MTM on 2019/1/14.
 *
 * @author QSX
 */
public abstract class BaseUiFragment<P extends IPresenter> extends BaseFragment<P> implements BaseUiView {


    private LinearLayout mLlContent;
    private LoadService loadService;

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_base_ui, container, false);
        inflater.inflate(getContentViewId(), (LinearLayout) rootView.findViewById(R.id.base_content), true);
        mLlContent = (LinearLayout) rootView.findViewById(R.id.base_content);
        findView(rootView);
        if (getLoadView() != null) {
            loadService = LoadSir.getDefault().register(getLoadView(), new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    // 点击事件
                    initData(savedInstanceState);
                }
            });
            showLoadSirView(CloudConstant.LoadSir.LOADING);
        }
        return rootView;
    }
    public View getLoadView() {
        return mLlContent;
    }


    @Override
    public void showLoadSirView(int status) {
        if(loadService!=null){
            switch (status){
                case CloudConstant.LoadSir.SUCCESS:
                    //加载完成
                    loadService.showSuccess();
                    break;

                case CloudConstant.LoadSir.ERROR:
                    //加载失败
                    loadService.showCallback(ErrorCallback.class);
                    break;
                case CloudConstant.LoadSir.EMPTY:
                    //数据为空
                     loadService.showCallback(ErrorCallback.class);
                    break;

                case CloudConstant.LoadSir.LOADING:
                    //加载中
                    loadService.showCallback(LoadingCallback.class);
                    break;
                default:
            }
        }
    }

}
