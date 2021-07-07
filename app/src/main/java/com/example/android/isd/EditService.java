package com.example.android.isd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class EditService extends AppCompatActivity {
    Context mm=this;
    String result="";
    String nameserv,descrip,photos,phoneee,servids,useridsa;

    EditText namesr,descrp,addres,phoness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editservice);
        Bundle k=getIntent().getExtras();
        Toolbar sdss= findViewById(R.id.toolbarsaxzsss);
        sdss.setTitle("Edit Service");
        assert k != null;
        nameserv =k.getString("nameserv");
        descrip =k.getString("descrip");
        photos =k.getString("photos");
        phoneee =k.getString("phoneee");
        servids =k.getString("servids");
        useridsa =k.getString("useridsa");
        String str = photos;
        ImageView phhoo= findViewById(R.id.srvphotosss);
        str=str.replace("/","//");
        String sdd= "http://isd2020.gq/Admin/"+str;
        Picasso.with(this).load(sdd).into(phhoo);
         namesr = findViewById(R.id.namesrvaa);
         namesr.setHint(nameserv);
         descrp = findViewById(R.id.descsrvaa);
        descrp.setHint(descrip);
         addres = findViewById(R.id.addresssrvaa);
         phoness = findViewById(R.id.phoneesrvaa);
        phoness.setHint(phoneee);

        Button saveedit= findViewById(R.id.saveeditss);
        saveedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url_search="http://isd2020.gq/Admin/android/update_service.php?service_id="+Uri.encode(servids)+"&user_id="+Uri.encode(useridsa)+"&name="+Uri.encode(namesr.getText().toString())+"&description="+Uri.encode(descrp.getText().toString())+"&phone="+Uri.encode(phoness.getText().toString())+"&address="+Uri.encode(addres.getText().toString())+"&city=5&category=30";
                ServiceAsyncTask task;
                task = new ServiceAsyncTask();
                task.execute(url_search);
                showToast(R.string.updatesucess);
                finish();
            }
        });

    }
    @SuppressLint("StaticFieldLeak")
    private class ServiceAsyncTask extends AsyncTask<String, Void, CatSubServ> {

        protected CatSubServ doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            MakeConnectionReq ss =new MakeConnectionReq();
            result = ss.fetchData(urls[0]);
            if(result.equals("\"no result\""))
            { showToast(R.string.NoResult);
                return null;}
            else if(result.isEmpty()) {
                showToast(R.string.ErrorConnectionfromWebHosting);
                return null;
            }
            else if(result.equals("\"update\":\"service updated succesfully\"")){
                showToast(R.string.updatesucess);
                Intent inss = new Intent(mm, HomePageActivity.class);
                startActivity(inss);
            }
            return CatSubServ.extractFeatureFromJson(result);
        }

        protected void onPostExecute(final CatSubServ result) {
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }
            updateUi();
        }
    }
    private void updateUi() {
//

    }

    public void showToast(final int toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                //Toast.makeText(MainActivRecial.this, toast, Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = getLayoutInflater();
                // Inflate the Layout
                View layout = inflater.inflate(R.layout.tostalayout,
                        (ViewGroup) findViewById(R.id.custom_toast_layout));
                TextView text = layout.findViewById(R.id.textToShow);
                // Set the Text to show in TextView
                text.setText(toast);
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
    }
}
