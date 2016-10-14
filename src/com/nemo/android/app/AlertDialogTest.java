package com.nemo.android.app;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AlertDialogTest extends Activity {
    private static final String TAG = "AlertDialogTest";
    
    private static final int DIALOG_TEST = 10001;
    
    private Dialog mDialog = null;
    
    private Context mContext;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
  
    }

    private void initView() {

    }
    
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Dialog onCreateDialog(int id, Bundle args) {
        AlertDialog.Builder builder = null;

		/*
        if (DIALOG_TEST == id) {
            builder = new Builder(this);
            //builder.setCancelable(false);
            builder.setMessage(R.string.vd_dlg_found_risk_to_full_scan);
            builder.setPositiveButton(R.string.vd_dlg_btn_full_scan,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.setNegativeButton(R.string.vd_dlg_btn_cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                public void onCancel(DialogInterface dialog) {

                }
            });
            mDialog = builder.create();
            //mDialog.setCanceledOnTouchOutside(false);
            return mDialog;
        }
*/
        return null;
    }
    
    
    
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case DIALOG_TEST:
                
                break;
                
            default:
                break;
            }
        }
    };
    
    

}
