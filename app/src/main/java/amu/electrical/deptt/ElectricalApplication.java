package amu.electrical.deptt;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseInstallation;

public class ElectricalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "NacoBY7Ou7P5BteNm8nXk47uaMo4DPgB5TRHGzYL", "lb8tkGEsV5DWkRDz0G9Ei7DouUyptt1YmiS16Yg6");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
