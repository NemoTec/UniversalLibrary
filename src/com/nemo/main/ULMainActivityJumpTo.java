package com.nemo.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

import com.nemo.ul.R;


public class ULMainActivityJumpTo extends Activity {
    private static final String TAG = ULMainActivityJumpTo.class.getSimpleName();

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
		
		
		builderA.append("\n--- ---\n");
        mTVResult.setText(builderA.toString());
	}
	
	//B------------------------------------------------------------------
	private void onButtonBClick() {
		final StringBuilder builderB = new StringBuilder();
		builderB.append("Button B result: \n").append("--- ---\n\n");
		
		
		builderB.append("\n--- ---\n");
        mTVResult.setText(builderB.toString());
	}
	
	//C------------------------------------------------------------------
	private void onButtonCClick() {
		final StringBuilder builderC = new StringBuilder();
		builderC.append("Button C result: \n").append("--- ---\n\n");
		
		
		builderC.append("\n--- ---\n");
        mTVResult.setText(builderC.toString());
	}

	
	private void testString() {
		String s1= "ab" + "cd";  
		String s2= "abc" + "d"; 
		
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
				Toast.makeText(ULMainActivityJumpTo.this, "Button A!!!", Toast.LENGTH_LONG).show();
				//
	
				onButtonAClick();
            }
        });
		
		mButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "mButtonB onClick()");
				Toast.makeText(ULMainActivityJumpTo.this, "Button B!!!", Toast.LENGTH_LONG).show();
				//
	
        		onButtonBClick();		
            }
        });
		
		mButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "mButtonC onClick()");
				Toast.makeText(ULMainActivityJumpTo.this, "Button C!!!", Toast.LENGTH_LONG).show();
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

			actionBar.setTitle(R.string.main_jumpto_name);
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

}
