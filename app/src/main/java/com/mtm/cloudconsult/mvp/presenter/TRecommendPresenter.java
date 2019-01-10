package com.mtm.cloudconsult.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.LogUtils;
import com.mtm.cloudconsult.app.api.ConstantsImageUrl;
import com.mtm.cloudconsult.app.utils.SPUtils;
import com.mtm.cloudconsult.app.utils.StringUtils;
import com.mtm.cloudconsult.mvp.contract.TRecommendContract;
import com.mtm.cloudconsult.mvp.model.bean.AndroidBean;
import com.mtm.cloudconsult.mvp.model.bean.FrontpageBean;
import com.mtm.cloudconsult.mvp.model.bean.GankIoDayBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@FragmentScope
public class TRecommendPresenter extends BasePresenter<TRecommendContract.Model, TRecommendContract.View> {
    private ArrayList<List<AndroidBean>> lists;
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private ArrayList<String> mBannerImages;

    private static final String HOME_ONE = "home_one";
    private static final String HOME_TWO = "home_two";
    private static final String HOME_SIX = "home_six";

    @Inject
    public TRecommendPresenter(TRecommendContract.Model model, TRecommendContract.View rootView) {
        super(model, rootView);

    }

    /**
     * 数据请求-轮播图
     */
    public void showBannerPage(int dynamicKey, boolean updat) {
        Observable<FrontpageBean> requestInfo = mModel.showBannerPage(dynamicKey, updat);
        requestInfo.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleSubscriber<FrontpageBean>(mErrorHandler) {
                    @Override
                    public void onNext(FrontpageBean bean) {

                        if (mBannerImages == null) {
                            mBannerImages = new ArrayList<String>();
                        } else {
                            mBannerImages.clear();
                        }
                        if (bean != null && bean.getResult() != null && bean.getResult().getFocus() != null && bean.getResult().getFocus().getResult() != null) {
                            final ArrayList<FrontpageBean.ResultBannerBean.FocusBean.ResultBeanX> result = (ArrayList<FrontpageBean.ResultBannerBean.FocusBean.ResultBeanX>) bean.getResult().getFocus().getResult();
                            if (result != null && result.size() > 0) {
                                for (int i = 0; i < result.size(); i++) {
                                    //获取所有图片
                                    mBannerImages.add(result.get(i).getRandpic());
                                }
                                if (mRootView != null) {
                                    mRootView.showBannerView(mBannerImages, result);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void showRecyclerViewData(String year, String month, String day) {
        SPUtils.putString(HOME_ONE, "");
        SPUtils.putString(HOME_TWO, "");
        SPUtils.putString(HOME_SIX, "");
        LogUtils.warnInfo(year + month + day);
        Observable<GankIoDayBean> requestInfo = mModel.getGankIoDay(year, month, day);
        requestInfo.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleSubscriber<GankIoDayBean>(mErrorHandler) {
                    @Override
                    public void onNext(GankIoDayBean gankIoDayBean) {
                        lists = new ArrayList<>();
                        GankIoDayBean.ResultsBean results = gankIoDayBean.getResults();
                        if (results.getAndroid() != null && results.getAndroid().size() > 0) {
                            addUrlList(lists, results.getAndroid(), "Android");
                        }
                        if (results.getWelfare() != null && results.getWelfare().size() > 0) {
                            addUrlList(lists, results.getWelfare(), "福利");
                        }
                        if (results.getiOS() != null && results.getiOS().size() > 0) {
                            addUrlList(lists, results.getiOS(), "IOS");
                        }
                        if (results.getRestMovie() != null && results.getRestMovie().size() > 0) {
                            addUrlList(lists, results.getRestMovie(), "休息视频");
                        }
                        if (results.getResource() != null && results.getResource().size() > 0) {
                            addUrlList(lists, results.getResource(), "拓展资源");
                        }
                        if (lists.size() > 0 && lists.size() > 0) {
                            if (mRootView != null) {
                                mRootView.showListView(lists);
                            }
                        } else {
                            //一直请求获取上一天的数据，直到请求到数据为止
                            ArrayList<String> lastTime = StringUtils.getLastTime(year, month, day);
                            try {
                                showRecyclerViewData(lastTime.get(0), lastTime.get(1), lastTime.get(2));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    // subList没有实现序列化！缓存时会出错！
    private void addUrlList(List<List<AndroidBean>> lists, List<AndroidBean> arrayList, String typeTitle) {
        // title
        AndroidBean bean = new AndroidBean();
        bean.setType_title(typeTitle);
        ArrayList<AndroidBean> androidBeen = new ArrayList<>();
        androidBeen.add(bean);
        lists.add(androidBeen);

        int androidSize = arrayList.size();

        if (androidSize > 0 && androidSize < 4) {
            lists.add(addUrlList(arrayList, androidSize));
        } else if (androidSize >= 4) {
//
            ArrayList<AndroidBean> list1 = new ArrayList<>();
            ArrayList<AndroidBean> list2 = new ArrayList<>();
//
            for (int i = 0; i < androidSize; i++) {
                if (i < 3) {
                    list1.add(getAndroidBean(arrayList, i, androidSize));
                } else if (i < 6) {
                    list2.add(getAndroidBean(arrayList, i, androidSize));
                }
            }
            lists.add(list1);
            lists.add(list2);
        }
    }

    private AndroidBean getAndroidBean(List<AndroidBean> arrayList, int i, int androidSize) {

        AndroidBean androidBean = new AndroidBean();
        // 标题
        androidBean.setDesc(arrayList.get(i).getDesc());
        // 类型
        androidBean.setType(arrayList.get(i).getType());
        // 跳转链接
        androidBean.setUrl(arrayList.get(i).getUrl());
        // 随机图的url
        if (androidSize == 4) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_ONE_URLS[getRandom(1)]);//一图
        } else if (androidSize == 5) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_TWO_URLS[getRandom(2)]);//两图
        } else if (androidSize >= 6) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_SIX_URLS[getRandom(3)]);//三小图
        }
        return androidBean;
    }


    private List<AndroidBean> addUrlList(List<AndroidBean> arrayList, int androidSize) {
        List<AndroidBean> tempList = new ArrayList<>();
        for (int i = 0; i < androidSize; i++) {
            AndroidBean androidBean = new AndroidBean();
            // 标题
            androidBean.setDesc(arrayList.get(i).getDesc());
            // 类型
            androidBean.setType(arrayList.get(i).getType());
            // 跳转链接
            androidBean.setUrl(arrayList.get(i).getUrl());
//            DebugUtil.error("---androidSize:  " + androidSize);
            // 随机图的url
            if (androidSize == 1) {
                androidBean.setImage_url(ConstantsImageUrl.HOME_ONE_URLS[getRandom(1)]);//一图
            } else if (androidSize == 2) {
                androidBean.setImage_url(ConstantsImageUrl.HOME_TWO_URLS[getRandom(2)]);//两图
            } else if (androidSize == 3) {
                androidBean.setImage_url(ConstantsImageUrl.HOME_SIX_URLS[getRandom(3)]);//三图
            }
            tempList.add(androidBean);
        }
        return tempList;
    }

    /**
     * 取不同的随机图，在每次网络请求时重置
     */
    private int getRandom(int type) {
        String saveWhere = null;
        int urlLength = 0;
        if (type == 1) {
            saveWhere = HOME_ONE;
            urlLength = ConstantsImageUrl.HOME_ONE_URLS.length;
        } else if (type == 2) {
            saveWhere = HOME_TWO;
            urlLength = ConstantsImageUrl.HOME_TWO_URLS.length;
        } else if (type == 3) {
            saveWhere = HOME_SIX;
            urlLength = ConstantsImageUrl.HOME_SIX_URLS.length;
        }

        String homeSix = SPUtils.getString(saveWhere, "");
        if (!TextUtils.isEmpty(homeSix)) {
            // 已取到的值
            String[] split = homeSix.split(",");
            LogUtils.warnInfo("homeSix="+homeSix);
            Random random = new Random();
            for (int j = 0; j < urlLength; j++) {
                int randomInt = random.nextInt(urlLength);

                boolean isUse = false;
                for (String aSplit : split) {
                    if (!TextUtils.isEmpty(aSplit) && String.valueOf(randomInt).equals(aSplit)) {
                        isUse = true;
                        break;
                    }
                }
                if (!isUse) {
                    StringBuilder sb = new StringBuilder(homeSix);
                    sb.insert(0, randomInt + ",");
                    SPUtils.putString(saveWhere, sb.toString());
                    return randomInt;
                }
            }

        } else {
            Random random = new Random();
            int randomInt = random.nextInt(urlLength);
            SPUtils.putString(saveWhere, randomInt + ",");
            return randomInt;
        }
        return 0;
    }
}
