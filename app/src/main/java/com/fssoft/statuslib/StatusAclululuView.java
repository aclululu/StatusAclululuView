package com.fssoft.statuslib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AnimRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fssoft.statusaclululuview.R;
import com.fssoft.customprogress.AVLoadingIndicatorView;

/**
 * 多状态页面
 */
public class StatusAclululuView extends LinearLayout {

    private static final boolean DEFAULT_ANIM_ENABLED = true;
    private static final int DEFAULT_IN_ANIM = android.R.anim.fade_in;
    private static final int DEFAULT_OUT_ANIM = android.R.anim.fade_out;


    private boolean animationEnabled;
    private Animation inAnimation;
    private Animation outAnimation;
    private int animCounter;

    private View content;
    private LinearLayout status_contain;
    private AVLoadingIndicatorView status_av;
    private ImageView status_iv;
    private TextView status_tv;
    private Button status_btn;

    public StatusAclululuView(Context context) {
        this(context, null);
    }

    public StatusAclululuView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.stfStatefulLayout, 0, 0);
        animationEnabled = array.getBoolean(R.styleable.stfStatefulLayout_stfAnimationEnabled, DEFAULT_ANIM_ENABLED);
        inAnimation = anim(array.getResourceId(R.styleable.stfStatefulLayout_stfInAnimation, DEFAULT_IN_ANIM));
        outAnimation = anim(array.getResourceId(R.styleable.stfStatefulLayout_stfOutAnimation, DEFAULT_OUT_ANIM));
        array.recycle();
    }

    public boolean isAnimationEnabled() {
        return animationEnabled;
    }

    public void setAnimationEnabled(boolean animationEnabled) {
        this.animationEnabled = animationEnabled;
    }

    public Animation getInAnimation() {
        return inAnimation;
    }

    public void setInAnimation(Animation animation) {
        inAnimation = animation;
    }

    public void setInAnimation(@AnimRes int anim) {
        inAnimation = anim(anim);
    }

    public Animation getOutAnimation() {
        return outAnimation;
    }

    public void setOutAnimation(Animation animation) {
        outAnimation = animation;
    }

    public void setOutAnimation(@AnimRes int anim) {
        outAnimation = anim(anim);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) throw new IllegalStateException("StateAclululuView只能包含一个子View");
        if (isInEditMode()) return;
        setOrientation(VERTICAL);
        content = getChildAt(0); // assume first child as content
        LayoutInflater.from(getContext()).inflate(R.layout.status_temp, this, true);
        status_contain = (LinearLayout) findViewById(R.id.status_contain);
        status_av = (AVLoadingIndicatorView) findViewById(R.id.status_av);
        status_iv = (ImageView) findViewById(R.id.status_iv);
        status_tv = (TextView) findViewById(R.id.status_tv);
        status_btn = (Button) findViewById(R.id.status_btn);
    }


    /**
     * 显示内容
     */
    public void showContent() {
        if (isAnimationEnabled()) {
            status_contain.clearAnimation();
            content.clearAnimation();
            final int animCounterCopy = ++animCounter;
            if (status_contain.getVisibility() == VISIBLE) {
                outAnimation.setAnimationListener(new CustomAnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (animCounter != animCounterCopy) return;
                        status_contain.setVisibility(GONE);
                        content.setVisibility(VISIBLE);
                        content.startAnimation(inAnimation);
                    }
                });
                status_contain.startAnimation(outAnimation);
            }
        } else {
            status_contain.setVisibility(GONE);
            content.setVisibility(VISIBLE);
        }
    }

    // 加载。。。 //

    public void showLoading() {
        showLoading(R.string.status_loaddddding);
    }

    public void showLoading(@StringRes int resId) {
        showLoading(str(resId));
    }

    public void showLoading(String message) {
        showCustom(new CustomStateOptions()
                .message(message)
                .loading());
    }

    // 内容为空 //

    public void showEmpty(OnClickListener clickListener) {
        showEmpty(R.string.status_content_empty,clickListener);
    }

    public void showEmpty(@StringRes int resId,OnClickListener clickListener) {
        showEmpty(str(resId),clickListener);
    }

    public void showEmpty(String message,OnClickListener clickListener) {
        showCustom(new CustomStateOptions()
                .message(message)
                .image(R.drawable.ic_empty)
                .buttonText(str(R.string.status_error))
                .buttonClickListener(clickListener));
    }

    // 加载错误 //

    public void showError(OnClickListener clickListener) {
        showError(R.string.status_error, clickListener);
    }

    public void showError(@StringRes int resId, OnClickListener clickListener) {
        showError(str(resId), clickListener);
    }

    public void showError(String message, OnClickListener clickListener) {
        showCustom(new CustomStateOptions()
                .message(message)
                .image(R.drawable.ic_error)
                .buttonText(str(R.string.status_retry))
                .buttonClickListener(clickListener));
    }

    // 无网络

    public void showOffline(OnClickListener clickListener) {
        showOffline(R.string.status_no_network, clickListener);
    }

    public void showOffline(@StringRes int resId, OnClickListener clickListener) {
        showOffline(str(resId), clickListener);
    }

    public void showOffline(String message, OnClickListener clickListener) {
        showCustom(new CustomStateOptions()
                .message(message)
                .image(R.drawable.ic_no_network)
                .buttonText(str(R.string.status_retry))
                .buttonClickListener(clickListener));
    }




    /**
     * 用户自定义状态页面  如果没有设置onClickListener则重新加载按钮隐藏
     * @param options
     */
    public void showCustom(final CustomStateOptions options) {
        if (isAnimationEnabled()) {
            status_contain.clearAnimation();
            content.clearAnimation();
            final int animCounterCopy = ++animCounter;
            if (status_contain.getVisibility() == GONE) {
                outAnimation.setAnimationListener(new CustomAnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (animCounterCopy != animCounter) return;
                        content.setVisibility(GONE);
                        status_contain.setVisibility(VISIBLE);
                        status_contain.startAnimation(inAnimation);
                    }
                });
                content.startAnimation(outAnimation);
                state(options);
            } else {
                outAnimation.setAnimationListener(new CustomAnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (animCounterCopy != animCounter)
                            return;
                        state(options);
                        status_contain.startAnimation(inAnimation);
                    }
                });
                status_contain.startAnimation(outAnimation);
            }
        } else {
            content.setVisibility(GONE);
            status_contain.setVisibility(VISIBLE);
            state(options);
        }
    }

    // 通过options设置对应状态 //

    private void state(CustomStateOptions options) {
        if (!TextUtils.isEmpty(options.getMessage())) {
            status_tv.setVisibility(VISIBLE);
            status_tv.setText(options.getMessage());
        } else {
            status_tv.setVisibility(GONE);
        }

        if (options.isLoading()) {
            status_av.setVisibility(VISIBLE);
            status_iv.setVisibility(GONE);
            status_btn.setVisibility(GONE);
        } else {
            status_av.setVisibility(GONE);
            if (options.getImageRes() != 0) {
                status_iv.setVisibility(VISIBLE);
                status_iv.setImageResource(options.getImageRes());
            } else {
                status_iv.setVisibility(GONE);
            }

            if (options.getClickListener() != null) {
                status_btn.setVisibility(VISIBLE);
                status_btn.setOnClickListener(options.getClickListener());
                if (!TextUtils.isEmpty(options.getButtonText())) {
                    status_btn.setText(options.getButtonText());
                }
            } else {
                status_btn.setVisibility(GONE);
            }
        }
    }

    private String str(@StringRes int resId) {
        return getContext().getString(resId);
    }

    private Animation anim(@AnimRes int resId) {
        return AnimationUtils.loadAnimation(getContext(), resId);
    }

}
