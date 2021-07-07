package com.example.android.isd;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class notficConta {
    String notid;  //"not_id":"248",
    String   nottext;    // "not_text":"تم التعليق بواسطة",
    String   notuser   ; // "user":"tarek badry",
    String     notserv;  //  "service":"cvvc"
    String notsrvid; // "service_id"
    public static final String LOG_TAG = notficConta.class.getSimpleName();
    notficConta(String notid, String nottext, String notuser, String notserv,String notsrvid)
    {
          this.notid=notid;  //"not_id":"248",
          this.nottext=nottext;    // "not_text":"تم التعليق بواسطة",
          this.notuser =notuser ; // "user":"tarek badry",
          this.notserv=notserv;  //  "service":"cvvc"
        this. notsrvid=notsrvid;
    }
    static notficConta extractFeatureFromJson(String jsonrespon) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonrespon)) {
            return null;
        }
        try {
            JSONObject firstFeature = new JSONObject(jsonrespon);
            JSONArray firstnot =firstFeature.getJSONArray("item");
            JSONObject firtelm=firstnot.getJSONObject(0);
            // Extract out the id , name of user
            String notid = firtelm.optString("not_id");
            String nottext = firtelm.optString("not_text");
            String notuser = firtelm.optString("user");
            String notserv = firtelm.optString("service");
            String notsrvid=firtelm.optString("service_id");
            // Create a new {@link Event} object
            return new notficConta(notid, nottext, notuser,notserv,notsrvid);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the  JSON results", e);
        }
        return null;
    }
}