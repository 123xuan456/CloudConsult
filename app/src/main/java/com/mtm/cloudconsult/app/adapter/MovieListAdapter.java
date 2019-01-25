package com.mtm.cloudconsult.app.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.api.AdapterConstant;
import com.mtm.cloudconsult.app.base.BaseEntityBean;
import com.mtm.cloudconsult.app.utils.GlideUtils;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieBean;
import com.mtm.cloudconsult.mvp.ui.activity.MovieDetailActivity;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.List;
import java.util.Map;

import static com.mtm.cloudconsult.app.utils.StringUtils.formatGenres;
import static com.mtm.cloudconsult.app.utils.StringUtils.formatName;

/**
 * Created by MTM on 2019/1/2.
 *
 * @author QSX
 */
public class MovieListAdapter extends BaseMultiItemQuickAdapter<BaseEntityBean, BaseViewHolder> {

    private final Activity activity;

    public MovieListAdapter(Activity activity, @Nullable List<BaseEntityBean> data) {
        super(data);
        this.activity=activity;
        Map<Integer, Integer> maps = AdapterShine.fetchAdapterMap();
        for (Integer type : maps.keySet()) {
            addItemType(type, maps.get(type));
        }
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, BaseEntityBean data) {
        //添加滑动动画效果
        ViewHelper.setScaleX(viewHolder.itemView, 0.8f);
        ViewHelper.setScaleY(viewHolder.itemView, 0.8f);
        ViewPropertyAnimator.animate(viewHolder.itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
        ViewPropertyAnimator.animate(viewHolder.itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
        switch (viewHolder.getItemViewType()) {
            case AdapterConstant.ITME_MOVIE_DEFAULT:
                if (data != null && data instanceof MovieBean) {
                    MovieBean item = (MovieBean) data;
                    viewHolder.setText(R.id.tv_one_title, item.getTitle())
                            .setText(R.id.tv_one_directors, formatName(item.getDirectors()))
                            .setText(R.id.tv_one_casts, formatName(item.getCasts()))
                            .setText(R.id.tv_one_genres, formatGenres(item.getGenres()))
                            .setText(R.id.tv_one_rating_rate, "评分：" + item.getRating().getAverage());
                    ImageView iv_one_photo = viewHolder.getView(R.id.iv_one_photo);
                    GlideUtils.loadImage(activity, item.getImages().getLarge(), iv_one_photo, R.drawable.img_default_movie, R.drawable.img_default_movie);
                    viewHolder.getView(R.id.ll_one_item).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MovieDetailActivity.startActivity(activity, item,iv_one_photo);
                        }
                    });
                }
                break;
//            case AdapterConstant.ITME_MOVIE_PERSONAL_LIST:
//                if(data!=null && data instanceof MovieList){
//                    MovieList item = (MovieList) data;
//                    //横向演员列表
//                    RecyclerView recyclerView = viewHolder.getView(R.id.rvList);
//                    recyclerView.setNestedScrollingEnabled(false);
//                    LinearLayoutManager manager = new LinearLayoutManager(recyclerView.getContext());
//                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                    recyclerView.setLayoutManager(manager);
//                    MovieListAdapter adapter = new MovieListAdapter(item.getList());
//                    recyclerView.setAdapter(adapter);
//                }
//                break;
//            case AdapterConstant.ITME_MOVIE_PERSONAL_DEFAULT:
//                if(data!=null && data instanceof MoviePerson){
//                    MoviePerson item = (MoviePerson) data;
//                    binding.setVariable(BR.person,item);
//                }
//                break;
//            case AdapterConstant.ITME_MOVIE_IMAGE_DEFAULT:
//                if(data!=null && data instanceof MovieImage){
//                    MovieImage item = (MovieImage) data;
//                    binding.setVariable(BR.image,item);
//                }
//                break;
//            case AdapterConstant.ITME_MOVIE_CATE_GORY:
//                if(data!=null && data instanceof MovieCateGory){
//                    MovieCateGory item = (MovieCateGory) data;
//                    binding.setVariable(BR.cate,item);
//                }
//                break;
//            case AdapterConstant.ITME_MOVIE_COMMENT_REVIEW:
//                if(data!=null && data instanceof MovieComment){
//                    MovieComment item = (MovieComment) data;
//                    binding.setVariable(BR.commentReview,item);
//                }
//                break;
//            case AdapterConstant.ITME_MOVIE_COMMENT_DEFAULT:
//                if(data!=null && data instanceof MovieComment){
//                    MovieComment item = (MovieComment) data;
//                    binding.setVariable(BR.commentDefault,item);
//                }
//                break;
//            case AdapterConstant.ITME_MOVIE_BEAN_CELEBIRTY:
//                if(data!=null && data instanceof MovieBean){
//                    MovieBean item = (MovieBean) data;
//                    binding.setVariable(BR.beanCelebrity,item);
//                }
//                break;
//            case AdapterConstant.ITME_MOVIE_PHOTO_DEFAULT:
//                if(data!=null && data instanceof MoviePhotoRequest.PhotosBean){
//                    MoviePhotoRequest.PhotosBean item = (MoviePhotoRequest.PhotosBean) data;
//                    binding.setVariable(BR.photo,item);
//                }
//                break;
//            case AdapterConstant.ITEM_MOVIE_COUNT_DEFAULT:
//                if(data!=null && data instanceof MovieCount){
//                    MovieCount item = (MovieCount) data;
//                    binding.setVariable(BR.MovieCount,item);
//                }
//                break;
        }
    }

}
