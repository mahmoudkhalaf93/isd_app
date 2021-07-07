package com.example.android.isd;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by modye on 4/1/2018.
 */

public class citycat {

     ArrayList<String> catname;
    ArrayList<String> cityname;
    ArrayList<String> catid;
    ArrayList<String> cityid;
    private static final String LOG_TAG = citycat.class.getSimpleName();

    private citycat(ArrayList<String> catname, ArrayList<String> catid, ArrayList<String> cityname, ArrayList<String> cityid)
    {
      this.catname=catname;
      this.cityname=cityname;
      this.catid=catid;
      this.cityid=cityid;
    }
    /**
     * Return an {@link Sgin_in_JsonResponse} object by parsing out information
     * about the first element from the input jsonrespon string.
     */
    static citycat extractFeatureFromJson(String jsonrespon) {
        // If the JSON string is empty or null, then return early.
        ArrayList<String> catname=new ArrayList<>();
        ArrayList<String> cityname=new ArrayList<>();
        ArrayList<String> catid=new ArrayList<>();
        ArrayList<String> cityid=new ArrayList<>();
        if (TextUtils.isEmpty(jsonrespon)) {
            return null;
        }
        try {
            JSONObject firstFeature = new JSONObject(jsonrespon);

            JSONArray casat=firstFeature.optJSONArray("category");
            for (int i=0;i<casat.length();i++)
            {
                JSONObject indx=casat.getJSONObject(i);
                catname.add(indx.optString("name"));
                catid.add(indx.optString("cat_id"));
            }
            JSONArray citty=firstFeature.optJSONArray("city");
            for (int i=0;i<citty.length();i++)
            {
                JSONObject indxss=citty.getJSONObject(i);
                cityname.add(indxss.optString("cityname"));
                cityid.add(indxss.optString("city_id"));
            }
            // Create a new {@link Event} object
            return new citycat(catname,catid,cityname,cityid);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the  JSON results", e);
        }
        return null;
    }
}
