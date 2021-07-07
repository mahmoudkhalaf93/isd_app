package com.example.android.isd;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by modye on 3/2/2018.
 */
public class Sgin_in_JsonResponse {
    //[{"id":"8","name":"klalaf","email":"khalaf22@khalaf.org","Date":"0000-00-00"}]
   //بيانات بروفايل المستخدم
    String id;
    String name;
    String email;
    String Date;
    String img;
    public static final String LOG_TAG = Sgin_in_JsonResponse.class.getSimpleName();
Sgin_in_JsonResponse(String id, String name, String email, String date,String imaggg)
{
    this.id=id;
    this.name=name;
    this.email=email;
    this.Date=date;
    this.img=imaggg;
}
    /**
     * Return an {@link Sgin_in_JsonResponse} object by parsing out information
     * about the first element from the input jsonrespon string.
     */
     static Sgin_in_JsonResponse extractFeatureFromJson(String jsonrespon) {
        // If the JSON string is empty or null, then return early.
         String jkm=jsonrespon;

        if (TextUtils.isEmpty(jsonrespon)) {


            return null;
        }
        try {
                JSONObject firstFeature = new JSONObject(jsonrespon);
                // Extract out the id , name of user
                String ids = firstFeature.optString("id");
                String names = firstFeature.optString("name");
                String emails = firstFeature.optString("email");
                String Dates = firstFeature.optString("Date");
            String imaggg = firstFeature.optString("img");

                // Create a new {@link Event} object
                return new Sgin_in_JsonResponse(ids, names, emails,Dates,imaggg);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the  JSON results", e);
        }
        return null;
    }
}