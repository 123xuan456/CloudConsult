package com.mtm.cloudconsult.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.mtm.cloudconsult.mvp.contract.TRadioContract;
import com.mtm.cloudconsult.mvp.model.TRadioModel;


@Module
public class TRadioModule {
    private TRadioContract.View view;

    /**
     * 构建TRadioModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TRadioModule(TRadioContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    TRadioContract.View provideTRadioView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    TRadioContract.Model provideTRadioModel(TRadioModel model) {
        return model;
    }
}