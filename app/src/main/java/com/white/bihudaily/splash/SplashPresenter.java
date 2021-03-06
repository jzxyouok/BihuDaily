package com.white.bihudaily.splash;

import android.app.Activity;

import com.white.bihudaily.BasePresenterImpl;
import com.white.bihudaily.R;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.base.BaseSubscriber;
import com.white.bihudaily.bean.StartImg;
import com.white.bihudaily.data.SplashSource;
import com.white.bihudaily.utils.CommonUtil;
import com.white.bihudaily.utils.NetUtils;
import com.white.bihudaily.utils.SPUtils;
import com.white.bihudaily.utils.TransformUtils;

import rx.Subscription;

/**
 * Author White
 * Date 2016/8/13
 * Time 12:45
 */
public class SplashPresenter extends BasePresenterImpl<SplashSource, SplachContract.View> implements SplachContract.Presenter {


    public SplashPresenter(SplashSource source, SplachContract.View splashView) {
        super(source, splashView);
    }

    @Override
    public void loadImg() {
        Subscription subscription = mSource.loadImg().compose(TransformUtils.<StartImg>defaultSchedulers())
                .subscribe(new BaseSubscriber<StartImg>() {
                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onSuccess(StartImg startImg) {
                        mView.getStartImgSuccess(startImg);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void showImg(Activity context) {
        if (!NetUtils.isConnected(context)) {
            mView.turn2DailysActivity();
            return;
        }

        boolean hasOpenApp = (boolean) SPUtils.get(context, Constant.KEY_HASOPENAPP, false);
        String imgText = (String) SPUtils.get(context, Constant.KEY_STARTIMGTEXT, "@Bihu");
        mView.showText(imgText);
        if (!hasOpenApp) {
            // 第一次打开APP
            mView.showImg(R.drawable.splash);
            SPUtils.put(context, Constant.KEY_HASOPENAPP, true);
            loadImg();
        } else {
            String imgPath = (String) SPUtils.get(context, Constant.KEY_STARTIMGPATH, "");
            if (imgPath != null && !imgPath.equals("")) {
                mView.showImg(imgPath);
                String today = (String) SPUtils.get(context, Constant.KEY_TODAY, "");
                if (!CommonUtil.getToday().equals(today)) {
                    loadImg();
                }
            } else {
                // 没有缓存
                mView.showImg(R.drawable.splash);
                loadImg();
            }
        }
    }

}
