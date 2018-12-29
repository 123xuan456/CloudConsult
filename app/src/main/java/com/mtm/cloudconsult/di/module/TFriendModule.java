package com.mtm.cloudconsult.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.mtm.cloudconsult.mvp.contract.TFriendContract;
import com.mtm.cloudconsult.mvp.model.TFriendModel;


@Module
public class TFriendModule {
    private TFriendContract.View view;

    /**
     * 构建TFriendModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TFriendModule(TFriendContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    TFriendContract.View provideTFriendView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    TFriendContract.Model provideTFriendModel(TFriendModel model) {
        return model;
    }
}