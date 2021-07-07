package com.example.android.isd;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.isd.search_Recyclerview.MainActivRecial;
import com.example.android.isd.search_Recyclerview.Sub_cat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by modye on 3/10/2018.
 */

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private PendingIntent pendingIntent;
    Context c=this;
    String result="";

    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<SectionDataModel> allSampleData = new ArrayList<>();
    String useremaile;
    String userenamesd;
    String userdate;
    String imgs;
    String ids;
    ImageView imgg;
    public static final String LOG_TAG = HomePageActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        startAlarm();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Bundle k=getIntent().getExtras();
        assert k != null;
        useremaile=k.getString("emails");
        userenamesd=k.getString("username");
        userdate=k.getString("date");
        imgs=k.getString("imgse");
        ids=k.getString("id");

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        TextView   name = header.findViewById(R.id.usrname);
        TextView  email = header.findViewById(R.id.useremail);
        TextView  date = header.findViewById(R.id.userdated);


        date.setText(userdate);
        name.setText(userenamesd);
        email.setText(useremaile);

        EditText getKey= findViewById(R.id.search);
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
        //استدعاء الرسايلك فيو وجلب البيانات
        starthome();

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // cancle the Visual indication of a refresh
                        starthome();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 300);
            }
        });


    }
    @SuppressLint("ShortAlarm")
    public void startAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 5000;
        assert manager != null;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() , interval, pendingIntent);

    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
      //لو الزرار ماشتغلش بتاع الرجوع هفعل الاتنين دول
//        finish();
//        System.exit(0);

        //الكود ده يفتح النفجشن

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


//اللى تحت ادوس مرتين يخرج
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.doubleclicktoexit, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);

        }



    }

