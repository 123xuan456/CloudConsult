package com.mtm.cloudconsult.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.adapter.MyFragmentPagerAdapter;
import com.mtm.cloudconsult.app.api.Api;
import com.mtm.cloudconsult.di.component.DaggerMainComponent;
import com.mtm.cloudconsult.di.module.MainModule;
import com.mtm.cloudconsult.mvp.contract.MainContract;
import com.mtm.cloudconsult.mvp.presenter.MainPresenter;
import com.mtm.cloudconsult.mvp.ui.fragment.OneFragment;
import com.mtm.cloudconsult.mvp.ui.fragment.ThreeFragment;
import com.mtm.cloudconsult.mvp.ui.fragment.TwoFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mtm.cloudconsult.app.api.Api.API_GANKIO;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View , ViewPager.OnPageChangeListener {


    @BindView(R.id.iv_title_menu)
    ImageView ivTitleMenu;
    @BindView(R.id.ll_title_menu)
    FrameLayout llTitleMenu;
    @BindView(R.id.iv_title_one)
    ImageView ivTitleOne;
    @BindView(R.id.iv_title_two)
    ImageView ivTitleTwo;
    @BindView(R.id.iv_title_three)
    ImageView ivTitleThree;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        RetrofitUrlManager.getInstance().putDomain(Api.GANK_DOMAIN_NAME, API_GANKIO);
        RetrofitUrlManager.getInstance().putDomain(Api.DOUBAN_DOMAIN_NAME, Api.API_DOUBAN);
        RetrofitUrlManager.getInstance().putDomain(Api.TING_DOMAIN_NAME, Api.API_TING);
        RetrofitUrlManager.getInstance().putDomain(Api.FIR_DOMAIN_NAME, Api.API_FIR);
        RetrofitUrlManager.getInstance().putDomain(Api.WAN_ANDROID_DOMAIN_NAME, Api.API_WAN_ANDROID);
        RetrofitUrlManager.getInstance().putDomain(Api.QSBK_DOMAIN_NAME, Api.API_QSBK);
        initContentFragment();
    }
    private void initContentFragment() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new OneFragment());
        mFragmentList.add(new TwoFragment());
        mFragmentList.add(new ThreeFragment());
        // 注意使用的是：getSupportFragmentManager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        vpContent.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        vpContent.setOffscreenPageLimit(2);
        vpContent.addOnPageChangeListener(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
        //默认显示第二页
        setCurrentItem(1);
    }
    /**
     * 切换页面
     *
     * @param position 分类角标
     */
    private void setCurrentItem(int position) {
        boolean isOne = false;
        boolean isTwo = false;
        boolean isThree = false;
        switch (position) {
            case 0:
                isOne = true;
                break;
            case 1:
                isTwo = true;
                break;
            case 2:
                isThree = true;
                break;
            default:
                isTwo = true;
                break;
        }
        vpContent.setCurrentItem(position);
        ivTitleOne.setSelected(isOne);
        ivTitleTwo.setSelected(isTwo);
        ivTitleThree.setSelected(isThree);
    }
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @OnClick({R.id.ll_title_menu, R.id.iv_title_one, R.id.iv_title_two, R.id.iv_title_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_menu:
                // 开启菜单
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.iv_title_two:
                // 不然cpu会有损耗
                if (vpContent.getCurrentItem() != 1) {
                    setCurrentItem(1);
                }
                break;
            case R.id.iv_title_one:
                if (vpContent.getCurrentItem() != 0) {
                    setCurrentItem(0);
                }
                break;
            case R.id.iv_title_three:
                if (vpContent.getCurrentItem() != 2) {
                    setCurrentItem(2);
                }
                break;
                default:
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                setCurrentItem(0);
                break;
            case 1:
                setCurrentItem(1);
                break;
            case 2:
                setCurrentItem(2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
