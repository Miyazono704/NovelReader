package com.example.NovelReader;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.chad.library.adapter.base.animation.BaseAnimation;

import org.jetbrains.annotations.NotNull;

public class MyAnimation3 implements BaseAnimation {

    @NotNull
    @Override
    public Animator[] animators(@NotNull View view) {
        Animator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1f);//从底部不透明
        alpha.setDuration(450); //设置动画的过渡时间
        //移动当前的view层次中处在最顶层的view的高度的过程中透明
        Animator translationY =
                ObjectAnimator.ofFloat(view, "translationY", view.getRootView().getHeight(), 0f);
        translationY.setDuration(450); //设置动画的过渡时间
        translationY.setInterpolator(new DecelerateInterpolator(1.2f));
        //又不透明
        return new Animator[]{alpha, translationY};
    }
}
