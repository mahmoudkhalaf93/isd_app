package com.example.android.isd;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


//http://localhost/last/admin/android/signup.php?username=vfdsvsd&password=dsvsvsv&email=dcacc&fullname=fcvxvsvsv
public class sign_up extends AppCompatActivity {
    public Context contexts = this;
    String result="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        VideoView videoview = (VideoView) findViewById(R.id.videoViewsu);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bkground1wat);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        Button lognn = findViewById(R.id.log_inup);



        lognn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent inss = new Intent(contexts, Sign_in.class);
                startActivity(inss);
                finish();
            }
        });
        Button login = findViewById(R.id.sign_upup);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isNetworkConnected()) {
                    TextView usernamessa = findViewById(R.id.usernamup);
                    TextView paswordssa = findViewById(R.id.pasworddup);
                    TextView paswordss2 = findViewById(R.id.pasworddup2);
                    TextView fullname = findViewById(R.id.fullname);
                    TextView email = findViewById(R.id.email);
                    String usrnm = usernamessa.getText().toString();
                    String paswor = paswordssa.getText().toString();
                    String paswor2 = paswordss2.getText().toString();
                    String emails = email.getText().toString();
                    String fullnameees = fullname.getText().toString();

                     if (usrnm.isEmpty() || paswor.isEmpty() || paswor2.isEmpty() || emails.isEmpty() || fullnameees.isEmpty())
                        Toast.makeText(contexts, R.string.somefieldisempty, Toast.LENGTH_LONG).show();
                      else if (emails.length() < 13)
                        Toast.makeText(contexts, R.string.emailmustbe14charmini, Toast.LENGTH_LONG).show();
                    else if (!((((emails.charAt(emails.length() - 1)) == 'm') && ((emails.charAt(emails.length() - 2)) == 'o') && ((emails.charAt(emails.length() - 3)) == 'c') && ((emails.charAt(emails.length() - 4)) == '.'))
                            && (((emails.charAt(emails.length() - 8)) == '@') || ((emails.charAt(emails.length() - 9)) == '@') || ((emails.charAt(emails.length() - 10)) == '@') || ((emails.charAt(emails.length() - 11)) == '@') || ((emails.charAt(emails.length() - 12)) == '@') || ((emails.charAt(emails.length() - 13)) == '@'))))
                        Toast.makeText(contexts, R.string.unvaildemail, Toast.LENGTH_LONG).show();
                     else if((paswor.length()<=6)||(paswor2.length()<=6)){
                         Toast.makeText(contexts, R.string.pasowrdlenthg, Toast.LENGTH_LONG).show();
                    }
                    else if (paswor.equals(paswor2)) {
                        String REQUEST_URL = "http://isd2020.gq/Admin/android/signup.php?username=" + Uri.encode(usrnm)+ "&password=" + Uri.encode(paswor) + "&email=" + Uri.encode(emails)+ "&fullname=" +Uri.encode(fullnameees);
                        sign_upAsyncTask task = new sign_upAsyncTask();
                        task.execute(REQUEST_URL);
                    }

                    else
                        Toast.makeText(contexts, R.string.confirmpasswordfield, Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(contexts,R.string.nonetworkconection,Toast.LENGTH_LONG).show();

            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class sign_upAsyncTask extends AsyncTask<String, Void, Sign_up_JsonResponse> {
        /**
         * This method is invoked (or called) on a background thread, so we can perform
         * long-running operations like making a network request.
         * <p>
         * It is NOT okay to update the UI from a background thread, so we just return an
         * {@link Sign_up_JsonResponse} object as the result.
         */
        protected Sign_up_JsonResponse doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            MakeConnectionReq ss = new MakeConnectionReq();
             result = ss.fetchData(urls[0]);
            if(result.isEmpty()) {
                showToast(R.string.ErrorConnectionfromWebHosting);
                return null;
            }
          else  if(result.equals("\"no result\""))
            {showToast(R.string.ErrorConnectionfromWebHosting);
            return null;
               }
            // Extract relevant fields from the JSON response and create an {@link Event} object
            return Sign_up_JsonResponse.extractFeatureFromJson(result);
        }
        /**
         * This method is invoked on the main UI thread after the background work has been
         * completed.
         * <p>
         * It IS okay to modify the UI within this method. We take the {@link Sign_up_JsonResponse} object
         * (which was returned from the doInBackground() method) and update the views on the screen.
         */
        protected void onPostExecute(final Sign_up_JsonResponse result) {
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }
            updateUi(result);

        }
    }
    private void updateUi(Sign_up_JsonResponse data) {

        if ("true".equals(data.state)) {
            Intent inss = new Intent(this, Sign_in.class);
            Toast.makeText(this, R.string.SuccessfullyRegistry, Toast.LENGTH_LONG).show();
            startActivity(inss);
            finish();
        }
        else
            Toast.makeText(this, R.string.thisaccountisexist, Toast.LENGTH_LONG).show();
    }
    public  boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            return cm.getActiveNetworkInfo() != null;
        }
        return Boolean.parseBoolean(null);
    }

    public void showToast(final int toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(sign_up.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
