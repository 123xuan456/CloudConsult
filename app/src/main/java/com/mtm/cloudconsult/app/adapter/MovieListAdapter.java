package com.mtm.cloudconsult.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieCateGory;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieComment;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieCount;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieImage;
import com.mtm.cloudconsult.mvp.model.bean.movie.MovieList;
import com.mtm.cloudconsult.mvp.model.bean.movie.MoviePerson;
import com.mtm.cloudconsult.mvp.model.bean.movie.MoviePhotoRequest;
import com.mtm.cloudconsult.mvp.ui.activity.ViewBigImageActivity;
import com.mtm.cloudconsult.mvp.ui.activity.config.WebViewActivity;
import com.mtm.cloudconsult.mvp.ui.activity.movie.MovieCelebrityActivity;
import com.mtm.cloudconsult.mvp.ui.activity.movie.MovieDetailActivity;
import com.mtm.cloudconsult.mvp.ui.activity.movie.MovieFragmentActivity;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.mtm.cloudconsult.app.api.CloudConstant.MOVIE_COMMENT_DEFAULT;
import static com.mtm.cloudconsult.app.api.CloudConstant.MOVIE_COMMENT_REVIEW;
import static com.mtm.cloudconsult.app.api.CloudConstant.MOVIE_INFO;
import static com.mtm.cloudconsult.app.api.CloudConstant.MOVIE_PERSON;
import static com.mtm.cloudconsult.app.api.CloudConstant.MOVIE_PHOTOS_LIST;
import static com.mtm.cloudconsult.app.api.CloudConstant.MOVIE_SUBJECT_ID;
import static com.mtm.cloudconsult.app.api.CloudConstant.MOVIE_SUBJECT_TYPE;
import static com.mtm.cloudconsult.app.utils.StringUtils.formatGenres;
import static com.mtm.cloudconsult.app.utils.StringUtils.formatName;

/**
 * Created by MTM on 2019/1/2.
 *
 * @author QSX
 */
public class MovieListAdapter extends BaseMultiItemQuickAdapter<BaseEntityBean, BaseViewHolder> {

