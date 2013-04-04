package com.mda.coordinatetracker.demos;

import android.app.Application;
import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formUri = "http://www.bugsense.com/api/acra?api_key=87d96896", formKey="")
public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        ACRA.init(this);
        super.onCreate();
    }

}
