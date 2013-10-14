package com.syk.dev.xposed.legacyvm;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * Xposed module that re-enables notifications for legacy Sprint voicemail system on 4.1.2 Sprint HTC One
 * 
 * Created by Steven on 7/17/13.
 */
public class LegacyCmdaMwi implements IXposedHookLoadPackage{
	
	/**
	 * Hook the phone package which creates the notifications
	 */
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
    	//XposedBridge.log("Loaded app: " + lpparam.packageName);
    	
        if (!lpparam.packageName.equals("com.android.phone"))
            return;

        XposedBridge.log("Adding hooks for package " + lpparam.packageName);
        
        findAndHookMethod("com.android.phone.util.HtcPhoneUtils", lpparam.classLoader, "isSprintVVMEnabled", new XC_MethodReplacement() {
			@Override
			protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
				XposedBridge.log("isSprintVVMEnabled() called, returning false");
				return false;
			}
        });
    }
}
