package com.example.housie_app_hook;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.icu.text.MessagePattern;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.os.Handler;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.logging.LogRecord;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedHelpers;


public class AppHooker  implements IXposedHookZygoteInit, IXposedHookLoadPackage, Runnable{
    private static String TAG = "AppHooker";
    Context context=null;

    Thread runner = null;

    Object xx = null;
    static {
        Log.i(TAG,"<<<<<<<<<<<<<<<<< Class loaded on VM >>>>>>>>>>>>>>>>>>");


    }

//    AppHooker(){
//      Log.i(TAG,"Constructor Called");
//      new Thread(this).start();
//    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        try {

        if(lpparam.packageName.equals("com.viaangaming.housiequiz"))
        {
            Log.i(TAG,"--------------> Start <--------------------");
            if(runner == null) {
                runner = new Thread(this);
                runner.start();
                Log.i(TAG,"Thread started");
            }
            Log.i(TAG,"packageName:"+lpparam.packageName);
            Log.i(TAG,"processName:"+lpparam.processName);
            Log.i(TAG,"className:"+lpparam.appInfo.className);
            Log.i(TAG,"Hey!!! Package found :-) "+lpparam.appInfo.className);

            Class HousieQuizApplicationClass = Class.forName("com.viaangaming.housiequiz.HousieQuizApplication",false,lpparam.classLoader);
            Log.i(TAG,"Class Successfully loaded :"+HousieQuizApplicationClass);


            XposedHelpers.findAndHookMethod("android.app.LoadedApk",lpparam.classLoader ,"makeApplication",boolean.class, Instrumentation.class, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    context = (Application)param.getResult();


                   // Toast.makeText(context.getApplicationContext(),"Fucking Bitch",Toast.LENGTH_LONG).show();




                }
            });

            XposedBridge.hookAllMethods(HousieQuizApplicationClass, "a", new XC_MethodHook() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.i(TAG,"<-----------On Method A(No Of Params:("+param.args.length+")---------->");
                    if(param.args.length ==9)
                    {
                        param.args[6] =param.args[7];
                        param.args[8] = "success";
                        if(context!=null)
                        {
                            showToastMessage(param.args[6].toString());
                        }
                    }
                    else if(param.args.length ==8)
                    {
                        ;
                        if(context!=null)
                        {
                            showToastMessage(param.args[6].toString());
                        }
                    }
                    for(int i=0;i<param.args.length;i++)
                    {


                        if(param.args[i].getClass().toString().equals( "class java.lang.String"))
                        {

                            String a = (String) param.args[i];
                            Log.i(TAG, "paramString" + i + " = " + a);
                        }
                        else{
                            Log.i(TAG,""+(param.args[i].getClass().toString()));
                        }

                        //}
                    }

//                    for (StackTraceElement ste : context.getMainLooper().getThread().currentThread().getStackTrace())
//                    {
//                        Log.i(TAG,""+ste.toString());
//                    }



                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                }
            });

            XposedBridge.hookAllMethods(HousieQuizApplicationClass, "b", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.i(TAG,"<-----------On Method B(No Of Params:("+param.args.length+")---------->");
                    if(param.args.length ==9)
                    {
                        param.args[6] =param.args[7];
                        if(context!=null)
                        {
                            showToastMessage(param.args[6].toString());
                        }
                        param.args[8] = "success";
                    }
                    else if(param.args.length ==8)
                    {
                        if(context!=null)
                        {
                            showToastMessage(param.args[6].toString());
                        }

                    }
                    for(int i=0;i<param.args.length;i++)
                    {


                        if(param.args[i].getClass().toString().equals( "class java.lang.String"))
                        {

                            String a = (String) param.args[i];
                            Log.i(TAG, "paramString" + i + " = " + a);
                        }
                        else{
                            Log.i(TAG,""+(param.args[i].getClass().toString()));
                        }

                        //}
                    }



                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                }
            });

            Class KYCActivityClass = Class.forName("com.viaangaming.housiequiz.Activity.da",false,lpparam.classLoader);
            XposedBridge.hookAllMethods(KYCActivityClass, "run", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);


                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Log.i(TAG,"---->com.viaangaming.housiequiz.Activity.da");
                    try
                    {
                        String ch = (String)XposedHelpers.getObjectField(param.thisObject,"a.b.a.q.getJSONObject(\"questionData\")");
                        Log.i(TAG,"Check This 1:"+ch);
                        ch = (String)XposedHelpers.getObjectField(param.thisObject,"a.b.a.r.getJSONObject(\"correctAnswer\")");
                        Log.i(TAG,"Check This 2:"+ch);
//
                    }
                    catch (Exception e)
                    {
                        Log.i(TAG,"On Error :"+e.getMessage());
                    }


                }
            });


            Class KYCActivityClass2 = Class.forName("com.viaangaming.housiequiz.Activity.GameScreenActivity",false,lpparam.classLoader);
            XposedBridge.hookAllMethods(KYCActivityClass2, "onCreate", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);


                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Log.i(TAG,"Sumo ---->com.viaangaming.housiequiz.Activity.GameScreenActivity");
                    xx = param.thisObject;


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



    @Override
    public void run() {
        while (true) {
            if (context != null)


//                new Handler(context.getMainLooper()).post(()->{
//                    Toast.makeText(context.getApplicationContext(), "Fucking Bitch", Toast.LENGTH_SHORT).show();
//                });
                //Log.i(TAG,"Showing Toast");
            try {
                if(xx!=null)
                {
                   String currentData =  (String)XposedHelpers.getObjectField(xx,"F");
                   Log.i(TAG,"Answer From Activity F:"+currentData);

                }
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void showToastMessage(String msg){
     if(context!=null)
     {
         new Handler(context.getMainLooper()).post(()->{
            Toast.makeText(context.getApplicationContext(),msg,Toast.LENGTH_LONG).show();
         });
     }

    }
}
