package com.mtm.cloudconsult.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.base.BaseAppliction;
import com.mtm.cloudconsult.app.utils.DensityUtil;
import com.mtm.cloudconsult.app.utils.DialogBuild;
import com.mtm.cloudconsult.app.utils.GlideUtils;
import com.mtm.cloudconsult.app.utils.PerfectClickListener;
import com.mtm.cloudconsult.mvp.model.bean.AndroidBean;
import com.mtm.cloudconsult.mvp.ui.activity.config.WebViewActivity;

import org.simple.eventbus.EventBus;

import java.util.List;

import static com.mtm.cloudconsult.app.EventBusTags.TWO_CURRENTITEM;

/**
 * 多布局Item
 */
public class TRecommendAdapter extends MultipleItemRvAdapter<List<AndroidBean>, BaseViewHolder> {

    private static final int TYPE_TITLE = 1; // title
    private static final int TYPE_ONE = 2;// 一张图
    private static final int TYPE_TWO = 3;// 二张图
    private static final int TYPE_THREE = 4;// 三张图
    private final int width;

    public TRecommendAdapter(@Nullable List<List<AndroidBean>> data) {
        super(data);
        WindowManager wm = (WindowManager) BaseAppliction.getInstance().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        finishInitialize();
    }


    @Override
    protected int getViewType(List<AndroidBean> androidBeans) {
        if (!TextUtils.isEmpty(androidBeans.get(0).getType_title())) {
            return TYPE_TITLE;
        } else if (androidBeans.size() == 1) {
            return TYPE_ONE;
        } else if (androidBeans.size() == 2) {
            return TYPE_TWO;
        } else if (androidBeans.size() == 3) {
            return TYPE_THREE;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new TitleHolder());
        mProviderDelegate.registerProvider(new OneHolder());
        mProviderDelegate.registerProvider(new TwoHolder());
        mProviderDelegate.registerProvider(new ThreeHolder());
    }

    private class TitleHolder extends BaseItemProvider<List<AndroidBean>, BaseViewHolder> {
        @Override
        public int viewType() {
            return TYPE_TITLE;
        }

        @Override
        public int layout() {
            return R.layout.item_everyday_title;
        }

