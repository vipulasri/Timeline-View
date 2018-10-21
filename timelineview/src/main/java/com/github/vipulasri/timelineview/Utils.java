package com.github.vipulasri.timelineview;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.RestrictTo;
import android.util.TypedValue;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class Utils {

    public static int dpToPx(float dp, Context context) {
        return dpToPx(dp, context.getResources());
    }

    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }
}
