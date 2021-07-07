package com.example.android.isd;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by modye on 3/4/2018.
 */

public class Service {
    public static final String LOG_TAG = Service.class.getSimpleName();

    public ArrayList<String> NameService,DescriptionService, DateService,ProviderService, CityService,
            PhoneService,IdService,CategoryService, ImageService;
    public ArrayList<Integer>   ImageServiceInt,ZoomService;
    public ArrayList<Double>   LatitudeService, LongitudeService;
    public ArrayList<Float>   DRateService;
    public   int[] phot={R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty,R.drawable.empty};

    public Service(ArrayList<String> NameService, ArrayList<String> DescriptionService, ArrayList<String> DateService,
                      ArrayList<String> ProviderService, ArrayList<String> CityService
            , ArrayList<Double> LatitudeService, ArrayList<Double> LongitudeService,  ArrayList<Integer>  ZoomService,
                      ArrayList<String> PhoneService, ArrayList<Float> RateService,
                      ArrayList<String> IdService, ArrayList<String> CategoryService, ArrayList<String> ImageService)
    {
        this.NameService=NameService;
        this.DescriptionService=DescriptionService;
        this.DateService=DateService;
        this.ProviderService=ProviderService;
        this.CityService=CityService;
        this.LatitudeService=LatitudeService;
        this.LongitudeService=LongitudeService;
        this.ZoomService=ZoomService;
        this.PhoneService=PhoneService;
        this.DRateService= RateService ;
        this.IdService=IdService;
        this.CategoryService=CategoryService;
        this.ImageService=ImageService;

        for (int i=0;i<PhoneService.size();i++)
            DRateService.add((float) 2.5);
        // تحويل الجسون من استرنج لانتجر ودابل
//      if(ImageService.length>0)
//    for (int i=0;i<ImageService.length;i++)
//      ImageServiceInt[i]=Integer.parseInt(ImageService[i]);

    }

    public Service() {

    }


    public static Service extractFeatureFromJson(String jsonrespon) {
        // If the JSON string is empty or null, then return early.
        String jsonrespons=jsonrespon;
        if (TextUtils.isEmpty(jsonrespon)) {
            return null;
        }


        try {

             ArrayList<String> NameService = new ArrayList<>(),DescriptionService = new ArrayList<>(), DateService= new ArrayList<>(),ProviderService= new ArrayList<>(), CityService= new ArrayList<>(),
                    PhoneService= new ArrayList<>(),IdService= new ArrayList<>(),CategoryService= new ArrayList<>(), ImageService= new ArrayList<>();
             ArrayList<Integer>   ZoomService= new ArrayList<>();
             ArrayList<Double>   LatitudeService= new ArrayList<>(), LongitudeService= new ArrayList<>();
             ArrayList<Float>   DRateService= new ArrayList<>();

            JSONArray firstservice = new JSONArray(jsonrespon);
            for (int i=0;i<firstservice.length();i++)
            {//هنبدء هنا ناخد المفتاح بتاع الجسون ونجيب القيمة نحطه فى متغيرات الكلاس
                JSONObject ary=firstservice.getJSONObject(i);

                NameService.add(ary.optString("name"));

                DescriptionService.add(ary.optString("description"));

                DateService.add(ary.optString("date"));

                ProviderService.add(ary.optString("provider"));

                CityService.add(ary.optString("city"));

                LatitudeService.add( Double.valueOf(ary.optString("Latitude")));

                LongitudeService.add(Double.valueOf(ary.optString("Longitude")));

                ZoomService.add( Integer.valueOf(ary.optString("zoom")));

                PhoneService.add(ary.optString("phone"));

              //  DRateService.add(Float.valueOf(ary.optString("rate")));

                IdService.add(ary.optString("service_id"));

                CategoryService.add(ary.optString("category"));

                ImageService.add(ary.optString("img"));
            }
            return new  Service( NameService,  DescriptionService,  DateService,  ProviderService,  CityService
                    ,  LatitudeService,  LongitudeService,  ZoomService,  PhoneService,DRateService,
                    IdService,  CategoryService,  ImageService);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Problem parsing the  JSON results", e);
        }
        return null;
    }

}
