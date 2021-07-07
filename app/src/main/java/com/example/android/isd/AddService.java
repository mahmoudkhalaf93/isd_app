package com.example.android.isd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by modye on 4/1/2018.
 */

public class AddService  extends AppCompatActivity  {
    String result="";
    String ctyid,catidssa,statt="false";
    Context co=this;
    citycat resultsss;
    Spinner catsss,cityyys;
    EditText named,descrption,phone,address;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_service);

        String REQUEST_URL ="http://isd2020.gq/Admin/android/cat_city.php";
        addsrvc_InAsyncTask task = new addsrvc_InAsyncTask();
        task.execute(REQUEST_URL);

        named= findViewById(R.id.namesrvce);
        descrption= findViewById(R.id.description);
                phone= findViewById(R.id.phone);
                        address= findViewById(R.id.address);
                        Button dss= findViewById(R.id.addsarvice);
        dss.setOnClickListener(new View.OnClickListener() {
public void onClick(View v) {


    catidssa = resultsss.catid.get(catsss.getSelectedItemPosition()) ;
    ctyid = resultsss.cityid.get(cityyys.getSelectedItemPosition()) ;

          if((!named.getText().toString().isEmpty())||(!descrption.getText().toString().isEmpty())||(!phone.getText().toString().isEmpty())||(!address.getText().toString().isEmpty()))
                       {
                            Bundle k=getIntent().getExtras();
                           assert k != null;
                           String idsaa=  k.getString("idda");
      String addsrc ="http://isd2020.gq/Admin/android/insert_service.php?name="+Uri.encode(named.getText().toString())+"&description="+Uri.encode(descrption.getText().toString())+"&phone="+Uri.encode(phone.getText().toString())+"&address="+Uri.encode(address.getText().toString())+"&category="+Uri.encode(catidssa)+"&city="+Uri.encode(ctyid)+"&Latitude=33&user_id="+Uri.encode(idsaa)+"&Longitude=1&zoom=55";

    gooo_InAsyncTask task = new gooo_InAsyncTask();
       task.execute(addsrc);
                        }
                        else{
              showToast(R.string.pleasfill);
          }
}
        });

    }

    private void updateUi(citycat datauesr)
    {
            ArrayAdapter<String> adaptercat;
        adaptercat = new ArrayAdapter<>(co, android.R.layout.simple_list_item_1, datauesr.catname);

        catsss = findViewById(R.id.category);

            catsss.setAdapter(adaptercat);

        catidssa = datauesr.catid.get(catsss.getSelectedItemPosition()) ;

            ArrayAdapter<String> adaptercity = new ArrayAdapter<>(co, android.R.layout.simple_list_item_1, datauesr.cityname);
            cityyys = findViewById(R.id.city);
            cityyys.setAdapter(adaptercity);

        ctyid = datauesr.cityid.get(cityyys.getSelectedItemPosition()) ;
    }

    private void updateUidd(Sign_up_JsonResponse datassuesr)
    {
        statt= datassuesr.state;
        if("true".equals(statt)){
            showToast(R.string.done);
            super.onBackPressed();
        }
        else{
            showToast(R.string.fail);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class addsrvc_InAsyncTask extends AsyncTask<String, Void, citycat>
    {

        protected citycat doInBackground(String... urls) {
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
            return citycat.extractFeatureFromJson(result);
        }

        protected void onPostExecute(final citycat result) {
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }
            resultsss=result;
            updateUi( result);

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class gooo_InAsyncTask extends AsyncTask<String, Void, Sign_up_JsonResponse>
    {
        /**
         * This method is invoked (or called) on a background thread, so we can perform
         * long-running operations like making a network request.
         *
         * It is NOT okay to update the UI from a background thread, so we just return an

         */
        protected Sign_up_JsonResponse doInBackground(String... urls) {
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
            return Sign_up_JsonResponse.extractFeatureFromJson(result);
        }
        /**
         * This method is invoked on the main UI thread after the background work has been
         * completed.
         *
         * It IS okay to modify the UI within this method. We take the {@link Sgin_in_JsonResponse} object
         * (which was returned from the doInBackground() method) and update the views on the screen.
         */
        protected void onPostExecute(final Sign_up_JsonResponse result) {
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }

            updateUidd( result);

        }
    }

    public void showToast(final int toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(AddService.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
