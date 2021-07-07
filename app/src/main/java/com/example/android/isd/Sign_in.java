package com.example.android.isd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Sign_in extends AppCompatActivity {
    public Context contextsign=this;

    String result="";
    TextView usernamess;
    TextView paswordss;
    SaveSharedPreference save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        Button lognn = findViewById(R.id.log_inup);
        lognn.setVisibility(TextView.INVISIBLE);
//        lognn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent inss = new Intent(contextsign, Sign_in.class);
//                startActivity(inss);
//                finish();
//            }
//        });

        if(SaveSharedPreference.getUserName(Sign_in.this).length() == 0)
        {
            // call Login Activity
            setContentView(R.layout.sign_in);
            VideoView videoview = (VideoView) findViewById(R.id.videoViewsi);
            Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bkground1wat);
            videoview.setVideoURI(uri);
            videoview.start();
            videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setLooping(true);
                }
            });
            Button login = findViewById(R.id.log_in);
            login.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    usernamess= findViewById(R.id.usernam);
                    paswordss= findViewById(R.id.paswordd);
                     if(isNetworkConnected()) {
                        if(usernamess.getText().toString().isEmpty()|| paswordss.getText().toString().isEmpty()){
                            Toast.makeText(contextsign,R.string.somefieldisempty,Toast.LENGTH_LONG).show();
                        }
                        else {
                             String REQUEST_URL ="http://isd2020.gq/Admin/android/login.php?username="+Uri.encode(usernamess.getText().toString())+"&password="+Uri.encode(paswordss.getText().toString());
                             Sign_InAsyncTask task = new Sign_InAsyncTask();
                            task.execute(REQUEST_URL);
                            CheckBox kepmelogn =(CheckBox) findViewById(R.id.keepmelogin);
                            if(kepmelogn.isChecked()){
                                SaveSharedPreference.setUserName(contextsign, usernamess.getText().toString());
                                SaveSharedPreference.setpassword(contextsign,  paswordss.getText().toString());}
                        }
                    }
                    else
                    {
                        Toast.makeText(contextsign,R.string.nonetworkconection,Toast.LENGTH_LONG).show();
                    }
                }
            });
            Button signup = findViewById(R.id.sign_up);
            signup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent inss=new Intent(contextsign,sign_up.class);
                    startActivity(inss);
                    finish();
                }
            });
        }
        else
           {
            // Stay at the current activity.
          String usrtnm= SaveSharedPreference.getUserName(contextsign);
           String pasword= SaveSharedPreference.getpassword(contextsign);
                     if(isNetworkConnected()) {
                        if(usrtnm.isEmpty()|| pasword.isEmpty()){
                            Toast.makeText(contextsign,R.string.somefieldisempty,Toast.LENGTH_LONG).show();
                        }
                        else {
                            String REQUEST_URL ="http://isd2020.gq/Admin/android/login.php?username="+Uri.encode(usrtnm)+"&password="+Uri.encode(pasword);
                            Sign_InAsyncTask task = new Sign_InAsyncTask();
                            task.execute(REQUEST_URL);}
                    }

                    else
                    { setContentView(R.layout.loading);
                        ImageView sdds=(ImageView) findViewById(R.id.nointrnt);
                        sdds.setImageResource(R.drawable.ic_signal_wifi_off_black_24dp);
                        Toast.makeText(contextsign,R.string.nonetworkconection,Toast.LENGTH_LONG).show();
                        Button lognsn = findViewById(R.id.log_inup);
                        lognsn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Intent inss = new Intent(contextsign, Sign_in.class);
                                startActivity(inss);
                                finish();
                            }
                        });
                    }
        }
        //end oncreate
    }



    private void updateUi(Sgin_in_JsonResponse datauesr)
    {
       if(!datauesr.id.isEmpty())
        {
            if(SaveSharedPreference.getUserName(Sign_in.this).length() == 0) {
                Toast.makeText(this, R.string.successfullylogin, Toast.LENGTH_LONG).show();
            }
          Intent inss=new Intent(this,HomePageActivity.class);
            Bundle b=new Bundle();
            b.putString("id",datauesr.id);
            SaveSharedPreference.setUserid( this,  datauesr.id);
            b.putString("username",datauesr.name);
            b.putString("emails",datauesr.email);
            b.putString("date",datauesr.Date);
            b.putString("imgse",datauesr.img);
            inss.putExtras(b);
            startActivity(inss);
finish();
        }

        else
       {Toast.makeText(this,R.string.incorrectinputs,Toast.LENGTH_LONG).show();
           SaveSharedPreference.clearUserName(this);
       }
    }
    @SuppressLint("StaticFieldLeak")
    private class Sign_InAsyncTask extends AsyncTask<String, Void, Sgin_in_JsonResponse>
    {


        protected Sgin_in_JsonResponse doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            MakeConnectionReq ss =new MakeConnectionReq();
             result = ss.fetchData(urls[0]);

            if(result.isEmpty())

            {showToast(R.string.ErrorConnectionfromWebHosting);
                return null;
                }
            // Extract relevant fields from the JSON response and create an {@link Event} object
            return Sgin_in_JsonResponse.extractFeatureFromJson(result);
        }


        protected void onPostExecute(final Sgin_in_JsonResponse result) {
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }

            updateUi(result);

        }
    }
    public  boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }
    public void showToast(final int toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(Sign_in.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

