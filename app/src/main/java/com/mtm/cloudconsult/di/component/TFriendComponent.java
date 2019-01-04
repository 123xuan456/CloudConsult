package com.mtm.cloudconsult.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mtm.cloudconsult.di.module.TFriendModule;

import com.jess.arms.di.scope.FragmentScope;
import com.mtm.cloudconsult.mvp.ui.fragment.two.TFriendFragment;

@FragmentScope
@Component(modules = TFriendModule.class, dependencies = AppComponent.class)
public interface TFriendComponent {
    void inject(TFriendFragment fragment);
}