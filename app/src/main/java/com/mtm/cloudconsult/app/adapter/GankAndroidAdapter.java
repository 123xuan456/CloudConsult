package com.mtm.cloudconsult.app.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.utils.GlideUtils;
import com.mtm.cloudconsult.app.utils.StringUtils;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDataBean;
import com.mtm.cloudconsult.mvp.ui.activity.WebViewActivity;

import java.util.List;

/**
 * Created by MTM on 2019/1/14.
 *
 * @author QSX
 */
public class GankAndroidAdapter extends BaseQuickAdapter<GankIoDataBean.ResultBean, BaseViewHolder> {
    private boolean isAll = false;

    public void setAllType(boolean isAll) {
        this.isAll = isAll;
    }

    public GankAndroidAdapter(int layoutResId, @Nullable List<GankIoDataBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, GankIoDataBean.ResultBean item) {
        ImageView ivAllWelfare=viewHolder.getView(R.id.iv_all_welfare);
        ImageView ivAndroidPic=viewHolder.getView(R.id.iv_android_pic);
        LinearLayout llWelfareOther=viewHolder.getView(R.id.ll_welfare_other);
        LinearLayout llAll=viewHolder.getView(R.id.ll_all);
        TextView tvContentType=viewHolder.getView(R.id.tv_content_type);
        viewHolder.setText(R.id.tv_android_des,item.getDesc())
                .setText(R.id.tv_android_who,TextUtils.isEmpty(item.getWho())?"佚名":item.getWho())
                .setText(R.id.tv_android_time, StringUtils.getTranslateTime(item.getPublishedAt()));


        if ( isAll && "福利".equals(item.getType())) {
            ivAllWelfare.setVisibility(View.VISIBLE);
            llWelfareOther.setVisibility(View.GONE);
            GlideUtils.displayEspImage(item.getUrl(), ivAllWelfare, 1);
        } else {
            ivAllWelfare.setVisibility(View.GONE);
            llWelfareOther.setVisibility(View.VISIBLE);
        }

        if (isAll) {
            tvContentType.setVisibility(View.VISIBLE);
            tvContentType.setText(" · " + item.getType());
        } else {
            tvContentType.setVisibility(View.GONE);
        }
        // 显示gif图片会很耗内存
        if (item.getImages() != null
                && item.getImages().size() > 0
                && !TextUtils.isEmpty(item.getImages().get(0))) {
            ivAndroidPic.setVisibility(View.VISIBLE);
            GlideUtils.displayGif(item.getImages().get(0), ivAndroidPic);
        } else {
            ivAndroidPic.setVisibility(View.GONE);
        }
        llAll.setOnClickListener(v -> WebViewActivity.loadUrl(v.getContext(), item.getUrl(), item.getDesc()));

    }

}
