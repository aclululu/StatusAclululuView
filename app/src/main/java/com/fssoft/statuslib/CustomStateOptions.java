package com.fssoft.statuslib;

import android.support.annotation.DrawableRes;
import android.view.View;

import java.io.Serializable;


public class CustomStateOptions implements Serializable {

    @DrawableRes
    private int imageRes;
    private boolean isLoading;
    private String message;
    private String buttonText;
    private View.OnClickListener buttonClickListener;

    /**
     * 设置图片
     * @param val
     * @return
     */
    public CustomStateOptions image(@DrawableRes int val) {
        imageRes = val;
        return this;
    }

    /**
     * 是否为正在加载页面
     * @return
     */
    public CustomStateOptions loading() {
        isLoading = true;
        return this;
    }

    /**
     * 设置文字提示
     * @param val
     * @return
     */
    public CustomStateOptions message(String val) {
        message = val;
        return this;
    }

    /**
     * 按钮名称
     * @param val
     * @return
     */
    public CustomStateOptions buttonText(String val) {
        buttonText = val;
        return this;
    }

    /**
     * 设置点击事件
     * @param val
     * @return
     */
    public CustomStateOptions buttonClickListener(View.OnClickListener val) {
        buttonClickListener = val;
        return this;
    }

    public int getImageRes() {
        return imageRes;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public String getMessage() {
        return message;
    }

    public String getButtonText() {
        return buttonText;
    }

    public View.OnClickListener getClickListener() {
        return buttonClickListener;
    }

}
