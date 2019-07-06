package com.ubxty.gymmanagement.DB;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ubxty06 on 24/12/18.
 */

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // Shared sharedPreferences mode
    int PRIVATE_MODE = 0;

    // SharedsharedPreferences file name
    private static final String sharedPreferences_NAME = "ELA";

    // All Shared sharedPreferenceserences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "el";
    public static final String KEY_Account_type = "account_type";

    public static final String KEY_F_NAME= "fname";
    public static final String KEY_L_NAME = "lname";
    public static final String KEY_NATIONAL_NUMBER = "national_num";
    public static final String KEY_PHONE_NUMBER = "phone_num";

    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_COVER ="cover_pic";
    public static final String KEY_PROFILE="profile_pic";
    public static final String KEY_OTHER_NOTIFICATIONS ="other_notifications";
    public static final String KEY_ROAD_NOTIFICATIONS ="road_notifications";
    public static final String KEY_RADIUS = "radius";
    public static final String KEY_RADIUS_UNIT = "radius_unit";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_LOGIN_PHONE = "login_phone";
    public static final String KEY_LOGIN_PASS = "login_password";
    public static final String KEY_USER_ACTIVE = "user_active";
    public static final String KEY_USER_PHONE_RE = "user_phone_re";
    public static final String KEY_USER_PASSWORD_RE = "user_pass_re";
    public static final String KEY_User_Medicin_JSON = "user_medicin_json";
    public static final String KEY_User_Medicin_JSON_List = "user_medicin_json_list";
    public static final String KEY_Filter_DATA = "user_filter_date_set";
    public static final String KEY_Show_Price = "user_filter_date_set";




    Context context;

    public SessionManager(Context context) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences("spdb",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putStrin(String key,String data){

        editor.putString(key,data);
        editor.commit();
    }
    public String getString(String key){
        return  sharedPreferences.getString(key,"");

    }




    public void putLong(String key,long data){

        editor.putLong(key,data);
        editor.commit();
    }
    public long getLong(String key){


        return  sharedPreferences.getLong(key,0 );

    }




    public void putListAny(String key , List list){

        try {

            editor.putString(key,serialize((Serializable) list)) ;

        } catch (IOException e) {
//            e.printStackTrace();
        }
        editor.commit();

    }


    public List getListAny(String key) throws IOException {

        return (List) deserialize(sharedPreferences.getString(key,serialize(new ArrayList<>())));
    }



    public static Object deserialize(String str) throws IOException {
        if (str == null || str.length() == 0) return null;
        try {
            ByteArrayInputStream serialObj = new ByteArrayInputStream(decodeBytes(str));
            ObjectInputStream objStream = new ObjectInputStream(serialObj);
            return objStream.readObject();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return "";

    }
    public static byte[] decodeBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i+=2) {
            char c = str.charAt(i);
            bytes[i/2] = (byte) ((c - 'a') << 4);
            c = str.charAt(i+1);
            bytes[i/2] += (c - 'a');
        }
        return bytes;
    }

    public static String serialize(Serializable obj) throws IOException {
        if (obj == null) return "";
        try {
            ByteArrayOutputStream serialObj = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(serialObj);
            objStream.writeObject(obj);
            objStream.close();
            return encodeBytes(serialObj.toByteArray());
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return "";
    }
    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
        }

        return strBuf.toString();
    }



    public boolean getBol(String key){

        return sharedPreferences.getBoolean(""+key,false);
    }
    public void putBol(String key,boolean value){

        editor.putBoolean(key,value);
        editor.commit();
    }





    public void putString(String key,String value){

        editor.putString(key,value);
        editor.commit();

    }
    /**
     * Create login session
     * */


    public void putUserLogin(String phone,String password) {

        editor.putString(KEY_LOGIN_PHONE,phone);
        editor.putString(KEY_LOGIN_PASS,password);
        editor.commit();

    }



    public void userLat(String value){
        editor.putString("lat",value);
        editor.commit();

    }

    public String getUserlati(){

        return sharedPreferences.getString("lat","0.0");
    }


    public void userLong(String value){
        editor.putString("logti",value);
        editor.commit();

    }

    public String getUserLong(){

        return sharedPreferences.getString("logti","0.0");
    }



    public String getMedicinJson(){

        return   sharedPreferences.getString(KEY_User_Medicin_JSON,"");
    }

    public void putMedicinJson(String json){

        editor.putString(KEY_User_Medicin_JSON,json);
        editor.commit();
    }


    public String getMedicinJsonTemp(){

        return   sharedPreferences.getString(KEY_User_Medicin_JSON_List,"");
    }

    public void putMedicinJsonTemp(String json){

        editor.putString(KEY_User_Medicin_JSON_List,json);
        editor.commit();
    }


    public void putUserPhoneRE(String phone,String pass){

        editor.putString(KEY_USER_PHONE_RE,phone);
        editor.putString(KEY_USER_PASSWORD_RE,pass);
        editor.commit();
    }

    public String getUserPhoneRe(){

        return sharedPreferences.getString(KEY_USER_PHONE_RE,"");
    }



    public String getUserPasswordRe(){

        return sharedPreferences.getString(KEY_USER_PASSWORD_RE,"");
    }



    public void putUserActive(boolean b){

        editor.putBoolean(KEY_USER_ACTIVE,b);
        editor.commit();
    }

    public boolean getUserActive(){

        return sharedPreferences.getBoolean(KEY_USER_ACTIVE,false);
    }

    public  String getLoginPhone() {

        return sharedPreferences.getString(KEY_LOGIN_PHONE,"");
    }

    public  String getLoginPassword() {

        return sharedPreferences.getString(KEY_LOGIN_PASS,"");
    }


    public void putUserId(String userId){

        editor.putString(KEY_USER_ID,userId);
        editor.commit();

    }

    public String getUserId(){

        return sharedPreferences.getString(KEY_USER_ID,"");


    }


    public  void setRadius(String  radius, String radius_unit)
    {
        editor.putString(KEY_RADIUS,radius);
        editor.putString(KEY_RADIUS_UNIT,radius_unit);
        editor.commit();


    }

    public   String getKeyRadius() {
        return sharedPreferences.getString(KEY_RADIUS,"20");
    }

    public   String getKeyRadiusUnit() {
        return sharedPreferences.getString(KEY_RADIUS_UNIT,"km");
    }

    public  void setRoadNotifications(String  notification)
    {
        editor.putString(KEY_ROAD_NOTIFICATIONS,notification);
        editor.commit();

    }

    public boolean getOtherNotifications()
    {
        if(sharedPreferences.getString(KEY_OTHER_NOTIFICATIONS,"0").matches("1"))
        {
            return  true;

        }
        else
        {
            return false;
        }

    }

    public boolean getRoadNotifications()
    {
        if(sharedPreferences.getString(KEY_ROAD_NOTIFICATIONS,"0").matches("1"))
        {
            return  true;

        }
        else
        {
            return false;
        }

    }

    public void createLoginSession(String access_token, String email, String firstName, String lastName , String userId, String phoneNumber,String accountType){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in sharedPreferences
        editor.putString(KEY_ACCESS_TOKEN, access_token);

        // Storing email in sharedPreferences
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_F_NAME, firstName);
        editor.putString(KEY_L_NAME, lastName);
        editor.putString(KEY_PHONE_NUMBER, phoneNumber);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_Account_type, accountType);
        // commit changes
        editor.commit();
    }


    public String getAccountType(){

        return sharedPreferences.getString(KEY_Account_type,"0");
    }




    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
