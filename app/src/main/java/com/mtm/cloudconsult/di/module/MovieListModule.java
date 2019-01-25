package com.mtm.cloudconsult.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.mtm.cloudconsult.mvp.contract.MovieListContract;
import com.mtm.cloudconsult.mvp.model.MovieListModel;

import dagger.Module;
import dagger.Provides;


@Module
public class MovieListModule {
    private MovieListContract.View view;

    /**
     * 构建MovieListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MovieListModule(MovieListContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    MovieListContract.View provideMovieListView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    MovieListContract.Model provideMovieListModel(MovieListModel model) {
        return model;
    }
}