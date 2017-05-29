package org.fpezzato.lib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;

import static org.fpezzato.lib.util.OverlappingDetector.isVerticalOverlapping;

public class OverlappingConstraintLayout extends ConstraintLayout {

    private WeakReference<View> hide = new WeakReference<>(null);
    private WeakReference<View> keep = new WeakReference<>(null);

    public OverlappingConstraintLayout(Context context) {
        super(context);
    }

    public OverlappingConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverlappingConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OverlappingConstraintLayout hide(@NonNull View hide) {
        this.hide = new WeakReference<>(hide);
        return this;
    }

    public void whenOverlappedBy(@NonNull View keep) {
        this.keep = new WeakReference<>(keep);
        checkNotNullAndSameParent();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(hide.get() != null && keep.get() != null) {
            if (isVerticalOverlapping(hide.get(), keep.get())){
                hide.get().setVisibility(INVISIBLE);
            }
        }
    }

    private void checkNotNullAndSameParent() {
        if (hide == null || hide.get() == null) {
            throw new IllegalArgumentException("hide() must be called with a non null view");
        }

        if (keep == null || keep.get() == null) {
            throw new IllegalArgumentException("whenOverlappedBy() must be called with a non null view");
        }

        //noinspection ObjectEquality //we really care about having the  same parent
        if (hide.get().getParent() != keep.get().getParent()) {
            throw new IllegalArgumentException("The views must be siblings");
        }
    }


}
