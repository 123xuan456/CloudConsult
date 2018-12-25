package com.mtm.cloudconsult.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mtm.cloudconsult.di.module.MainModule;

import com.jess.arms.di.scope.ActivityScope;
import com.mtm.cloudconsult.mvp.ui.activity.MainActivity;

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}