    private final Activity activity;
    private ArrayList<String> imgTitleList;
    private ArrayList<String> imgList;
    public MovieListAdapter(Activity activity, @Nullable List<BaseEntityBean> data) {
        super(data);
        this.activity = activity;
        Map<Integer, Integer> maps = AdapterShine.fetchAdapterMap();
        for (Integer type : maps.keySet()) {
            addItemType(type, maps.get(type));
        }
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, BaseEntityBean data) {
        try {
            //添加滑动动画效果
            ViewHelper.setScaleX(viewHolder.itemView, 0.8f);
            ViewHelper.setScaleY(viewHolder.itemView, 0.8f);
            ViewPropertyAnimator.animate(viewHolder.itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
            ViewPropertyAnimator.animate(viewHolder.itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
            switch (viewHolder.getItemViewType()) {
                //电影列表
                case AdapterConstant.ITME_MOVIE_DEFAULT:
                    if (data != null && data instanceof MovieBean) {
                        MovieBean item = (MovieBean) data;
                        viewHolder.setText(R.id.tv_one_title, item.getTitle())
                                .setText(R.id.tv_one_directors, formatName(item.getDirectors()))//导演
                                .setText(R.id.tv_one_casts, formatName(item.getCasts()))//主演
                                .setText(R.id.tv_one_genres, formatGenres(item.getGenres()))//电影类型
                                .setText(R.id.tv_one_rating_rate, "评分：" + item.getRating().getAverage());
                        ImageView iv_one_photo = viewHolder.getView(R.id.iv_one_photo);
                        GlideUtils.loadImage(activity, item.getImages().getLarge(), iv_one_photo, R.drawable.img_default_movie, R.drawable.img_default_movie);
                        viewHolder.getView(R.id.ll_one_item).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MovieDetailActivity.startActivity(activity, item, iv_one_photo);
                            }
                        });
                    }
                    break;
                //演员
                case AdapterConstant.ITME_MOVIE_PERSONAL_LIST:
                    if (data != null && data instanceof MovieList) {
                        MovieList item = (MovieList) data;
                        //横向演员列表
                        RecyclerView recyclerView = viewHolder.getView(R.id.rvList);
                        recyclerView.setNestedScrollingEnabled(false);
                        LinearLayoutManager manager = new LinearLayoutManager(recyclerView.getContext());
                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(manager);
                        MovieListAdapter adapter = new MovieListAdapter(activity, item.getList());
                        recyclerView.setAdapter(adapter);
                    }
                    break;
                //演员列表
                case AdapterConstant.ITME_MOVIE_PERSONAL_DEFAULT:
                    if (data != null && data instanceof MoviePerson) {
                        MoviePerson item = (MoviePerson) data;
                        GlideUtils.showImageView(activity, viewHolder.getView(R.id.iv_one_photo), item.getAvatars().getLarge());
                        viewHolder.setText(R.id.tv_movie_person_name, item.getName())
                                .setText(R.id.tv_movie_person_role, item.getRole());
                        viewHolder.getView(R.id.ll_one_item).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openMovieCelebrityActivity(activity, item);
                            }
                        });
                    }
                    break;
                //剧照/影人照片
                case AdapterConstant.ITME_MOVIE_IMAGE_DEFAULT:
                    if (data != null && data instanceof MovieImage) {
                        MovieImage item = (MovieImage) data;
                        GlideUtils.showImageView(activity, viewHolder.getView(R.id.iv_one_photo), item.getCover());
                        viewHolder.getView(R.id.iv_one_photo).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (com.blankj.utilcode.util.StringUtils.isEmpty(item.getSubjectId())){
                                    ViewBigImageActivity.start(activity, item.getCover(),item.getId());
                                }else {
                                    openMovieFragmentActivity(activity, MOVIE_PHOTOS_LIST, item.getSubjectId());
                                }
                            }
                        });
                    }
                    break;
                //简介
                case AdapterConstant.ITME_MOVIE_CATE_GORY:
                    if (data != null && data instanceof MovieCateGory) {
                        MovieCateGory item = (MovieCateGory) data;
                        viewHolder.setText(R.id.tv_movie_cate_title, item.getTitle())
                                .setText(R.id.tv_movie_cate_content, item.getContent());
                    }
                    break;
                //影评
                case AdapterConstant.ITME_MOVIE_COMMENT_REVIEW:
                    if (data != null && data instanceof MovieComment) {
                        MovieComment item = (MovieComment) data;
                        //评分
                        MaterialRatingBar ratingBar = viewHolder.getView(R.id.mr_movie_comment_rating);
                        ratingBar.setNumStars(item.getRating().getMax());
                        ratingBar.setRating(item.getRating().getValue());
                        viewHolder.setText(R.id.tv_movie_comment_title, item.getTitle())//标题
                                .setText(R.id.tv_movie_comment_author_name, item.getAuthor().getName())//用户名
                                .setText(R.id.tv_movie_comment_summary, item.getSummary())//影评内容
                                .setText(R.id.tv_movie_comment_count, item.getUseful_count() + "/" + item.getComments_count() + " 有用");
                        viewHolder.getView(R.id.ll_one_item).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = "https://movie.douban.com/review/" + item.getId() + "/";
                                WebViewActivity.loadUrl(activity, url, item.getTitle());
                            }
                        });

                    }
                    break;
                //短评
                case AdapterConstant.ITME_MOVIE_COMMENT_DEFAULT:
                    if (data != null && data instanceof MovieComment) {
                        MovieComment item = (MovieComment) data;
                        //用户头像
                        GlideUtils.showImageView(activity, viewHolder.getView(R.id.ivAvatar), item.getAuthor().getAvatar());
                        MaterialRatingBar ratingBar = viewHolder.getView(R.id.mr_movie_comment_rating);
                        ratingBar.setNumStars(item.getRating().getMax());
                        ratingBar.setRating(item.getRating().getValue());
                        viewHolder.setText(R.id.tvUsername, item.getAuthor().getName())
                                .setText(R.id.tvSummary, item.getContent())
                                .setText(R.id.tv_created, item.getCreated_at());
                        viewHolder.getView(R.id.ivAvatar).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = "https://m.douban.com/people/" + item.getAuthor().getId() + "/";
                                WebViewActivity.loadUrl(activity, url, item.getAuthor().getName());
                            }
                        });

                    }
                    break;
                //演员信息
                case AdapterConstant.ITME_MOVIE_BEAN_CELEBIRTY:
                    if (data != null && data instanceof MovieBean) {
                        MovieBean item = (MovieBean) data;
                        GlideUtils.showImageView(activity, viewHolder.getView(R.id.iv_one_photo), item.getImages().getLarge());
                        MaterialRatingBar ratingBar = viewHolder.getView(R.id.mr_movie_comment_rating);
                        ratingBar.setNumStars(5);
                        ratingBar.setRating(item.getRating().getAverage() / 2);
                        viewHolder.setText(R.id.tv_movie_celebrity_title, item.getTitle())
                                .setText(R.id.tv_movie_celebrity_rating, item.getRating().getAverage() + "");
                        viewHolder.getView(R.id.ll_one_item).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openMovieDetailActivity(activity, item);
                            }
                        });

                    }
                    break;
                //剧照图片详情
                case AdapterConstant.ITME_MOVIE_PHOTO_DEFAULT:
                    if (data != null && data instanceof MoviePhotoRequest.PhotosBean) {
                        MoviePhotoRequest.PhotosBean item = (MoviePhotoRequest.PhotosBean) data;
                        GlideUtils.showImageView(activity, viewHolder.getView(R.id.iv_all_welfare), item.getCover());
                    }
                    break;

                case AdapterConstant.ITEM_MOVIE_COUNT_DEFAULT:
                    if (data != null && data instanceof MovieCount) {
                        MovieCount item = (MovieCount) data;
                        viewHolder.setText(R.id.tv_movie_count_title, item.getContent());
                        viewHolder.getView(R.id.tv_movie_count_title).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (item != null && item.getObject() != null) {
                                    if (item.getObject() instanceof MovieImage) {
                                        MovieImage image = (MovieImage) item.getObject();
                                        openMovieFragmentActivity(activity, MOVIE_PHOTOS_LIST, image.getSubjectId());
                                    } else if (item.getObject() instanceof MovieComment) {
                                        MovieComment comment = (MovieComment) item.getObject();
                                        if (comment.getItemType() == AdapterConstant.ITME_MOVIE_COMMENT_DEFAULT) {
                                            openMovieFragmentActivity(activity, MOVIE_COMMENT_DEFAULT, comment.getSubject_id());
                                        } else {
                                            openMovieFragmentActivity(activity, MOVIE_COMMENT_REVIEW, comment.getSubject_id());
                                        }
                                    }
                                }

                            }
                        });

                    }
                    break;
            }
        } catch (Exception ignored) {

        }
    }
    //跳转豆瓣--影评--短评--剧照详情
    private static void openMovieFragmentActivity(Activity activity, int type, String extend) {
        Intent intent = new Intent(activity, MovieFragmentActivity.class);
        intent.putExtra(MOVIE_SUBJECT_ID, extend);
        intent.putExtra(MOVIE_SUBJECT_TYPE, type);
        activity.startActivity(intent);
    }
    //跳转豆瓣演员详情
    private static void openMovieCelebrityActivity(Activity activity, MoviePerson person) {
        Intent intent = new Intent(activity, MovieCelebrityActivity.class);
        intent.putExtra(MOVIE_PERSON, person);
        activity.startActivity(intent);
    }
    //跳转豆瓣电影详情
    public static void openMovieDetailActivity(Activity activity, MovieBean movieBean) {
        Intent intent = new Intent(activity, MovieDetailActivity.class);
        intent.putExtra(MOVIE_INFO, movieBean);
        activity.startActivity(intent);
    }

}
