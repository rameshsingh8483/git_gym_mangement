package com.ubxty.gymmanagement.healperdialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ubxty.gymmanagement.R;



public class HelperDialog {

    Activity activity;
    Dialog dialog;

    public HelperDialog(Activity activity){
        this.activity = activity;

    }


    public  void showLoader()
    {


        this.dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.loader);



        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0f;
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;



        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();

       // showError();
    }

    public  void  dismissLoader(){
        if(dialog!=null) {
            if (dialog.isShowing())
            dialog.dismiss();
        }

    }


    void showError(){


        try {


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms


                    if (dialog.isShowing()) {

                        Toast.makeText(activity, "something went wromg", Toast.LENGTH_LONG).show();



                    }
                }
            }, 10000);

        }catch (Exception e){

            Log.w("Healper","Dilaog Exceprion !");
        }
    }

}
