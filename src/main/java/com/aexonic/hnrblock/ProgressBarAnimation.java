package com.aexonic.hnrblock;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

/**
 * Created by Parikshit Patil on 12/17/2015.
 */
public class ProgressBarAnimation extends Animation
{
    RoundCornerProgressBar roundCornerProgressBar;
    private float from;
    private float  to;

    public ProgressBarAnimation(RoundCornerProgressBar roundCornerProgressBar, float from, float to)
    {
        super();
        this.roundCornerProgressBar = roundCornerProgressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t)
    {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        roundCornerProgressBar.setProgress(value);
    }
}
