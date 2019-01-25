package com.mtm.cloudconsult.app.base;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * 公共实体类
 * 所有实体类都继承该基类，混淆和数据展示更加方便
 */
public abstract class BaseEntityBean implements Serializable, Cloneable, MultiItemEntity {
    protected int itemtype;

    public int getItemType() {
        return itemtype;
    }

    public void setItemtype(int itemtype) {
        this.itemtype = itemtype;
    }
}
