package com.mtm.cloudconsult.mvp.ui.fragment;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mtm.cloudconsult.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 第一个页面
 */
public class OneFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.imageview)
    CircleImageView imageview;
    @BindView(R.id.tv_fuli)
    TextView tvFuli;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.ll_one_music)
    LinearLayout llOneMusic;
    @BindView(R.id.ll_one_diy)
    LinearLayout llOneDiy;
    @BindView(R.id.ll_one_gank)
    LinearLayout llOneGank;
    @BindView(R.id.ll_one_wx)
    LinearLayout llOneWx;
    @BindView(R.id.ll_one_game)
    LinearLayout llOneGame;
    @BindView(R.id.ll_one_dy)
    LinearLayout llOneDy;
    @BindView(R.id.ll_one_bz)
    LinearLayout llOneBz;
    @BindView(R.id.expand_img)
    ImageView expandImg;
    @BindView(R.id.expand_title)
    TextView expandTitle;
    @BindView(R.id.rl_one_gedan)
    RelativeLayout rlOneGedan;
    @BindView(R.id.ll_one_gedan_list)
    LinearLayout llOneGedanList;
    private boolean collectExpanded;

    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);
        swipeRefresh.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        //设置刷新两秒关闭动画
        swipeRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
            }
        }, 2000);
    }

    @OnClick({R.id.ll_one_music, R.id.ll_one_diy, R.id.ll_one_gank, R.id.ll_one_wx, R.id.ll_one_game, R.id.ll_one_dy, R.id.ll_one_bz, R.id.rl_one_gedan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_one_music:
                break;
            case R.id.ll_one_diy:
                break;
            case R.id.ll_one_gank:
                break;
            case R.id.ll_one_wx:
                break;
            case R.id.ll_one_game:
                break;
            case R.id.ll_one_dy:
                break;
            case R.id.ll_one_bz:
                break;
            //创建的歌单
            case R.id.rl_one_gedan:
                ObjectAnimator anim = null;
                anim = ObjectAnimator.ofFloat(expandImg, "rotation", 90, 0);
                if (collectExpanded) {
                    llOneGedanList.setVisibility(View.VISIBLE);
                    anim.setDuration(100);
                    anim.setRepeatCount(0);
                    anim.start();
                    collectExpanded = false;
                } else {
                    llOneGedanList.setVisibility(View.GONE);
                    anim.reverse();
                    collectExpanded = true;
                }
                break;
            default:
        }
    }

}