        @Override
        public void convert(BaseViewHolder helper, List<AndroidBean> data, int position) {
            int index = 0;
            String title = data.get(0).getType_title();
            TextView tvTitleType = helper.getView(R.id.tv_title_type);
            View viewLine = helper.getView(R.id.view_line);
            View llTitleMore = helper.getView(R.id.ll_title_more);
            tvTitleType.setText(title);
            if ("Android".equals(title)) {
                index = 2;
            } else if ("福利".equals(title)) {
                index = 1;
            } else if ("IOS".equals(title)) {
                index = 2;
            } else if ("休息视频".equals(title)) {
                index = 2;
            } else if ("拓展资源".equals(title)) {
                index = 2;
            }
            if (position != 0) {
                viewLine.setVisibility(View.VISIBLE);
            } else {
                viewLine.setVisibility(View.GONE);
            }
            final int finalIndex = index;
            llTitleMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(finalIndex,TWO_CURRENTITEM);
                }
            });
        }

    }

    private class OneHolder extends BaseItemProvider<List<AndroidBean>, BaseViewHolder> {

        @Override
        public int viewType() {
            return TYPE_ONE;
        }

        @Override
        public int layout() {
            return R.layout.item_everyday_one;
        }

        @Override
        public void convert(BaseViewHolder helper, List<AndroidBean> data, int position) {
            ImageView ivOnePhoto=helper.getView(R.id.iv_one_photo);
            TextView tvOnePhotoTitle=helper.getView(R.id.tv_one_photo_title);
            DensityUtil.formatHeight(ivOnePhoto, width, 2.6f, 1);
            if ("福利".equals(data.get(0).getType())) {
                tvOnePhotoTitle.setVisibility(View.GONE);
                ivOnePhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                GlideUtils.loadImage(ivOnePhoto.getContext(),data.get(0).getUrl(),ivOnePhoto,R.drawable.img_two_bi_one,R.drawable.img_two_bi_one);
                ivOnePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(1,TWO_CURRENTITEM);
                    }
                });

            } else {
                tvOnePhotoTitle.setVisibility(View.VISIBLE);
                setDes(data, 0, tvOnePhotoTitle);
                displayRandomImg(1, 0, ivOnePhoto, data);
                setOnClick(ivOnePhoto, data.get(0));
            }

        }
        @Override
        public void onClick(BaseViewHolder helper, List<AndroidBean> data, int position) {


        }

        @Override
        public boolean onLongClick(BaseViewHolder helper, List<AndroidBean> data, int position) {
            return true;
        }
    }
    private void setDes(List<AndroidBean> object, int position, TextView textView) {
        textView.setText(object.get(position).getDesc());
    }

    private void displayRandomImg(int imgNumber, int position, ImageView imageView, List<AndroidBean> object) {
        GlideUtils.displayRandom(imgNumber, object.get(position).getImage_url(), imageView);
    }

    private class TwoHolder extends BaseItemProvider<List<AndroidBean>, BaseViewHolder> {

        @Override
        public int viewType() {
            return TYPE_TWO;
        }

        @Override
        public int layout() {
            return R.layout.item_everyday_two;
        }

        @Override
        public void convert(BaseViewHolder helper, List<AndroidBean> data, int position) {
            ImageView ivTwoOneOne=helper.getView(R.id.iv_two_one_one);
            ImageView ivTwoOneTwo=helper.getView(R.id.iv_two_one_two);
            TextView tvTwoOneOneTitle=helper.getView(R.id.tv_two_one_one_title);
            TextView tvTwoOneTwoTitle=helper.getView(R.id.tv_two_one_two_title);
            LinearLayout llTwoOneOne=helper.getView(R.id.ll_two_one_one);
            LinearLayout llTwoOneTwo=helper.getView(R.id.ll_two_one_two);
            int imageWidth = (width - DensityUtil.dip2px(3)) / 2;
            DensityUtil.formatHeight(ivTwoOneOne, imageWidth, 1.75f, 1);
            DensityUtil.formatHeight(ivTwoOneTwo, imageWidth, 1.75f, 1);
            displayRandomImg(2, 0, ivTwoOneOne, data);
            displayRandomImg(2, 1, ivTwoOneTwo, data);
            setDes(data, 0, tvTwoOneOneTitle);
            setDes(data, 1, tvTwoOneTwoTitle);
            setOnClick(llTwoOneOne, data.get(0));
            setOnClick(llTwoOneTwo, data.get(1));
        }
        @Override
        public void onClick(BaseViewHolder helper, List<AndroidBean> data, int position) {
        }

        @Override
        public boolean onLongClick(BaseViewHolder helper, List<AndroidBean> data, int position) {
            return true;
        }
    }
    private class ThreeHolder extends BaseItemProvider<List<AndroidBean>, BaseViewHolder> {

        @Override
        public int viewType() {
            return TYPE_THREE;
        }

        @Override
        public int layout() {
            return R.layout.item_everyday_three;
        }

        @Override
        public void convert(BaseViewHolder helper, List<AndroidBean> data, int position) {
            ImageView ivThreeOneOne=helper.getView(R.id.iv_three_one_one);
            ImageView ivThreeOneTwo=helper.getView(R.id.iv_three_one_two);
            ImageView ivThreeOneThree=helper.getView(R.id.iv_three_one_three);
            TextView tvThreeOneOneTitle=helper.getView(R.id.tv_three_one_one_title);
            TextView tvThreeOneTwoTitle=helper.getView(R.id.tv_three_one_two_title);
            TextView tvThreeOneThreeTitle=helper.getView(R.id.tv_three_one_three_title);
            LinearLayout llThreeOneOne=helper.getView(R.id.ll_three_one_one);
            LinearLayout llThreeOneTwo=helper.getView(R.id.ll_three_one_two);
            LinearLayout llThreeOneThree=helper.getView(R.id.ll_three_one_three);
            int imageWidth = (width - DensityUtil.dip2px(6)) / 3;
            DensityUtil.formatHeight(ivThreeOneOne, imageWidth, 1, 1);
            DensityUtil.formatHeight(ivThreeOneTwo, imageWidth, 1, 1);
            DensityUtil.formatHeight(ivThreeOneThree, imageWidth, 1, 1);
            displayRandomImg(3, 0, ivThreeOneOne, data);
            displayRandomImg(3, 1, ivThreeOneTwo, data);
            displayRandomImg(3, 2, ivThreeOneThree, data);
            setOnClick(llThreeOneOne, data.get(0));
            setOnClick(llThreeOneTwo, data.get(1));
            setOnClick(llThreeOneThree, data.get(2));
            setDes(data, 0, tvThreeOneOneTitle);
            setDes(data, 1, tvThreeOneTwoTitle);
            setDes(data, 2, tvThreeOneThreeTitle);
            
            
        }
        @Override
        public void onClick(BaseViewHolder helper, List<AndroidBean> data, int position) {
        }

        @Override
        public boolean onLongClick(BaseViewHolder helper, List<AndroidBean> data, int position) {
            return true;
        }
    }

    private void setOnClick(final View view, final AndroidBean bean) {
        view.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {

                WebViewActivity.loadUrl(v.getContext(), bean.getUrl(), bean.getDesc());
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String title = TextUtils.isEmpty(bean.getType()) ? bean.getDesc() : bean.getType() + "：  " + bean.getDesc();
                DialogBuild.showCustom(v, title, "查看详情", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WebViewActivity.loadUrl(view.getContext(), bean.getUrl(), bean.getDesc());
                    }
                });
                return false;
            }
        });

    }

}
