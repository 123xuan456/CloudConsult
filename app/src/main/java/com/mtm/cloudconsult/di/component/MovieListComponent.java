package com.mtm.cloudconsult.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mtm.cloudconsult.di.module.MovieListModule;

import com.jess.arms.di.scope.FragmentScope;
import com.mtm.cloudconsult.mvp.ui.fragment.MovieListFragment;

@FragmentScope
@Component(modules = MovieListModule.class, dependencies = AppComponent.class)
public interface MovieListComponent {
    void inject(MovieListFragment fragment);
}