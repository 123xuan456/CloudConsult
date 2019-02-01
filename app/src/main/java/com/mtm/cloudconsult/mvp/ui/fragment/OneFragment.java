package com.mtm.cloudconsult.mvp.ui.fragment;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.adapter.RecycleSongAdapter;
import com.mtm.cloudconsult.mvp.model.bean.SongRecycleBean;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.mtm.cloudconsult.app.EventBusTags.MAIN_CURRENTITEM;
import static com.mtm.cloudconsult.app.api.CloudConstant.CURRENT_MAIN_THREEFRAGMENT;
import static com.mtm.cloudconsult.app.api.CloudConstant.CURRENT_MAIN_TWOFRAGMENT;

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
    @BindView(R.id.rl_one_song)
    RelativeLayout rlOneGedan;
    @BindView(R.id.recycle_one_song)
    RecyclerView recycleOneSong;
    private boolean collectExpanded;
    private RecycleSongAdapter songAdapter;
    private List<SongRecycleBean> songList;

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
        initView();
        initData();
        return view;
    }

    private void initData() {
        songList=new ArrayList<>();
        for(int i=0;i<10;i++){
            SongRecycleBean bean=new SongRecycleBean();
            bean.setName("歌单"+i);
            songList.add(bean);
        }
        songAdapter.setNewData(songList);
        expandTitle.setText(String.format(getResources().getString(R.string.string_one_song),songList.size()));


    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        recycleOneSong.setHasFixedSize(true);
        //解决和ScrollView 冲突
        recycleOneSong.setFocusable(false);
        recycleOneSong.setNestedScrollingEnabled(false);
        recycleOneSong.setLayoutManager(manager);
        songAdapter = new RecycleSongAdapter(R.layout.item_fragment_one_song, new ArrayList<SongRecycleBean>());
        recycleOneSong.setAdapter(songAdapter);
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

    @OnClick({R.id.ll_one_music, R.id.ll_one_diy, R.id.ll_one_gank, R.id.ll_one_wx, R.id.ll_one_game, R.id.ll_one_dy, R.id.ll_one_bz, R.id.rl_one_song})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //玩安卓
            case R.id.ll_one_music:

                break;
                //diy
            case R.id.ll_one_diy:
                break;
                //干货
            case R.id.ll_one_gank:
                EventBus.getDefault().post(CURRENT_MAIN_TWOFRAGMENT,MAIN_CURRENTITEM);
                break;
                //微信
            case R.id.ll_one_wx:
                break;
                //游民星空
            case R.id.ll_one_game:
                break;
                //影视
            case R.id.ll_one_dy:
                EventBus.getDefault().post(CURRENT_MAIN_THREEFRAGMENT,MAIN_CURRENTITEM);
                break;
                //壁纸
            case R.id.ll_one_bz:
                break;
            //创建的歌单
            case R.id.rl_one_song:
                ObjectAnimator anim = null;
                //箭头旋转
                anim = ObjectAnimator.ofFloat(expandImg, "rotation", 90, 0);
                if (collectExpanded) {
                    recycleOneSong.setVisibility(View.VISIBLE);
                    anim.setDuration(100);
                    anim.setRepeatCount(0);
                    anim.start();
                    collectExpanded = false;
                } else {
                    recycleOneSong.setVisibility(View.GONE);
                    anim.reverse();
                    collectExpanded = true;
                }
                break;
            default:
        }
    }

}
