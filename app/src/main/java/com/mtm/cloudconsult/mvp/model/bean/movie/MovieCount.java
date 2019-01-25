package com.mtm.cloudconsult.mvp.model.bean.movie;


import com.mtm.cloudconsult.app.api.AdapterConstant;
import com.mtm.cloudconsult.app.base.BaseEntityBean;

public class MovieCount<T> extends BaseEntityBean {
    private String content;
    private T object;
    @Override
    public int getItemType() {
        return AdapterConstant.ITEM_MOVIE_COUNT_DEFAULT;
    }


    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}