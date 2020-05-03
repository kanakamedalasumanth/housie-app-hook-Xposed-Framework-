package com.example.housie_app_hook;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedHelpers;


public class AppHooker  implements IXposedHookZygoteInit, IXposedHookLoadPackage {
    private static String TAG = "AppHooker";


    static {
        Log.i(TAG,"<<<<<<<<<<<<<<<<< Class loaded on VM >>>>>>>>>>>>>>>>>>");


    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        try {


        if(lpparam.packageName.equals("com.viaangaming.housiequiz"))
        {
            Log.i(TAG,"--------------> Start <--------------------");
            Log.i(TAG,"packageName:"+lpparam.packageName);
            Log.i(TAG,"processName:"+lpparam.processName);
            Log.i(TAG,"className:"+lpparam.appInfo.className);
            Log.i(TAG,"Hey!!! Package found :-) "+lpparam.appInfo.className);
            Class x = lpparam.classLoader.getClass();;
            Class HousieQuizApplicationClass = Class.forName("com.viaangaming.housiequiz.HousieQuizApplication",false,lpparam.classLoader);
            Log.i(TAG,"Class Successfully loaded :"+HousieQuizApplicationClass);
            Class at = Class.forName("android.app.LoadedApk", false, lpparam.classLoader);
            XposedBridge.hookAllMethods(at, "makeApplication", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Context context = (Context)param.getResult();
                    Log.i(TAG,"On After Hook 1:"+param.getResult().getClass().getName());
                    Toast.makeText(context.getApplicationContext(),"Fucking Bitch 1",Toast.LENGTH_LONG);


                }

                    });
            XposedHelpers.findAndHookMethod("android.app.LoadedApk", lpparam.classLoader, "makeApplication", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Context context = (Application)param.getResult();
                    Log.i(TAG,"On After Hook 2:"+param.getResult().getClass().getName());

                    Toast.makeText(context.getApplicationContext(),"Fucking Bitch",Toast.LENGTH_LONG);


                }
            });








            Log.i(TAG,"--------------> End <--------------------");






        }
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
           Log.i(TAG,"Class not found "+e.getMessage());
        }


    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        Log.i(TAG,"On initZygote module:"+startupParam.modulePath+ " ,startsSystemServer:"+startupParam.startsSystemServer);

    }

    void handleHook(final XC_LoadPackage.LoadPackageParam lpparam) {
    }


}
