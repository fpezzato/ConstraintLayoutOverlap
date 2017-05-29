package org.fpezzato.lib.util;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;

public class OverlappingDetector {

    public static boolean isVerticalOverlapping(@NonNull View view1, @NonNull View view2) {
        return !(bottomWithMargin(view1) < topWithMargin(view2) || topWithMargin(view1) > bottomWithMargin(view2));
    }

    private static int topWithMargin(@NonNull View view) {
        int safeTop = view.getTop();
        if (view.getLayoutParams() instanceof MarginLayoutParams) {
            safeTop -= Math.min(((MarginLayoutParams) view.getLayoutParams()).topMargin, safeTop);
        }
        return safeTop;
    }

    private static int bottomWithMargin(@NonNull View view) {
        int safeBottom = view.getBottom();
        if (view.getLayoutParams() instanceof MarginLayoutParams) {
            safeBottom += ((MarginLayoutParams) view.getLayoutParams()).bottomMargin;
        }
        return safeBottom;
    }
}
