

package com.example.android.isd;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

//save_username_&_pasword
class SaveSharedPreference
{
    static final String PREF_USER_NAME ="USER_sNAME" , PREF_PASSWORD = "PREF_PASSWss",USERID = "user_id",LASTNO="last_noot"
            ,LASTSERVVV="lasservc";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }





    //SaveSharedPreference.setlastnot(Context,lastnot )
    public static void setlastserv(Context ctx, String lastservss)
    {
        String lastnotss =lastservss;
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString( LASTSERVVV , lastservss);
        editor.commit();
    }
    //SaveSharedPreference.getlastnot(Context )
    public static  String getlastserv(Context ctx)
    {
        return getSharedPreferences(ctx).getString( LASTSERVVV , "");
    }






    //SaveSharedPreference.setlastnot(Context,lastnot )
    public static void setlastnot(Context ctx, String lastnot)
    {
        String lastnotss =lastnot;
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString( LASTNO , lastnot);
        editor.commit();
    }
    //SaveSharedPreference.getlastnot(Context )
    public static  String getlastnot(Context ctx)
    {
        return getSharedPreferences(ctx).getString( LASTNO , "");
    }



    public static void setUserid(Context ctx, String userid)
    {
        String useridd =userid;
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString( USERID , userid);
        editor.commit();
    }
//SaveSharedPreference.getUserid(Context )
    public static  String getUserid(Context ctx)
    {
        return getSharedPreferences(ctx).getString( USERID , "");
    }





    public static void setUserName(Context ctx, String userName)
    {
        String userNamse =userName;
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString( PREF_USER_NAME , userName);
        editor.commit();
    }

    public static  String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString( PREF_USER_NAME , "");
    }


    public static  void setpassword(Context ctx, String paswrid)
    {
        String pasowd =paswrid;
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PASSWORD, paswrid);
        editor.commit();
    }
    public  static String getpassword(Context ctx)
    {
        String pasowowoowow=PREF_PASSWORD;
        return getSharedPreferences(ctx).getString(PREF_PASSWORD, "");
    }

    public static  void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
/*
String encryptedString = encryption("Input Normal String");
String decryptedString = decryption("Input Encrypted String");
*/

}
