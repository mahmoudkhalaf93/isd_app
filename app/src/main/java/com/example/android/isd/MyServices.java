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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyServices extends AppCompatActivity {
    public Context contextsign=this;
    String result="";
    String idssa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysrvcrecycle);
        EditText getKey= findViewById(R.id.myservsearchr);
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
             idssa=k.getString("userid");
            String url_search="http://isd2020.gq/Admin/android/profile_services.php?user_id="+idssa;
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
        recycleview = findViewById(R.id.myservrv);
        // my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        //ADAPTER
        MyAdapter adapter = new MyAdapter(this, datauesr);
        recycleview.setAdapter(adapter);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myservnameTxt;
        TextView tvids;
        TextView myservphone;
        TextView myservaddress;
        ImageView mysrvmainImg;
        TextView myservprovidnames;
        RatingBar ratingBarSer;
        Button deleteserv,editserv;

        MyViewHolder(final View itemView) {
            super(itemView);

            myservnameTxt = itemView.findViewById(R.id.myservnameTxt);
            tvids = itemView.findViewById(R.id.tvids);
            myservprovidnames = itemView.findViewById(R.id.myservprovidnames);
            myservphone = itemView.findViewById(R.id.myservphone);
            myservaddress = itemView.findViewById(R.id.myservaddress);
            mysrvmainImg = itemView.findViewById(R.id.mysrvmainImg);
            ratingBarSer= itemView.findViewById(R.id.ratingBarSer);
            deleteserv=itemView.findViewById(R.id.deleteserv);
            editserv=itemView.findViewById(R.id.editserv);



            deleteserv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //v.getContext()
                }
            });
            mysrvmainImg.setOnClickListener(new View.OnClickListener() {
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
        ArrayList<String> name,myservphone,adress,provderos,idds,photoo;

        ArrayList<Float>   rating;

        MyAdapter(Context c, Service srch) {
            this.c = c;
            this.name = srch.NameService;
            this.myservphone = srch.PhoneService ;
            this.adress = srch.DescriptionService ;
            this.photoo = srch.ImageService ;
            this.rating =srch.DRateService ;
            this.provderos=srch.ProviderService;
            this.idds=srch.IdService;
        }
        //بنشاور على اللياوت الكارت فيو اللى هيتكرر فى الرسايكل
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(c).inflate(R.layout.myservcardviewlou, parent, false);
            return new MyViewHolder(v);
        }
        //رقم الفيو اللى عليه الدور انه يتاخد قيمة ويتعمله فيو كارت
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            //BIND DATA
            holder.myservnameTxt.setText(name.get(position));
            holder.myservaddress.setText(adress.get(position));
            holder.myservphone.setText(myservphone.get(position));

            String str = photoo.get(position);
            str=str.replace("/","//");
            String sdd= "http://isd2020.gq/Admin/"+str;
            Picasso.with(c).load(sdd).into(holder.mysrvmainImg);
            // holder.mysrvmainImg.setImageResource(photo[position]);
            holder.ratingBarSer.setRating(rating.get(position));
            holder.myservprovidnames.setText(provderos.get(position));
            holder.tvids.setText(idds.get(position));

            holder.editserv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent editsrv=new Intent(v.getContext(),EditService.class);
                    Bundle b=new Bundle();

                    b.putString("nameserv",name.get(position));
                    b.putString("descrip",adress.get(position));
                    b.putString("photos",photoo.get(position));
                    b.putString("phoneee",myservphone.get(position));
                    b.putString("servids", idds.get(position));
                    b.putString("useridsa", idssa);
                    editsrv.putExtras(b);
                    startActivity(editsrv);

                }
            });
            holder.deleteserv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Toast.makeText(v.getContext(), R.string.delete, Toast.LENGTH_SHORT).show();
                   String url_search="http://isd2020.gq/Admin/android/delete_service.php?user_id="+idssa+"&service_id="+idds.get(position);

                   ServiceAsyncTask task = new ServiceAsyncTask();
                  task.execute(url_search);

                        name.remove(position);
                        myservphone.remove(position);
                        adress.remove(position);
                        provderos.remove(position);
                        idds.remove(position);
                        photoo.remove(position);
                        rating.remove(position);

                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
//            holder.deleteserv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "delete", Toast.LENGTH_SHORT).show();
//                    String url_search="http://isd2020.gq/Admin/android/delete_service.php?user_id="+idssa+"&service_id="+idds;
//                    ServiceAsyncTask task = new ServiceAsyncTask();
//                    task.execute(url_search);
//                    myservcardviewlou.setVisibility(View.INVISIBLE);
//                }
//            });
// }
//هنا بنشوف هنعمل كم كارت

        @Override
        public int getItemCount() {
            return name.size();
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class ServiceAsyncTask extends AsyncTask<String, Void, Service> {

        protected Service doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            MakeConnectionReq ss =new MakeConnectionReq();
            result = ss.fetchData(urls[0]);
            if(result.equals("\"service_id\":\"\",\"value\":[],\"0\":\"no result\""))
            { showToast(R.string.NoResult);
                return null;}
            else if(result.isEmpty()) {
                showToast(R.string.ErrorConnectionfromWebHosting);
                return null;
            }
            // Extract relevant fields from the JSON response and create an {@link Event} object

            return Service.extractFeatureFromJson(result);
        }


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
                //Toast.makeText(MyService.this, toast, Toast.LENGTH_SHORT).show();
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
            EditText getKey= findViewById(R.id.myservsearchr);
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
        {EditText getKey = findViewById(R.id.myservsearchr);
            if(!getKey.getText().toString().isEmpty())
            {
                Intent srchintnt = new Intent(contextsign, MyServices.class);
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