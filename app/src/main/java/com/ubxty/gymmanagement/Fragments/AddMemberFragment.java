package com.ubxty.gymmanagement.Fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ubxty.gymmanagement.Activities.HomeActivity;
import com.ubxty.gymmanagement.R;
import com.ubxty.gymmanagement.Util.Utility;
import com.ubxty.gymmanagement.database.entity.User;
import com.ubxty.gymmanagement.healperdialog.HelperDialog;
import com.ubxty.gymmanagement.service.serviceImpl.UserServiceImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMemberFragment extends Fragment {

    EditText name , phone ,  address , full_fees , pay_fees , pending_fees;
    Button btn_submit , btn_cancel ;
    View view ;

    TextView join_date ;
    UserServiceImpl userService ;
    public  static ImageView iv_user ,iv_add_image ,ic_camera ;
    public  static  LinearLayout ll_add_image;
    private List<User> users = new ArrayList<>();
    byte[] bitmapdata;

    HomeActivity activity;

    public static File uploadFile;
    HelperDialog helperDialog ;


    public AddMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_add_member, container, false);
        helperDialog = new HelperDialog(getActivity()) ;
        activity = (HomeActivity)getActivity();


        findViewById() ;

        clickListener() ;




        return  view ;

    }

    private void clickListener() {

        iv_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                    if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                        requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},101);

                    }else {

                        openCamera();
                    }

                }else {


                    openCamera();

                }

            }
        });


        ic_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                    if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                        requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},101);

                    }else {

                        openCamera();
                    }

                }else {


                    openCamera();

                }

            }
        });

        join_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCalendar(join_date);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(uploadFile == null){

                    Toast.makeText(getContext(),"Please Select Profile Picture !", Toast.LENGTH_LONG).show();

                }else {

                    if (checkValidate()) {


                        helperDialog.showLoader();

                        try {

                            btn_submit.setClickable(false);

                            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(uploadFile));

                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                            bitmapdata = bos.toByteArray();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        new InsertMember().execute();

                    }
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






            }
        });

    }

    private boolean checkValidate() {

        if(name.getText().toString().isEmpty() || phone.getText().toString().isEmpty() || address.getText().toString().isEmpty() || full_fees.getText().toString().isEmpty() || pay_fees.getText().toString().isEmpty()){

            Toast.makeText(getContext() ,"All Feilds are required !" ,Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    void openCamera(){


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Take photo form Gallery or take new picture using camera !")
                .setCancelable(true)
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
//                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, 102);


                        openCam();

                    }
                })
                .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String option = "AddMemberFragment";

                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        activity.startActivityForResult(photoPickerIntent, 10);
                    }
                })
        ;
        AlertDialog alert = builder.create();
        alert.show();

    }

    void openCam(){

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                uploadFile = photoFile;

                Log.i("file", "FILE->>>>>"+uploadFile);
                Log.i("file1", "PHOTO->>>>>"+uploadFile);

            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("Exception", "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                Log.i("file1", "PHOTOOO->>>>>"+uploadFile);

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(uploadFile));
                activity.startActivityForResult(cameraIntent, 102);
            }
        }

    }

    public  void showCalendar(final TextView join_date){
        int syear , smonth , sday;

        String date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());

        String clicked_date = join_date.getText().toString();
        String str_year , str_month,str_day;

//        if(clicked_date.isEmpty()) {
//
//            str_year = clicked_date.substring(0, 4);
//            str_month = clicked_date.substring(5, 7);
//            str_day = clicked_date.substring(8, 10);
//
//            syear = Integer.parseInt(str_year);
//            smonth = Integer.parseInt(str_month);
//            sday = Integer.parseInt(str_day);
//
//        }else{


        str_year = date.substring(0, 4);
        str_month = date.substring(5, 7);
        str_day = date.substring(8, 10);

        syear = Integer.parseInt(str_year);
        smonth = Integer.parseInt(str_month);
        sday = Integer.parseInt(str_day);

