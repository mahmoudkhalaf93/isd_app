package com.example.android.isd;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class comments {
    //[{"id":"8","name":"klalaf","email":"khalaf22@khalaf.org","Date":"0000-00-00"}]
    //بيانات بروفايل المستخدم
    int num;
    ArrayList<String>  commentsd;
    ArrayList<String> usernm;
    ArrayList<String> image;

    private static final String LOG_TAG = comments.class.getSimpleName();
    private comments(int nums, ArrayList<String> commentss, ArrayList<String> usernm, ArrayList<String> image)
    {
        this.num=nums;
        this.commentsd=commentss;
this.usernm=usernm;
this.image=image;
    }
    /**
     * Return an {@link comments} object by parsing out information
     * about the first element from the input jsonrespon string.
     */
    static comments extractFeatureFromJson(String jsonrespon) {
        // If the JSON string is empty or null, then return early.

        if (TextUtils.isEmpty(jsonrespon)) {

            return null;
        }

        try {
            ArrayList<String> commentssds = new ArrayList<>();
            ArrayList<String> usernm = new ArrayList<>();
            ArrayList<String> image = new ArrayList<>();
            JSONObject firstFeature = new JSONObject(jsonrespon);

            int numsaa = firstFeature.optInt("num");
            if(numsaa!=0){
                JSONArray comnts=firstFeature.getJSONArray("comments");
                for (int i=0 ;i<=comnts.length()-1;i++) {
                    JSONObject elemnt=comnts.getJSONObject(i);
                     commentssds.add(elemnt.optString("comm_text")) ;
                    usernm.add(elemnt.optString("user")) ;
                    image.add(elemnt.optString("img")) ;
                }
                Collections.reverse(commentssds);
                Collections.reverse(usernm);
                Collections.reverse(image);
                return new comments( numsaa,  commentssds,usernm,image);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the  JSON results", e);
        }
        return null;
    }
}