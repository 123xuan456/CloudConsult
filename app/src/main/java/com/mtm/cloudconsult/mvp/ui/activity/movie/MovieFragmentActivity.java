package com.mtm.cloudconsult.mvp.ui.activity.movie;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.base.BaseRecycleFragment;
import com.mtm.cloudconsult.mvp.ui.fragment.MovieListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mtm.cloudconsult.app.api.CloudConstant.MOVIE_COMMENT_DEFAULT;
import static com.mtm.cloudconsult.app.api.CloudConstant.MOVIE_COMMENT_REVIEW;
import static com.mtm.cloudconsult.app.api.CloudConstant.MOVIE_PHOTOS_LIST;
import static com.mtm.cloudconsult.app.api.CloudConstant.MOVIE_SUBJECT_ID;
import static com.mtm.cloudconsult.app.api.CloudConstant.MOVIE_SUBJECT_TYPE;

/**
 * 豆瓣详情--影评--短评--剧照
 */
public class MovieFragmentActivity extends AppCompatActivity {

    @BindView(R.id.ye_contentContainer)
    FrameLayout yeContentContainer;
    @BindView(R.id.tv_gun_title)
    TextView tvGunTitle;
    @BindView(R.id.title_tool_bar)
    Toolbar mTitleToolBar;
    private String extend;
    private int type = MOVIE_COMMENT_DEFAULT;
    private FragmentManager fragmentManager;
    protected FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_fragment);
        ButterKnife.bind(this);
        if (getIntent() != null && getIntent().hasExtra(MOVIE_SUBJECT_ID)) {
            if (getIntent().hasExtra(MOVIE_SUBJECT_ID)) {
                extend = getIntent().getStringExtra(MOVIE_SUBJECT_ID);
            }
            if (getIntent().hasExtra(MOVIE_SUBJECT_TYPE)) {
                type = getIntent().getIntExtra(MOVIE_SUBJECT_TYPE, MOVIE_COMMENT_DEFAULT);
            }
        }
        initToolBar();
        switch (type) {
            case MOVIE_COMMENT_DEFAULT:
                tvGunTitle.setText("短评");
                break;
            case MOVIE_PHOTOS_LIST:
                tvGunTitle.setText("剧照");
                break;
            case MOVIE_COMMENT_REVIEW:
                tvGunTitle.setText("影评");
                break;
        }
        fragmentManager = getSupportFragmentManager();
        initFragment();

    }
    private void initToolBar() {
        setSupportActionBar(mTitleToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
        tvGunTitle.postDelayed(() -> tvGunTitle.setSelected(true), 1900);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 返回键
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = MovieListFragment.newInstance(type, extend);
        fragmentTransaction.add(R.id.ye_contentContainer, fragment);
        fragmentTransaction.commit();
        if (fragment instanceof BaseRecycleFragment) {
            BaseRecycleFragment uiFragment = (BaseRecycleFragment) fragment;
            uiFragment.resume();
        }
    }
}
