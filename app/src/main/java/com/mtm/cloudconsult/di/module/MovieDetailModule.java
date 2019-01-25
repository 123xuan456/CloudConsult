package com.mtm.cloudconsult.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mtm.cloudconsult.mvp.contract.MovieDetailContract;
import com.mtm.cloudconsult.mvp.model.MovieDetailModel;


@Module
public class MovieDetailModule {
    private MovieDetailContract.View view;

    /**
     * 构建MovieDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MovieDetailModule(MovieDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MovieDetailContract.View provideMovieDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MovieDetailContract.Model provideMovieDetailModel(MovieDetailModel model) {
        return model;
    }
}