package com.mtm.cloudconsult.app.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.mvp.model.bean.AndroidBean;

/**
 *
 */
public class TRecommendAdapter extends BaseQuickAdapter<AndroidBean, BaseViewHolder> {

    private static final int TYPE_TITLE = 1; // title
    private static final int TYPE_ONE = 2;// 一张图
    private static final int TYPE_TWO = 3;// 二张图
    private static final int TYPE_THREE = 4;// 三张图
    public TRecommendAdapter() {
        super(R.layout.item_fragment_one_song);
    }

//    @Override
//    public int getItemViewType(int position) {
//    }
//
//
//    @Override
//    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
//    }

    @Override
    protected void convert(BaseViewHolder helper, AndroidBean item) {




    }
}
