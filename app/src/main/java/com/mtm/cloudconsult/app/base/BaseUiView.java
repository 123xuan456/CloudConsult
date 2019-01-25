package com.mtm.cloudconsult.app.base;

import android.view.View;

import com.jess.arms.mvp.IView;

/**
 * Created by MTM on 2019/1/14.
 *
 * @author QSX
 */
public interface BaseUiView extends IView {
    void findView(View rootView);

    void showLoadSirView(int status);

    int getContentViewId();

}
