package com.example.housie_app_hook;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.MessagePattern;
import android.os.Build;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.logging.LogRecord;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedHelpers;
//import es.dmoral.toasty.Toasty;


public class AppHooker extends Application implements IXposedHookZygoteInit, IXposedHookLoadPackage{
    private static String TAG = "AppHooker";
    Context context=null;


     XSharedPreferences sharedPreferences;


    Object GameScreenActivityInstance=null;



    static {
        Log.i(TAG,"<<<<<<<<<<<<<<<<< Class defination loaded on VM: >>>>>>>>>>>>>>>>>>"+AppHooker.class.getPackage().getName());
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
//            sharedPreferences.reload();
//           Log.i(TAG,""+ sharedPreferences.getBoolean(MainActivity.AUTO_ANSWER_KEY,false));
//            if(lpparam.isFirstApplication)
//            {
//                Toasty.Config.getInstance().setTextSize(24).apply();;
//            }
//            else
//            {
//                Toasty.Config.reset();
//                Toasty.Config.getInstance().setTextSize(12).allowQueue(false).apply();;
//            }

            Log.i(TAG,"-------------->  Application found housiequiz  <--------------------");

            XposedHelpers.findAndHookMethod("android.app.LoadedApk",lpparam.classLoader ,"makeApplication",boolean.class, Instrumentation.class, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    context = (Application)param.getResult();

                    //Toasty.error(context.getApplicationContext(), "---->Yooo My Code Successfully Injected :-)<-------------",Toasty.LENGTH_LONG).show();
                   Toast.makeText(context.getApplicationContext(), Html.fromHtml("<p style=\"font-family:'Courier New'\"><b>Successfully injected my code :-)<br>Happy Gamming</p>"),Toast.LENGTH_LONG).show();
                }
            });

            Class daClazz = Class.forName("com.viaangaming.housiequiz.Activity.da",false,lpparam.classLoader);
            XposedBridge.hookAllMethods(daClazz, "run", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Log.i(TAG,"After On Run : ---->com.viaangaming.housiequiz.Activity.da");
                    if(GameScreenActivityInstance!=null) {
                       // String question = (String) XposedHelpers.getObjectField(GameScreenActivityInstance, "K");
                        String answer = (String) XposedHelpers.getObjectField(GameScreenActivityInstance, "F");
                        //Log.i(TAG,"Question :"+question);
                        Log.i(TAG,"Answer :"+answer);
                        if (context != null){
                            TextView optionA = (TextView) XposedHelpers.getObjectField(GameScreenActivityInstance,"L");
                            TextView optionB = (TextView) XposedHelpers.getObjectField(GameScreenActivityInstance,"M");
                            TextView optionC = (TextView) XposedHelpers.getObjectField(GameScreenActivityInstance,"N");
                            byte max = 9;
                            byte min = 2;
                            int randomNamber =(int)(Math.random() * ((max - min) + 1)) + min;
                            byte choiceNumber = 0;
                            if(optionA.getText().equals(answer))
                            {
                                choiceNumber =1;
                                //setAnswerAt(randomNamber,optionA);
                            }
                            else if(optionB.getText().equals(answer))
                            {
                                choiceNumber =2;
                                //setAnswerAt(randomNamber,optionB);
                            }
                            else if(optionC.getText().equals(answer))
                            {
                                choiceNumber =3;
                                //setAnswerAt(randomNamber,optionC);
                            }

                            Toast.makeText(context.getApplicationContext(), Html.fromHtml("<p style=\"font-family:'Courier New'\"><b>Your Answer: </b>"+answer+"("+choiceNumber+")</p><br><b>Auto Set Enable at "+randomNamber+" Sec</b>"),Toast.LENGTH_LONG).show();

                        }
                           // Toasty.info(context.getApplicationContext(), "Your Answer: " + answer, Toast.LENGTH_LONG, true).show();
                    }
                }
            });


            Class GameScreenActivityClazz = Class.forName("com.viaangaming.housiequiz.Activity.GameScreenActivity",false,lpparam.classLoader);
            XposedBridge.hookAllMethods(GameScreenActivityClazz, "onCreate", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    GameScreenActivityInstance = param.thisObject;
                    Log.i(TAG,"After OnCreate :: com.viaangaming.housiequiz.Activity.GameScreenActivity");
                    if(lpparam.isFirstApplication) {
                        Toast.makeText(context.getApplicationContext(), Html.fromHtml("<p style=\"font-family:'Courier New'\">All the best!!<br><b>Sumanth -:)</b></p>"), Toast.LENGTH_LONG).show();
                    }
                    //Toasty.error(context,"\tAll the best \n\n\t By Sumanth :-)",Toast.LENGTH_LONG, true).show();

                    //xx = param.thisObject;


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
        sharedPreferences = new XSharedPreferences(this.getClass().getPackage().getName());

    }


   void setAnswerAt(int millis,TextView tx)
   {
       if(context==null)
           return;

       Handler handler = new Handler(context.getMainLooper());

       handler.postAtTime(()->{
           tx.performClick();
       },millis);


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
