package com.example.android.isd.search_Recyclerview;
import com.example.android.isd.HomeService;
import com.example.android.isd.MakeConnectionReq;
import com.example.android.isd.Service;

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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.isd.R;
import com.example.android.isd.Sgin_in_JsonResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivRecial extends AppCompatActivity {
    public Context contextsign=this;
    String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycl);
        EditText getKey= findViewById(R.id.searchr);
        getKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        if(isNetworkConnected()) {
            Bundle k=getIntent().getExtras();
            assert k != null;
            String serchwordss=k.getString("serch");
            String url_search="http://isd2020.gq/Admin/android/search.php?title="+serchwordss;
            ServiceAsyncTask task = new ServiceAsyncTask();
            task.execute(url_search);
        }
        else
        {
            Toast.makeText(contextsign,R.string.nonetworkconection,Toast.LENGTH_LONG).show();
        }
    }
    private void updateUi(Service datauesr) {

        RecyclerView recycleview;
        recycleview = findViewById(R.id.rv);
        // my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                recycleview.setLayoutManager(new LinearLayoutManager(this));
        //ADAPTER
        MyAdapter adapter = new MyAdapter(this, datauesr);

        recycleview.setAdapter(adapter);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt;
        TextView tvids;
        TextView phone;
        TextView address;
        ImageView mainImg;
        TextView providnames;
        RatingBar ratingBarSer;

        MyViewHolder(View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.nameTxt);
            tvids = itemView.findViewById(R.id.tvids);
            providnames = itemView.findViewById(R.id.providnames);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);
            mainImg = itemView.findViewById(R.id.mainImg);
            ratingBarSer= itemView.findViewById(R.id.ratingBarSer);
            mainImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //v.getContext()
                    //Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
                    Intent srvcprofie=new Intent(v.getContext(),HomeService.class);
                    Bundle b=new Bundle();
                    String usr=tvids.getText().toString();
                    b.putString("id",usr);
                    srvcprofie.putExtras(b);
                    startActivity(srvcprofie);
                }
            });
        }

    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        Context c;
        ArrayList<String> name,phone,adress,provderos,idds,photoo;

        ArrayList<Float>   rating;

        MyAdapter(Context c, Service srch) {
            this.c = c;
            this.name = srch.NameService;
            this.phone = srch.PhoneService ;
            this.adress = srch.DescriptionService ;
            this.photoo = srch.ImageService ;
            this.rating =srch.DRateService ;
            this.provderos=srch.ProviderService;
            this.idds=srch.IdService;
        }
        //بنشاور على اللياوت الكارت فيو اللى هيتكرر فى الرسايكل
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(c).inflate(R.layout.cardviewlou, parent, false);
            return new MyViewHolder(v);
        }
        //رقم الفيو اللى عليه الدور انه يتاخد قيمة ويتعمله فيو كارت
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            //BIND DATA
            holder.nameTxt.setText(name.get(position));
            holder.address.setText(adress.get(position));
            holder.phone.setText(phone.get(position));
            String str = photoo.get(position);
            str=str.replace("/","//");
            String sdd= "http://isd2020.gq/Admin/"+str;

            Picasso.with(c).load(sdd).into(holder.mainImg);

           // holder.mainImg.setImageResource(photo[position]);
            holder.ratingBarSer.setRating(rating.get(position));
            holder.providnames.setText(provderos.get(position));
            holder.tvids.setText(idds.get(position));
        }
        //هنا بنشوف هنعمل كم كارت
        @Override
        public int getItemCount() {
            return name.size();
        }
    }

    @SuppressLint("StaticFieldLeak")
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
              else if(result.isEmpty()) {
                  showToast(R.string.ErrorConnectionfromWebHosting);
                  return null;
              }
            // Extract relevant fields from the JSON response and create an {@link Event} object

            return Service.extractFeatureFromJson(result);
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


    public  boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
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

//void  goHOme(){
//    Intent inss=new Intent(this,HomePageActivity.class);
//    startActivity(inss);
//}
    private void performSearch() {

        Switch map = findViewById(R.id.mapr);
        boolean mapon = map.isChecked();
        if(mapon)
        {
            EditText getKey= findViewById(R.id.searchr);
            if(!getKey.getText().toString().isEmpty()) {
                String serchword = getKey.getText().toString();

                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(serchword));//هيروح ويعمل علامة على المكان ده

                // + Uri.encode("29.9645023,30.9257868(kokkk)"));
                //29.9645023,30.9257868
// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                    finish();
                }
                else    Toast.makeText(contextsign,R.string.nomapapp,Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(contextsign,R.string.Searchisempty,Toast.LENGTH_LONG).show();
        }//end if of check box
        else//else of checkbox
        {EditText getKey = findViewById(R.id.searchr);
            if(!getKey.getText().toString().isEmpty())
            {
                Intent srchintnt;
                srchintnt = new Intent(contextsign, MainActivRecial.class);
                Bundle b = new Bundle();
                String serchwordd = Uri.encode(getKey.getText().toString());
                b.putString("serch", serchwordd);
                srchintnt.putExtras(b);
                startActivity(srchintnt);
                finish();
            }
            else
                Toast.makeText(contextsign,R.string.Searchisempty,Toast.LENGTH_LONG).show();
        }

    }
}