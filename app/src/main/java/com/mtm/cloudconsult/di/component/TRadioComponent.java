package com.mtm.cloudconsult.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mtm.cloudconsult.di.module.TRadioModule;

import com.jess.arms.di.scope.FragmentScope;
import com.mtm.cloudconsult.mvp.ui.fragment.TRadioFragment;

@FragmentScope
@Component(modules = TRadioModule.class, dependencies = AppComponent.class)
public interface TRadioComponent {
    void inject(TRadioFragment fragment);
}