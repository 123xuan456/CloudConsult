package com.mtm.cloudconsult.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.mtm.cloudconsult.mvp.contract.TRecommendContract;
import com.mtm.cloudconsult.mvp.model.TRecommendModel;


@Module
public class TRecommendModule {
    private TRecommendContract.View view;

    /**
     * 构建TRecommendModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TRecommendModule(TRecommendContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    TRecommendContract.View provideTRecommendView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    TRecommendContract.Model provideTRecommendModel(TRecommendModel model) {
        return model;
    }
}