package com.github.vipulasri.timelineview;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class LineType {
    public static final int NORMAL = 0;
    public static final int BEGIN = 1;
    public static final int END = 2;
    public static final int ONLYONE = 3;

    @IntDef(flag=true, value={
        LineType.NORMAL,
        LineType.BEGIN,
        LineType.END,
        LineType.ONLYONE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LineTypeOptions {}

}

