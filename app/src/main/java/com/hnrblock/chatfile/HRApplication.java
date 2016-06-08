package com.hnrblock.chatfile;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
import android.content.Context;

@ReportsCrashes(formUri = "http://farmerspoint.in/patrick/crash.php")
public class HRApplication extends Application {
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);

		// The following line triggers the initialization of ACRA
		ACRA.init(this);
	}
}
