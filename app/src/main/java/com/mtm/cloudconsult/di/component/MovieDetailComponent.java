package com.mtm.cloudconsult.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mtm.cloudconsult.di.module.MovieDetailModule;
import com.mtm.cloudconsult.mvp.ui.activity.movie.MovieCelebrityActivity;
import com.mtm.cloudconsult.mvp.ui.activity.movie.MovieDetailActivity;

import dagger.Component;

@ActivityScope
@Component(modules = MovieDetailModule.class, dependencies = AppComponent.class)
public interface MovieDetailComponent {
    void inject(MovieDetailActivity activity);
    void inject(MovieCelebrityActivity activity);
}