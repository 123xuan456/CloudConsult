package com.mtm.cloudconsult.app.utils;

import com.blankj.utilcode.util.NetworkUtils;
import com.jess.arms.utils.ArmsUtils;
import com.mtm.cloudconsult.app.api.CloudConstant;
import com.mtm.cloudconsult.app.base.BaseRecycleIView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.annotations.NonNull;
import retrofit2.HttpException;

public class NetWorkUtils {
    public static void onListError(@NonNull Throwable e, BaseRecycleIView view, boolean pullToRefresh){
        if (e instanceof UnknownHostException || e instanceof SocketTimeoutException || e instanceof HttpException ||e instanceof ConnectException) {
            if(pullToRefresh){
                //判断网络是否连接
                if(!NetworkUtils.isConnected()){
                    view.showLoadSirView(CloudConstant.LoadSir.NO_NETWORK);
                }else{
                    //网络异常
                    view.showLoadSirView(CloudConstant.LoadSir.TIMEOUT);
                }
            }else{
                ArmsUtils.snackbarText("网络错误");
            }
        } else {
            view.showLoadSirView(CloudConstant.LoadSir.ERROR);
        }
        view.onError(e.getMessage());
    }
}
