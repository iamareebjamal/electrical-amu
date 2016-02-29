package amu.electrical.deptt.utils;

import amu.electrical.deptt.R;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import java.util.Random;

public class Colors {

    private static final Random RANDOM = new Random();
    private static final int[] COLORS = new int[]{R.color.green_main, R.color.pink_500, R.color.orange,
            R.color.purple, R.color.indigo, R.color.cyan, R.color.blue_grey,
            R.color.red_accent};

    public static int getRandomColor() {
        return COLORS[RANDOM.nextInt(COLORS.length)];
    }

    public static void tintFab(FloatingActionButton fab, Context context) {
        DrawableCompat.setTint(fab.getDrawable(), ContextCompat.getColor(context, R.color.text_dark));
    }
}