//navigation bar
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.addsrvc) {
            // Handle the camera action
            Intent inss = new Intent(this, AddService.class);
            Bundle b=new Bundle();
            b.putString("idda",ids);
            inss.putExtras(b);
            startActivity(inss);


        } else if (id == R.id.mysrvecc) {
         Intent mysrv = new Intent(c, MyServices.class);
                Bundle b = new Bundle();
                b.putString("userid", ids);
                mysrv.putExtras(b);
                startActivity(mysrv);
        }
        /*else if (id == R.id.cam) {
        } /*else if (id == R.id.nav_manage) {
        } */
          else if (id == R.id.share) {
            startActivity(newFacebookIntent(c.getPackageManager(),"https://www.facebook.com/IntegratedServiceDirector"));

        } else if (id == R.id.logout) {

            SaveSharedPreference.clearUserName(c);
            Intent inss = new Intent(this, Sign_in.class);
            Toast.makeText(this, R.string.logouts, Toast.LENGTH_LONG).show();
            startActivity(inss);

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {

                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            Log.e(LOG_TAG, "faceboooooooooooooookxxxxxxxx.", ignored);
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }


    void  starthome(){
        if(isNetworkConnected()) {
            String url_search="http://isd2020.gq/Admin/android/home_service.php";
            ServiceAsyncTask task = new ServiceAsyncTask();
            task.execute(url_search);
        }
        else
        {
            Toast.makeText(c,R.string.nonetworkconection,Toast.LENGTH_LONG).show();
        }

    }

    public void createDummyData(CatSubServ datauesr) {
        int sd=0,last=0;
        for (int i = 0; i < datauesr.subN.size(); i++) {
            SectionDataModel dm = new SectionDataModel();
            dm.setHeaderTitle(datauesr.subN.get(i));
            dm.setsubct(datauesr.catsubnumbr.get(sd));
            last=last+datauesr.srvcSiaz.get(i);
            ArrayList<SingleItemModel> singleItem = new ArrayList<>();
            for (int j = sd; j <datauesr.NameService.size(); j++) {
                if(j==last) {
                    sd=j;
                    break;
                }
                String str = datauesr.ImageService.get(j);
//                String sdd= "http://isd2018.000webhostapp.com/Admin/upload//services//"+im.charAt(19)+im.charAt(20)+"//"+im.charAt(19)+im.charAt(20)+"_main.jpg";
                str=str.replace("/","//");
                String sdd= "http://isd2020.gq/Admin/"+str;
                singleItem.add(new SingleItemModel(datauesr.NameService.get(j),sdd ,datauesr.IdService.get(j)));
            }
            dm.setAllItemsInSection(singleItem);
            allSampleData.add(dm);
        }


    }
    private void updateUi(CatSubServ datauesr) {
//
        imgg= findViewById(R.id.userphot);
        String sd= "http://isd2020.gq/Admin/"+imgs;
        Picasso.with(this).load(sd).into(imgg);
        createDummyData(datauesr);


        RecyclerView my_recycler_view = findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);
    }
    public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

        private ArrayList<SectionDataModel> dataList;
        private Context mContext;

        RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList) {
            this.dataList = dataList;
            this.mContext = context;
        }

        @Override
        public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
            return new ItemRowHolder(v);
        }

        @Override
        public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

            final String sectionName = dataList.get(i).getHeaderTitle();
            final String subid = dataList.get(i).getsubct();
            ArrayList<SingleItemModel> singleSectionItems = dataList.get(i).getAllItemsInSection();
            itemRowHolder.itemTitle.setText(sectionName);
            itemRowHolder.subidsss.setText(subid);
            SectionListDataAdapter itemListDataAdapter;
            itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);
            itemRowHolder.recycler_view_list.setHasFixedSize(true);
            itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
            itemRowHolder.itemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //xxxxxxxxxxxxxxxxx
                    Intent srvcprofie=new Intent(v.getContext(),Sub_cat.class);
                    Bundle b=new Bundle();
                    b.putString("subnumb", subid);
                    srvcprofie.putExtras(b);
                    startActivity(srvcprofie);
                }
            });
            itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                   // Toast.makeText(v.getContext(), "click event on more, "+sectionName , Toast.LENGTH_SHORT).show();



                }
            });


       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
        }

        @Override
        public int getItemCount() {
            return (null != dataList ? dataList.size() : 0);
        }

        public class ItemRowHolder extends RecyclerView.ViewHolder {

            protected TextView itemTitle;
            protected TextView subidsss;
            protected RecyclerView recycler_view_list;

            protected Button btnMore;



            ItemRowHolder(View view) {
                super(view);

                this.itemTitle = view.findViewById(R.id.itemTitle);
                this.subidsss = view.findViewById(R.id.subidsss);
                this.recycler_view_list = view.findViewById(R.id.recycler_view_list);
                this.btnMore= view.findViewById(R.id.btnMore);


            }

        }

    }
    public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

        private ArrayList<SingleItemModel> itemsList;
        private Context mContext;

        SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList) {
            this.itemsList = itemsList;
            this.mContext = context;
        }
        @Override
        public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
            return new SingleItemRowHolder(v);
        }
        @Override
        public void onBindViewHolder(SingleItemRowHolder holder, int i) {
            SingleItemModel singleItem = itemsList.get(i);
            holder.tvTitle.setText(singleItem.getName());
            holder.tvid.setText(singleItem.getid());
            Picasso.with(mContext).load(singleItem.getUrl()).resize(100,100).into(holder.itemImage);
       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
        }
        @Override
        public int getItemCount() {
            return (null != itemsList ? itemsList.size() : 0);
        }
        public class SingleItemRowHolder extends RecyclerView.ViewHolder {
            protected TextView tvTitle;
            protected TextView tvid;
            protected ImageView itemImage;
            private final Context context = itemView.getContext();
            SingleItemRowHolder(View view) {
                super(view);
                this.tvTitle = view.findViewById(R.id.tvTitle);
                this.itemImage = view.findViewById(R.id.itemImage);
                this.tvid= view.findViewById(R.id.tvid);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //v.getContext()
                        //Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
                        Intent srvcprofie=new Intent(v.getContext(),HomeService.class);
                        Bundle b=new Bundle();
                        String usr=tvid.getText().toString();
                        b.putString("id",usr);
                        srvcprofie.putExtras(b);
                        startActivity(srvcprofie);

                    }
                });


            }

            public Context getContext() {
                return context;
            }
        }

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
            return CatSubServ.extractFeatureFromJson(result);
        }

        protected void onPostExecute(final CatSubServ result) {
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
    private void performSearch() {
        Switch map = findViewById(R.id.map);
        boolean mapon = map.isChecked();
        if(mapon)
        {
            EditText getKey= findViewById(R.id.search);
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
                }
                else    Toast.makeText(c,R.string.nomapapp,Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(c,R.string.Searchisempty,Toast.LENGTH_LONG).show();
        }//end if of check box
        else//else of checkbox
        {
            EditText getKey = findViewById(R.id.search);
            if(!getKey.getText().toString().isEmpty())
            {
                Intent srchintnt = new Intent(c, MainActivRecial.class);
                Bundle b = new Bundle();
                String serchwordd = Uri.encode(getKey.getText().toString());
                b.putString("serch", serchwordd);
                srchintnt.putExtras(b);
                startActivity(srchintnt);
            }
            else
                Toast.makeText(c,R.string.Searchisempty,Toast.LENGTH_LONG).show();
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
