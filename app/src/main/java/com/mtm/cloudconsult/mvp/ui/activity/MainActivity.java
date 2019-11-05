package com.mtm.cloudconsult.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.kingja.loadsir.core.LoadSir;
import com.mtm.cloudconsult.R;
import com.mtm.cloudconsult.app.adapter.MyFragmentPagerAdapter;
import com.mtm.cloudconsult.app.api.Api;
import com.mtm.cloudconsult.app.callback.ErrorCallback;
import com.mtm.cloudconsult.app.callback.LoadingCallback;
import com.mtm.cloudconsult.app.view.SweetAlertDialog;
import com.mtm.cloudconsult.di.component.DaggerMainComponent;
import com.mtm.cloudconsult.di.module.MainModule;
import com.mtm.cloudconsult.mvp.contract.MainContract;
import com.mtm.cloudconsult.mvp.presenter.MainPresenter;
import com.mtm.cloudconsult.mvp.ui.fragment.OneFragment;
import com.mtm.cloudconsult.mvp.ui.fragment.ThreeFragment;
import com.mtm.cloudconsult.mvp.ui.fragment.TwoFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import timber.log.Timber;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.blankj.utilcode.util.ImageUtils.getBitmap;
import static com.jess.arms.utils.ArmsUtils.killAll;
import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mtm.cloudconsult.app.EventBusTags.MAIN_CURRENTITEM;
import static com.mtm.cloudconsult.app.api.Api.API_GANKIO;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, ViewPager.OnPageChangeListener {


    private static final int CODE_WRITE = 1000;
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
    private boolean mIsExit;

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        ArmsUtils.snackbarText("打包成功！！");
        RetrofitUrlManager.getInstance().putDomain(Api.GANK_DOMAIN_NAME, API_GANKIO);
        RetrofitUrlManager.getInstance().putDomain(Api.DOUBAN_DOMAIN_NAME, Api.API_DOUBAN);
        RetrofitUrlManager.getInstance().putDomain(Api.TING_DOMAIN_NAME, Api.API_TING);
        RetrofitUrlManager.getInstance().putDomain(Api.FIR_DOMAIN_NAME, Api.API_FIR);
        RetrofitUrlManager.getInstance().putDomain(Api.WAN_ANDROID_DOMAIN_NAME, Api.API_WAN_ANDROID);
        RetrofitUrlManager.getInstance().putDomain(Api.QSBK_DOMAIN_NAME, Api.API_QSBK);
        RetrofitUrlManager.getInstance().putDomain(Api.BIZHI_DOMAIN_NAME, Api.API_BIZHI);
        RetrofitUrlManager.getInstance().putDomain(Api.WECHAT_DOMAIN_NAME, Api.API_WECHAT);
        RetrofitUrlManager.getInstance().putDomain(Api.GAMER_DOMAIN_NAME, Api.API_GAMER);

        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new LoadingCallback())
                .commit();
        requestPermissions();
        initContentFragment();
        View headView = navView.getHeaderView(0);
        LinearLayout ll_header_bg = headView.findViewById(R.id.ll_header_bg);
        ll_header_bg.setBackground(new BitmapDrawable(ImageUtils.renderScriptBlur(getBitmap(R.drawable.ic_circle_head), 25)));
    }

    /**
     * 添加动态权限
     */
    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(MainActivity.this);
        rxPermission
                .requestEach(ACCESS_FINE_LOCATION,
                        WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        Timber.e("%s is granted.", permission.name);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        Timber.d("%s is denied. More info should be provided.", permission.name);
                        if (permission.name.equals(WRITE_EXTERNAL_STORAGE)) {
                            showAlertDialog("存储权限为必要权限，请开启存储权限，已正常使用。", "确定", false);
                        }
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        Timber.e("%s is denied.", permission.name);
                        if (permission.name.equals(WRITE_EXTERNAL_STORAGE)) {
                            showAlertDialog("在系统设置-应用-权限中开启储存权限，已正常使用。", "去设置", true);
                        }
                    }
                });
    }

    private void showAlertDialog(String content, String confirmText, boolean denied) {
        SweetAlertDialog dialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
        dialog.setConfirmText(confirmText);
        //不支持点击返回退出
        dialog.setCancelable(false);
        dialog.setTitleText("权限申请");
        dialog.setContentText(content);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                if (!denied) {
                    requestPermissions();
                } else {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, CODE_WRITE);
                }
                dialog.dismiss();
            }
        });
        dialog.setCancelText("取消");
        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                MainActivity.this.finish();
            }
        });
        dialog.showCancelButton(true);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog.show();
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
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
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

    @Subscriber(tag = MAIN_CURRENTITEM)
    public void showCurrentItem(int integer) {
        vpContent.setCurrentItem(integer);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_WRITE) {
            //TODO something;
            requestPermissions();
        }
    }

    /**
     * 双击返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();

            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            killAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
