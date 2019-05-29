package com.wisnusaputra.pinekaapp.Utils;

import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class AnimPost extends Animation {

    int mFromHeight;
    int mToHeight;
    CardView mView;

    public AnimPost(CardView view, int fromHeight, int toHeight) {
        this.mView = view;
        this.mFromHeight = fromHeight;
        this.mToHeight = toHeight;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        int newHeight;

        if (mView.getHeight() != mToHeight) {
            newHeight = (int) (mFromHeight + ((mToHeight - mFromHeight) * interpolatedTime));
            mView.getLayoutParams().height = newHeight;
            mView.requestLayout();
        }
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

}