//            Intent i = new Intent(_context, LoginActivity.class);
//            // Closing all the Activities
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_ACCESS_TOKEN, sharedPreferences.getString(KEY_ACCESS_TOKEN, ""));

        // user email id
        user.put(KEY_EMAIL, sharedPreferences.getString(KEY_EMAIL, ""));

        // return user
        return user;
    }

    public String getEmail()
    {

        return sharedPreferences.getString(KEY_EMAIL,"");
    }
    public String getCover()
    {

        return sharedPreferences.getString(KEY_COVER,"");
    }
    public String getProfilepic()
    {

        return sharedPreferences.getString(KEY_PROFILE,"");

    }


    public String getSessionToken()
    {

        return sharedPreferences.getString(KEY_ACCESS_TOKEN,"");
    }
    public String getUserName()
    {

        return sharedPreferences.getString(KEY_F_NAME,"");
    }

    public String getFirstName()
    {

        return sharedPreferences.getString(KEY_F_NAME,"");
    }
    public String getLastName()
    {

        return sharedPreferences.getString(KEY_L_NAME,"");
    }

    public String getNationalNumber()
    {

        return sharedPreferences.getString(KEY_NATIONAL_NUMBER,"");
    }
    public String getPhoneNum()
    {

        return sharedPreferences.getString(KEY_PHONE_NUMBER,"");
    }
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared sharedPreferenceserences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
//        Intent i = new Intent(_context, LoginActivity.class);
//        // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // Add new Flag to start new Activity
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        // Staring Login Activity
//        _context.startActivity(i);
    }


    public void clear(){

        editor.clear().commit();
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }



    public void putInt(int value){

        editor.putInt("counter",value);
        editor.commit();
    }


    public int getInt(){

        return  sharedPreferences.getInt("counter",0);
    }
}
