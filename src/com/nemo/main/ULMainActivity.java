package com.nemo.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.view.Window;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.BroadcastReceiver;
import android.os.Environment;
import android.widget.Toast;
import android.util.Log;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

//import com.android.internal.os.PowerProfile;

import com.nemo.ul.R;
import com.nemo.ul.java.ULMD5Util;
import com.nemo.ul.util.ULPackageUtil;
import com.nemo.ul.util.ULXmlUtilA;


public class ULMainActivity extends Activity {
    private static final String TAG = ULMainActivity.class.getSimpleName();
private static String mStr;
	private Button mButtonA;
	private Button mButtonB;
	private Button mButtonC;
	private TextView mTVResult;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	//requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);

		initView();
		initActionBar();
    }
	
	//A------------------------------------------------------------------
	private void onButtonAClick() {
		final StringBuilder builderA = new StringBuilder();
		builderA.append("Button A result: \n").append("--- ---\n\n");
		
		
		builderA.append(ULMD5Util.getStringMD5("ASDFGXXXYYYZZZ"));
        
		builderA.append("\n--- ---\n");
        mTVResult.setText(builderA.toString());
        
        Map<String, String> marketMap = new HashMap<String, String>();
        marketMap.put("111", "2222");
        marketMap.put("113", "2223");
        marketMap.put("114", "2224");
        marketMap.put("111", "2225");
        //ULXmlUtilA.writeValueToXML("/storage/emulated/0/gwb/write.xml", marketMap);
        
        ULXmlUtilA.writeXMLSample("/storage/emulated/0/gwb/write.xml");
        
        
		//Intent intent = new Intent("nemo.intent.action.MAIN_JUMP_TO");
        //intent.setClass(ULMainActivity.this, ULMainActivityJumpTo.class);
        //startActivity(intent);
	}
	
	//B------------------------------------------------------------------
	private void onButtonBClick() {
		final StringBuilder builderB = new StringBuilder();
		builderB.append("Button B result: \n").append("--- ---\n\n");
		
		builderB.append("\n--- ---\n");
        mTVResult.setText(builderB.toString());
        
        ULXmlUtilA.readXMLValue("/storage/emulated/0/gwb/read.xml");
	}
	
	//C------------------------------------------------------------------
	private void onButtonCClick() {
		final StringBuilder builderC = new StringBuilder();
		builderC.append("Button C result: \n").append("--- ---\n\n");
		
		builderC.append("getTest(): \n");
		
        //getPackageInfo();

        
		builderC.append("\n--- ---\n");
        mTVResult.setText(ULPackageUtil.getUninstalledCertMd5(this, "/storage/emulated/0/gwb/123.apk"));
	}
    
    private String testVol() {
    
        //PowerProfile mPowerProfile = new PowerProfile(this);
        //final double opVolt = mPowerProfile.getAveragePower(
        //            PowerProfile.POWER_BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE);
        return "";
    }
	


	private void testReflect() {
		Log.d(TAG, "1118--- getApplicationContext().getClass() = " + getApplicationContext().getClass());

	}
	
	private void testString() {
		String s1= "ab" + "cd";  
		String s2= "abc" + "d"; 
		
	}

   
    private void testLoggable() {
        Log.v(TAG, "v-v");
        Log.d(TAG, "d--d");
        Log.i(TAG, "i---i");
        Log.w(TAG, "w----w");
        Log.e(TAG, "e-----e");
        Log.e(TAG, "e------eee: " + this.getClass());
        Log.e(TAG, "e------eee22222: " + this.getClass().getSimpleName());
        Log.wtf(TAG, "wtf------wtf2: " + this.getClass().getName());

        Log.v(TAG, ""+Log.isLoggable(TAG, Log.VERBOSE));
        Log.d(TAG, ""+Log.isLoggable(TAG, Log.DEBUG));
        Log.i(TAG, ""+Log.isLoggable(TAG, Log.INFO));
        Log.w(TAG, ""+Log.isLoggable(TAG, Log.WARN));
        Log.e(TAG, ""+Log.isLoggable(TAG, Log.ERROR));

        //LOG.v(TAG, "v-v");
        //LOG.d(TAG, "d--d");
        //LOG.i(TAG, "i---i");
        //LOG.w(TAG, "w----w");
        //LOG.e(TAG, "e-----e");
        //LOG.wtf(TAG, "wtf------wtf: " + this.getClass().getName());
 
    }
	
	
	private void initView() {
        setContentView(R.layout.main_activity_layout);
		
		mButtonA = (Button) findViewById(R.id.btn_a);
		mButtonB = (Button) findViewById(R.id.btn_b);
		mButtonC = (Button) findViewById(R.id.btn_c);
		mTVResult = (TextView) findViewById(R.id.tv_result);
		
		mButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "mButtonA onClick()");
				Toast.makeText(ULMainActivity.this, "Button A!!!", Toast.LENGTH_LONG).show();
				//
	
				onButtonAClick();
            }
        });
		
		mButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "mButtonB onClick()");
				Toast.makeText(ULMainActivity.this, "Button B!!!", Toast.LENGTH_LONG).show();
				//
	
        		onButtonBClick();		
            }
        });
		
		mButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "mButtonC onClick()");
				Toast.makeText(ULMainActivity.this, "Button C!!!", Toast.LENGTH_LONG).show();
				//
	
        		onButtonCClick();		
            }
        });
		
    }

	private void initActionBar() {
		ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(
				ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE,
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE
                | ActionBar.DISPLAY_SHOW_HOME);

			actionBar.setTitle(R.string.app_name);
        }
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.pm_context_menu_settings, menu);
        //mMenuSettings = menu.findItem(R.id.menu_pm_settings);
        return true;
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
			
		case R.id.menu_pm_settings:
            break;
			
        default:
            break;
        }*/
        return super.onOptionsItemSelected(item);
    }

	@Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

	@Override
    public void onPause() {
        super.onPause();
    }

	@Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    
	
	
	
	class Base {
		public void setData(Map<String, Object> map){
			
		}
		
		public int Func(int a, int b){
			return a-b;
		}
	}

	class A extends Base {
		public void setData(LinkedHashMap<String, Object> map){
		
		}
		
		public int Func(int a, int b){
			return a+b;
		}
		
		public int Func2(int a, int b){
			return a*b;
		}
	}
    
    class Example
    {
        //private static String mStr = "";
        
        public void execute()
        {
            synchronized(Example.class) {
                for (int i = 0; i < 20; ++i)
                {
                    try
                    {
                        Thread.sleep((long) Math.random() * 1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println("Hello: " + i);
                }
            }
        }

        public void execute2()
        {
            synchronized(Example.class) {
                for (int i = 0; i < 20; ++i)
                {
                    try
                    {
                        Thread.sleep((long) Math.random() * 1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println("World: " + i);
                }
            }
        }

    }

}


class ThreadTest
    {
        public void mainxxx(String[] args)
        {
            Example example = new Example();

            Thread t1 = new Thread1(example);
            Thread t2 = new Thread2(example);

            t1.start();
            t2.start();
        }

    }

    class Example
    {
        private static String mStr = "";
        
        public void execute()
        {
            synchronized(Example.class) {
                for (int i = 0; i < 20; ++i)
                {
                    try
                    {
                        Thread.sleep((long) Math.random() * 1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println("Hello: " + i);
                }
            }
        }

        public static void execute2()
        {
            synchronized(mStr) {
                for (int i = 0; i < 20; ++i)
                {
                    try
                    {
                        Thread.sleep((long) Math.random() * 1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println("World: " + i);
                }
            }
        }

    }

    class Thread1 extends Thread
    {
        private Example example;

        public Thread1(Example example)
        {
            this.example = example;
        }

        @Override
        public void run()
        {
            example.execute();
        }

    }

    class Thread2 extends Thread
    {
        private Example example;

        public Thread2(Example example)
        {
            this.example = example;
        }

        @Override
        public void run()
        {
            example.execute2();
        }

    }
