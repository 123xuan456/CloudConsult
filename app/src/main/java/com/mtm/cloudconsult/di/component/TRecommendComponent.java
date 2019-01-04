package com.mtm.cloudconsult.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mtm.cloudconsult.di.module.TRecommendModule;

import com.jess.arms.di.scope.FragmentScope;
import com.mtm.cloudconsult.mvp.ui.fragment.two.TRecommendFragment;

@FragmentScope
@Component(modules = TRecommendModule.class, dependencies = AppComponent.class)
public interface TRecommendComponent {
    void inject(TRecommendFragment fragment);
}