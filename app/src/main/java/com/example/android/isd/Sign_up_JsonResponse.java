package com.example.android.isd;
import com.example.android.isd.sign_up;
import android.content.Context;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by modye on 3/2/2018.
 */

public class Sign_up_JsonResponse {

    String state;
    public static final String LOG_TAG = Sign_up_JsonResponse.class.getSimpleName();
Sign_up_JsonResponse(String statee){this.state=statee;}

    static Sign_up_JsonResponse extractFeatureFromJson(String jsonrespon) {
        // If the JSON string is empty or null, then return early.
        String jkm=jsonrespon;
        if (TextUtils.isEmpty(jsonrespon)) {

            return null;
        }

        try {
            // JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray featureArray = new JSONArray(jsonrespon);

            // If there are results in the features array

                // Extract out the first feature (which is an earthquake)
                JSONObject firstFeature = featureArray.getJSONObject(0);
                // JSONObject properties = firstFeature.getJSONObject("properties");

                // Extract out the title, number of people, and perceived strength values
                String stat = firstFeature.getString("correct");

                // Create a new {@link Event} object
                return new Sign_up_JsonResponse(stat);


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }

}
