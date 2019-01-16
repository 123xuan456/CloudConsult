package com.mtm.cloudconsult.app.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.base.BaseAppliction;
import com.mtm.cloudconsult.app.utils.GlideUtils;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDataBean;

import java.util.List;

/**
 * Created by MTM on 2019/1/14.
 *
 * @author QSX
 */
public class TFriendAdapter extends BaseQuickAdapter<GankIoDataBean.ResultBean, BaseViewHolder> {

    public TFriendAdapter(int layoutResId, @Nullable List<GankIoDataBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, GankIoDataBean.ResultBean item) {
        ImageView ivAllWelfare = viewHolder.getView(R.id.iv_welfare);
        //获取屏幕宽度
        WindowManager wm = (WindowManager) BaseAppliction.getInstance().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        //设置高度随机
        int iamgeHeight = (int) (width / 2 + Math.random() * 200);
        ViewGroup.LayoutParams params = ivAllWelfare.getLayoutParams();
        params.width = width / 2;
        params.height = iamgeHeight;
        ivAllWelfare.setLayoutParams(params);
        GlideUtils.displayEspImage(item.getUrl(), ivAllWelfare, 1);
    }
}
