package org.fpezzato.constraintlayoutoverlap;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;

public class EmptyAnimator extends ValueAnimator {
    @Override
    public long getStartDelay() {
        return 0;
    }

    @Override
    public void setStartDelay(long startDelay) {

    }

    @Override
    public ValueAnimator setDuration(long duration) {
        return this;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public void setInterpolator(TimeInterpolator value) {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
