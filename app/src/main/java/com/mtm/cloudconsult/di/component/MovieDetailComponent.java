package com.mtm.cloudconsult.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mtm.cloudconsult.di.module.MovieDetailModule;

import com.jess.arms.di.scope.ActivityScope;
import com.mtm.cloudconsult.mvp.ui.activity.MovieDetailActivity;

@ActivityScope
@Component(modules = MovieDetailModule.class, dependencies = AppComponent.class)
public interface MovieDetailComponent {
    void inject(MovieDetailActivity activity);
}