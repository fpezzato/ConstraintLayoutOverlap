package org.fpezzato.lib;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;

import static android.animation.ObjectAnimator.ofFloat;
import static org.fpezzato.lib.util.OverlappingDetector.isVerticalOverlapping;

public class NotOverlappingConstraintLayout extends ConstraintLayout {

    private static final int DURATION = 100;
    private float originalViewToHideAlpha;
    private WeakReference<View> hide = new WeakReference<>(null);
    private WeakReference<View> keep = new WeakReference<>(null);
    private ObjectAnimator animatorOut;
    private ObjectAnimator animatorIn;
    private boolean firstPassDone = false;

    public NotOverlappingConstraintLayout(Context context) {
        super(context);
    }

    public NotOverlappingConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotOverlappingConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NotOverlappingConstraintLayout hide(@NonNull View hide) {
        this.hide = new WeakReference<>(hide);
        this.originalViewToHideAlpha = hide.getAlpha();
        return this;
    }

    public void whenOverlapping(@NonNull View keep) {
        this.keep = new WeakReference<>(keep);
        checkNotNullAndSameParent();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        View viewToHide = hide.get();
        View viewToKeep = keep.get();

        if (viewToHide != null && viewToKeep != null) {
            if (isVerticalOverlapping(viewToHide, viewToKeep)) {
                if (!firstPassDone) {
                    viewToHide.setAlpha(0);
                    firstPassDone = true;
                } else {
                    if (animatorOut == null || !animatorOut.isRunning()) {
                        animatorOut = ofFloat(viewToHide, View.ALPHA, 0)
                                .setDuration(DURATION);
                        animatorOut.start();
                    }
                }

            } else {
                if (!firstPassDone) {
                    viewToHide.setAlpha(originalViewToHideAlpha);
                    firstPassDone = true;
                }
                if (animatorIn == null || !animatorIn.isRunning()) {
                    animatorIn = ofFloat(viewToHide, View.ALPHA, originalViewToHideAlpha)
                            .setDuration(DURATION);
                    animatorIn.start();
                }
            }
        }
    }

    private void checkNotNullAndSameParent() {
        if (hide == null || hide.get() == null) {
            throw new IllegalArgumentException("hide() must be called with a non null view");
        }

        if (keep == null || keep.get() == null) {
            throw new IllegalArgumentException("whenOverlapping() must be called with a non null view");
        }

        //noinspection ObjectEquality //we really care about having the  same parent
        if (hide.get().getParent() != keep.get().getParent()) {
            throw new IllegalArgumentException("The views must be siblings");
        }
    }

}
