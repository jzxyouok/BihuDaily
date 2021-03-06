package com.white.bihudaily.splash;

import android.app.Activity;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.BaseView;
import com.white.bihudaily.bean.StartImg;

/**
 * Author White
 * Date 2016/8/13
 * Time 12:47
 */
public interface SplachContract {

    interface View extends BaseView<Presenter> {

        void showImg(String imgUrl);

        void showText(String text);

        void turn2DailysActivity();

        void getStartImgSuccess(StartImg startImg);

        void showImg(int resId);
    }

    interface Presenter extends BasePresenter {

        void loadImg();

        void showImg(Activity context);

    }
}
