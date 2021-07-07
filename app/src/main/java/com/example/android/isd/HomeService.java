package com.example.android.isd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.android.isd.search_Recyclerview.MainActivRecial;
//import com.example.android.isd.search_Recyclerview.MyAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by modye on 3/13/2018.
 */

public class HomeService extends AppCompatActivity {
    public Context cs=this;
    String servesid;
    String result="";
    EditText getcom;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_service);
        Bundle k=getIntent().getExtras();
          servesid=k.getString("id");
        if(isNetworkConnected()) {
            String url_srvc="http://isd2020.gq/Admin/android/service_details.php?service_id="+servesid;
         ServiceAsyncTask task = new ServiceAsyncTask();
            task.execute(url_srvc);
            String urlcomment="http://isd2020.gq/Admin/android/fetch_comment.php?service_id="+servesid;
            comentAsyncTask comn = new comentAsyncTask();
            comn.execute(urlcomment);
        }
        else
        {
            Toast.makeText(cs,R.string.nonetworkconection,Toast.LENGTH_LONG).show();
        }

          getcom= findViewById(R.id.entercommment);
        getcom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    sendcomnt();

                    return true;
                }
                return false;
            }
        });

    }
    void sendcomnt(){
        String urlcommen="http://isd2020.gq/Admin/android/insert_comment.php?user_id="+ SaveSharedPreference.getUserid(cs)+"&service_id="+servesid+"&comm_text="+Uri.encode(getcom.getText().toString());
        ServiceAsyncTask comn = new ServiceAsyncTask();
        comn.execute(urlcommen);
        String urlcomment="http://isd2020.gq/Admin/android/fetch_comment.php?service_id="+servesid;
        comentAsyncTask comnd = new comentAsyncTask();
        comnd.execute(urlcomment);
        getcom.getText().clear();

    }
    @SuppressLint("ResourceAsColor")
    private void updateUi(Service datauesr) {

        final Service servc=datauesr;
        Toolbar sdss=(Toolbar) findViewById(R.id.toolbarsaxz);
        sdss.setTitle(servc.NameService.get(0));
        sdss.setTitleTextColor(R.color.textColorPrimary);
        sdss.setSubtitleTextColor(R.color.textColorPrimary);
        TextView des=(TextView) findViewById(R.id.descptionsa);
        des.setText( servc.DescriptionService.get(0));
        TextView namd=(TextView) findViewById(R.id.servcname);
        namd.setText(servc.NameService.get(0));
        TextView pho=(TextView) findViewById(R.id.phone);
        pho.setText(servc.PhoneService.get(0));
        TextView pro=(TextView) findViewById(R.id.providnames);
        pro.setText(servc.ProviderService.get(0));
        RatingBar rat=(RatingBar) findViewById(R.id.ratingBarSer) ;
        rat.setRating(servc.DRateService.get(0));
        ImageView img=(ImageView) findViewById(R.id.srvphoto);
       // if(!servc.ImageService.get(0).isEmpty()) {
            String str = servc.ImageService.get(0);

            str = str.replace("/", "//");
            String sd = "http://isd2020.gq/Admin/" + str;
            Picasso.with(cs).load(sd).into(img);
       // }
        Button mapd=(Button) findViewById(R.id.gompsse);
        mapd.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              Uri gmmIntentUri = Uri.parse("geo:0,0?q="+ Uri.encode(servc.LatitudeService.get(0).toString()+","+servc.LongitudeService.get(0).toString()+"("+servc.NameService.get(0).toString()+")"));//هيروح ويعمل علامة على المكان ده
              Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
              mapIntent.setPackage("com.google.android.apps.maps");
              if (mapIntent.resolveActivity(getPackageManager()) != null) {
                  startActivity(mapIntent);
              }
          }
        });


    }

    private class ServiceAsyncTask extends AsyncTask<String, Void, Service> {


        /**
         * This method is invoked (or called) on a background thread, so we can perform
         * long-running operations like making a network request.
         *
         * It is NOT okay to update the UI from a background thread, so we just return an
         * {@link Service} object as the result.
         */

        protected Service doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            MakeConnectionReq ss =new MakeConnectionReq();
            result = ss.fetchData(urls[0]);
            if(result.equals("\"no result\""))
            { showToast(R.string.NoResult);
                return null;}

            // Extract relevant fields from the JSON response and create an {@link Event} object
            Service jsonrespoBeforparsing = Service.extractFeatureFromJson(result);

            return jsonrespoBeforparsing;
        }

        /**
         * This method is invoked on the main UI thread after the background work has been
         * completed.
         *
         * It IS okay to modify the UI within this method. We take the {@link Sgin_in_JsonResponse} object
         * (which was returned from the doInBackground() method) and update the views on the screen.
         */
        protected void onPostExecute(final Service result) {
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }

            updateUi(result);

        }
    }

    private void updateUicom(comments datauesr) {

        RecyclerView recycleview;
        recycleview = findViewById(R.id.commentrecv);
        // my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        //ADAPTER
        MyAdaptercomnt adapter = new MyAdaptercomnt(this, datauesr);
        recycleview.setAdapter(adapter);
        recycleview.scrollToPosition(datauesr.num-1);


    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView donecomment;
        TextView nameuser;
        ImageView phottuser;

        MyViewHolder(View itemView) {
            super(itemView);

            donecomment = itemView.findViewById(R.id.donecomment);
            nameuser=itemView.findViewById(R.id.nameuser);
            phottuser=itemView.findViewById(R.id.phottuser);
        }

    }

    public class MyAdaptercomnt extends RecyclerView.Adapter<MyViewHolder> {

        Context c;
        ArrayList<String> commentsaaz;
        ArrayList<String> usernm;
        ArrayList<String> photousers;

        MyAdaptercomnt(Context c, comments srch) {
            this.c = c;
            this.commentsaaz = srch.commentsd;
            this.usernm=srch.usernm;
            this.photousers=srch.image;
        }
        //بنشاور على اللياوت الكارت فيو اللى هيتكرر فى الرسايكل
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(c).inflate(R.layout.comment, parent, false);
            return new MyViewHolder(v);
        }
        //رقم الفيو اللى عليه الدور انه يتاخد قيمة ويتعمله فيو كارت
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            //BIND DATA
            holder.donecomment.setText(commentsaaz.get(position));
            holder.nameuser.setText(usernm.get(position));
            String str = photousers.get(position);
            str=str.replace("/","//");
            String sdd= "http://isd2020.gq/Admin/"+str;

            Picasso.with(c).load(sdd).into(holder.phottuser);
        }
        //هنا بنشوف هنعمل كم كارت
        @Override
        public int getItemCount() {
            return commentsaaz.size();
        }
    }

    private class comentAsyncTask extends AsyncTask<String, Void, comments> {


        /**
         * This method is invoked (or called) on a background thread, so we can perform
         * long-running operations like making a network request.
         *
         * It is NOT okay to update the UI from a background thread, so we just return an
         * {@link Service} object as the result.
         */

        protected comments doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            MakeConnectionReq ss =new MakeConnectionReq();
            result = ss.fetchData(urls[0]);
            if(result.equals("{\"num\":0,\"comments\":[]}"))
            { showToast(R.string.comntnoo);
                return null;}
            else if(result.isEmpty()) {

                return null;
            }
            // Extract relevant fields from the JSON response and create an {@link Event} object
            comments jsonrespoBeforparsing = comments.extractFeatureFromJson(result);

            return jsonrespoBeforparsing;
        }

        /**
         * This method is invoked on the main UI thread after the background work has been
         * completed.
         *
         * It IS okay to modify the UI within this method. We take the {@link Sgin_in_JsonResponse} object
         * (which was returned from the doInBackground() method) and update the views on the screen.
         */
        protected void onPostExecute(final comments result) {
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }

            updateUicom(result);

        }
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

                TextView text = (TextView) layout.findViewById(R.id.textToShow);
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
    public  boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
