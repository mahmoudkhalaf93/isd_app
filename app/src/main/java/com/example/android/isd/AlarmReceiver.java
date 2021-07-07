package com.example.android.isd;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
public class AlarmReceiver extends BroadcastReceiver {
    String result="";
    String lastnot="";
    Context css;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, R.string.notficationtest, Toast.LENGTH_LONG).show();
        //http://isd2020.gq/Admin/android/notification.php?user_id=1
        //SaveSharedPreference.getUserid(Context )
        //SaveSharedPreference.setlastnot(context,lastnot);
css=context;
        lastnot=SaveSharedPreference.getlastnot(context);
        String url_srvc="http://isd2020.gq/Admin/android/notification.php?user_id="+SaveSharedPreference.getUserid(context);
        ServiceAsyncTask task = new ServiceAsyncTask();
        task.execute(url_srvc);

    }
    void showNotification(String content, String servid) {
        NotificationManager mNotificationManager =
                (NotificationManager) css.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(channel);
        }
        //Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
      //  Uri uri=Uri.parse("android.resource://"+css.getPackageName()+R.raw.a035);
        Uri uri=Uri.parse("android.resource://"+css.getPackageName()+"/raw/a035");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(css.getApplicationContext(), "default")
                .setSmallIcon(R.drawable.ward) // notification icon
                .setContentTitle("Comments") // title for notification
                .setSound(uri)
                .setContentText(content)// message for notification
                // set alarm sound for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(css.getApplicationContext(), HomeService.class);
        Bundle b=new Bundle();
        b.putString("id",servid);
        intent.putExtras(b);
        PendingIntent pi = PendingIntent.getActivity(css.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        assert mNotificationManager != null;
        mNotificationManager.notify(0, mBuilder.build());

    }

    private void updateUinot(notficConta datauesr) {
//!datauesr.notid.equals(lastnot)
if(!datauesr.notid.equals(lastnot)){
    showNotification(datauesr.notuser+" comment on "+datauesr.notserv +" service " ,datauesr.notsrvid);
    SaveSharedPreference.setlastnot(css,datauesr.notid);

        }

    }

    @SuppressLint("StaticFieldLeak")
    private class ServiceAsyncTask extends AsyncTask<String, Void, notficConta> {


        /**
         * This method is invoked (or called) on a background thread, so we can perform
         * long-running operations like making a network request.
         *
         * It is NOT okay to update the UI from a background thread, so we just return an
         * {@link Service} object as the result.
         */

        protected notficConta doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            MakeConnectionReq ss =new MakeConnectionReq();
            result = ss.fetchData(urls[0]);
            if(result.equals("{\"num\":0,\"item\":[]}"))
            {
                return null;}

            // Extract relevant fields from the JSON response and create an {@link Event} object

            return notficConta.extractFeatureFromJson(result);
        }

        /**
         * This method is invoked on the main UI thread after the background work has been
         * completed.
         *
         * It IS okay to modify the UI within this method. We take the {@link Sgin_in_JsonResponse} object
         * (which was returned from the doInBackground() method) and update the views on the screen.
         */
        protected void onPostExecute(final notficConta result) {
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }

            updateUinot(result);

        }
    }


}
