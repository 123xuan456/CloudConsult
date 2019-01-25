package com.mtm.cloudconsult.mvp.model.bean.movie;


import com.mtm.cloudconsult.app.api.AdapterConstant;
import com.mtm.cloudconsult.app.base.BaseEntityBean;

import java.util.List;

/**
 * Created by li.xiao on 2018-1-25.
 */

public class MovieList<T> extends BaseEntityBean {
    private List<T> list;

    @Override
    public int getItemType() {
        return AdapterConstant.ITME_MOVIE_PERSONAL_LIST;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
