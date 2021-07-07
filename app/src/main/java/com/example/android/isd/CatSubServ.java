package com.example.android.isd;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by modye on 3/11/2018.
 */

public class CatSubServ {
      static final String LOG_TAG = CatSubServ.class.getSimpleName();
    public ArrayList<String> subN;
      ArrayList<String> CatN ;
     ArrayList<String> NameService,DescriptionService, DateService,ProviderService, CityService,
            PhoneService,IdService,CategoryService, ImageService;
     ArrayList<Integer>   ImageServiceInt,ZoomService;
     ArrayList<String>   LatitudeService;
     ArrayList<String>      catsubnumbr;
    ArrayList<String>      LongitudeService;
     ArrayList<Double>      LongitudeServiceD;
      ArrayList<Float>   DRateService;
      ArrayList<Integer> srvcSiaz ;
         ArrayList<Integer> subNN ;
      ArrayList<Integer> catNN ;

    private CatSubServ(ArrayList<String> NameService, ArrayList<String> DescriptionService, ArrayList<String> DateService,
                       ArrayList<String> ProviderService, ArrayList<String> CityService
            , ArrayList<String> LatitudeService, ArrayList<String> LongitudeService, ArrayList<Integer> ZoomService,
                       ArrayList<String> PhoneService, ArrayList<Float> RateService,
                       ArrayList<String> IdService, ArrayList<String> CategoryService, ArrayList<String> ImageService, ArrayList<String> CatNc, ArrayList<String> subNc, ArrayList<Integer> srvcSiaz, ArrayList<Integer> subNN, ArrayList<Integer> catNN, ArrayList<String> subctss)
    {
        this.NameService=NameService;
        this.DescriptionService=DescriptionService;
        subN = new ArrayList<>();
        this.subN=subNc;
        this.srvcSiaz=srvcSiaz;
        this.subNN=subNN;
        this.catNN=catNN;
        this.CatN=CatNc;
        this.catsubnumbr=subctss;
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
        /*
تحويل الجسون من استرنج لانتجر ودابل
if(ImageService.length>0)
for (int i=0;i<ImageService.length;i++)
ImageServiceInt[i]=Integer.parseInt(ImageService[i]);
*/

    }


    static CatSubServ extractFeatureFromJson(String jsonrespon) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonrespon)) {
            return null;
        }

if(jsonrespon.equals("{\"update\":\"service not updated\"}")||jsonrespon.equals("{\"update\":\"service updated succesfully\"}"))
                  { return null;}
    try {
            ArrayList<String> subN = new ArrayList<>();
            ArrayList<Integer> subNN = new ArrayList<>();
            ArrayList<String> CatN = new ArrayList<>();
            ArrayList<Integer> srvcSiaz = new ArrayList<>();
            ArrayList<Integer> CatNN = new ArrayList<>();
            ArrayList<String> NameService = new ArrayList<>(),DescriptionService = new ArrayList<>(),
                    DateService= new ArrayList<>(),ProviderService= new ArrayList<>(), CityService= new ArrayList<>(),
                    PhoneService= new ArrayList<>(),IdService= new ArrayList<>(),CategoryService= new ArrayList<>(),
                    ImageService= new ArrayList<>();
            ArrayList<Integer>   ZoomService= new ArrayList<>();
            ArrayList<String>   LatitudeService= new ArrayList<>();
            ArrayList<String>    LongitudeService= new ArrayList<>();
            ArrayList<String>    subid= new ArrayList<>();
            ArrayList<Float>   DRateService= new ArrayList<>();
            JSONObject root = new JSONObject(jsonrespon);
            JSONArray mainA=root.getJSONArray("main");
            CatNN.add(mainA.length()-1);
            for (int i=0;i<mainA.length()-1;i++)
            {   JSONObject cat=mainA.getJSONObject(i);
                CatN.add(cat.getString("name"));
                    JSONArray subA = cat.getJSONArray("sub");
                subNN.add(subA.length());
                for (int j=0;j<subA.length();j++)
                {   JSONObject sub=subA.getJSONObject(j);
                    subN.add(sub.getString("sub_name"));
                    JSONArray servcA=sub.getJSONArray("item");
                    srvcSiaz.add(servcA.length());
                    for (int h=0;h<servcA.length();h++)
                    {
                        JSONObject servc=servcA.getJSONObject(h);
                        NameService.add(servc.optString("name"));
                        DescriptionService.add(servc.optString("description"));
                        DateService.add(servc.optString("date"));
                       ProviderService.add(servc.optString("provider"));
                        //"city_id"
                        //subid
                        subid.add(servc.optString("cat_id"));
                        CityService.add(servc.optString("city"));
                       LatitudeService.add(servc.optString("Latitude"));
                       LongitudeService.add(servc.optString("Longitude"));
                        ZoomService.add( Integer.valueOf(servc.optString("zoom")));
                      //  PhoneService.add(servc.getString("phone"));
                       // DRateService.add(Float.valueOf(servc.optString("rate")));
                        IdService.add(servc.optString("service_id"));
                      CategoryService.add(servc.optString("category"));
                        ImageService.add(servc.optString("img"));
                    }  }



            }

            return new  CatSubServ( NameService,  DescriptionService,  DateService,  ProviderService,  CityService,
                    LatitudeService,  LongitudeService,  ZoomService,  PhoneService,DRateService, IdService,
                    CategoryService,  ImageService,CatN,subN,srvcSiaz,subNN,CatNN,subid);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Problem parsing the  JSON results", e);
        }
        return null;
    }


}