//        }



        DatePickerDialog dpd = new DatePickerDialog(activity , new DatePickerDialog.OnDateSetListener() {

            @Override

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                String strmonth ;
                String strday;


                int mon = monthOfYear + 1 ;
                if (mon < 10){
                    strmonth = "0"+mon ;

                }else{

                    strmonth = ""+mon ;

                } if (dayOfMonth < 10){

                    strday = "0"+dayOfMonth ;

                }else{

                    strday = "" + dayOfMonth ;

                } join_date.setText(year+"/"+strmonth+"/"+strday) ;

//                list.get(i).setCalendar(join_date.getText().toString()) ;





            } },syear,smonth,sday); dpd.show();
    }





    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();


        uploadFile = image;

        Log.w("IMAGE","PATH >> "+uploadFile.getAbsolutePath());

        return image;
    }



    private void findViewById() {

        name = view.findViewById(R.id.name ) ;
        phone = view.findViewById(R.id.phone) ;
        address = view.findViewById(R.id.address) ;
        join_date = view.findViewById(R.id.join_date) ;
        full_fees = view.findViewById(R.id.full_fees) ;
        pay_fees = view.findViewById(R.id.pay_fees) ;
        pending_fees = view.findViewById(R.id.pending_fees);
        btn_submit = view.findViewById(R.id.btn_submit) ;
        btn_cancel = view.findViewById(R.id.btn_cancel) ;
        ic_camera = view.findViewById(R.id.ic_camera);
        iv_add_image = view.findViewById(R.id.iv_add_image);
        iv_user = view.findViewById(R.id.iv_user);
        ll_add_image= view.findViewById(R.id.ll_add_image);

        userService = new UserServiceImpl(getActivity());


    }


    public class InsertMember extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                // creating object manually this can be done using dependency injection
                Log.i("TGGG", "user from db...all users - " + userService.getAll().size());


                String payFeesFlag = "0" ;
                String payFeesFullDate = "0" ;

                if (pay_fees.getText().toString().trim().equalsIgnoreCase(full_fees.getText().toString().trim())){

                    payFeesFlag = "1" ;
                    payFeesFullDate  = Utility.CurrentDate() ;


                }


                User user = new User();
                user.setUid(new Random().nextInt());
                user.setName(name.getText().toString());
                user.setAddress(address.getText().toString());
                user.setPhone(phone.getText().toString());
                user.setJoindate(join_date.getText().toString());
                user.setTotalfees(full_fees.getText().toString());
                user.setPayfees(pay_fees.getText().toString());
                user.setPendingfees(pending_fees.getText().toString());
                user.setProfile_image(bitmapdata);
                user.setJoin_month(Utility.currentMonthYear());
                user.setPayFees_status(payFeesFlag) ;
                user.setPayfull_fees_date(payFeesFullDate) ;

                userService.insertAll(user);
                users = userService.getAll();


                Log.i("TAGGG", "user from db...all users - " + userService.getAll().size());

                Thread.sleep(2000);
            } catch (InterruptedException e) {

                e.fillInStackTrace();
                return false;
            }

//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(name)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(phone);
//                }
//            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            //     mAuthTask = null;
            //   showProgress(false);

            if (success) {

                Toast.makeText(getContext(), "User created...", Toast.LENGTH_LONG).show();
                //menRecView.notifyDataSetChanged();
            //    setAdapter();

                helperDialog.dismissLoader() ;

                ((HomeActivity)getActivity()).loadFragment(new MemberFragment() , false , "MemberFragment");


//                HomeActivity homeActivity = new HomeActivity() ;
//                homeActivity.loadFragment(new MemberFragment() , true , "MemberFragment") ;


                // usersTextView.setText("Users \n\n " + users);
            } else {
                // mPasswordView.setError(getString(R.string.error_incorrect_password));
                //mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            //mAuthTask = null;
            //showProgress(false);


        }

    }

        private static final String[] DUMMY_CREDENTIALS = new String[]{
                "foo@example.com:hello", "bar@example.com:world"
        };
//
//    public void startActivityForResult2(Intent intent, int requestCode
//    ) {
//        super.startActivityForResult(intent, requestCode);
//
//        Log.w("startActivityForResult2",">>>>>>>>>"+requestCode);
//
//    }

}
