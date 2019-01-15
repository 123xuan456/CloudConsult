package com.mtm.cloudconsult.mvp.contract;

import android.view.View;

import com.jess.arms.mvp.IView;

/**
 * Created by MTM on 2019/1/14.
 *
 * @author QSX
 */
public interface BaseRecyIView extends IView {
    void findView(View rootView);

    void showLoadSirView(int status);

    int getContentViewId();

}
