package amu.electrical.deptt.utils;

import amu.electrical.deptt.R;

import java.util.Random;

public class Colors {

    private static final Random RANDOM = new Random();

    public static int getRandomColor() {
        switch (RANDOM.nextInt(7)) {
            default:
            case 0:
                return R.color.green_main;
            case 1:
                return R.color.pink_500;
            case 2:
                return R.color.orange;
            case 3:
                return R.color.purple;
            case 4:
                return R.color.indigo;
            case 5:
                return R.color.cyan;
            case 6:
                return R.color.blue_grey;
        }
    }
}
