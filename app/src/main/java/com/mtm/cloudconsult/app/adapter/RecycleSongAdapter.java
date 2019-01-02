package com.mtm.cloudconsult.app.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.mvp.model.bean.SongRecycleBean;

import java.util.List;

/**
 * Created by MTM on 2019/1/2.
 *
 * @author QSX
 */
public class RecycleSongAdapter extends BaseQuickAdapter<SongRecycleBean, BaseViewHolder> {

    public RecycleSongAdapter(int layoutResId, @Nullable List<SongRecycleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, SongRecycleBean item) {
        viewHolder.setText(R.id.tv_song, item.getName());

    }

}
