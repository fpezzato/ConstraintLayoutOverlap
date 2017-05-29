package org.fpezzato.lib.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class FakeViewGroupMarginLayoutParams extends ViewGroup.MarginLayoutParams{

    private int marginTop;

    public static FakeViewGroupMarginLayoutParams aFakeViewGroupMarginLayoutParams(){
        return new FakeViewGroupMarginLayoutParams(0,0);
    }

    public FakeViewGroupMarginLayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    private FakeViewGroupMarginLayoutParams(int width, int height) {
        super(width, height);
    }

    private FakeViewGroupMarginLayoutParams(ViewGroup.MarginLayoutParams source) {
        super(source);
    }

    private FakeViewGroupMarginLayoutParams(ViewGroup.LayoutParams source) {
        super(source);
    }

    public FakeViewGroupMarginLayoutParams withMarginTop(int marginTop){
        super.topMargin = marginTop;
        return this;
    }

    public FakeViewGroupMarginLayoutParams withMarginBottom(int marginBottom){
        super.bottomMargin = marginBottom;
        return this;
    }

}
