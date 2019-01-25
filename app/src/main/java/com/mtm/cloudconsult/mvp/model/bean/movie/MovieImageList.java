package com.mtm.cloudconsult.mvp.model.bean.movie;


import com.mtm.cloudconsult.app.api.AdapterConstant;
import com.mtm.cloudconsult.app.base.BaseEntityBean;

import java.util.List;

/**
 * Created by li.xiao on 2018-1-25.
 */

public class MovieImageList extends BaseEntityBean {
    private List<MovieImage> list;

    @Override
    public int getItemType() {
        return AdapterConstant.ITME_MOVIE_IMAGE_LIST;
    }

    public List<MovieImage> getList() {
        return list;
    }

    public void setList(List<MovieImage> list) {
        this.list = list;
    }
}
