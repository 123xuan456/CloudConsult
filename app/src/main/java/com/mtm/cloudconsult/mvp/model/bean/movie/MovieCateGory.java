package com.mtm.cloudconsult.mvp.model.bean.movie;


import com.mtm.cloudconsult.app.api.AdapterConstant;
import com.mtm.cloudconsult.app.base.BaseEntityBean;

/**
 * Created by li.xiao on 2018-1-25.
 */

public class MovieCateGory extends BaseEntityBean {
    private String title;
    private String content;
    @Override
    public int getItemType() {
        return AdapterConstant.ITME_MOVIE_CATE_GORY;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
