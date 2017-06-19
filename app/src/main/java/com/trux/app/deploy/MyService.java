package com.trux.app.deploy;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sharadsingh on 16/06/17.
 */
public class MyService extends Service{
    public static final int notify = 300000;
        private Handler mHandler = new Handler();
        private Timer mTimer = null;
        @Override
        public IBinder onBind(Intent intent) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        @Override
        public void onCreate() {
            if (mTimer != null)
                mTimer.cancel();
            else
                mTimer = new Timer();
                mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);
        }
        @Override
        public void onDestroy() {
            super.onDestroy();
            mTimer.cancel();
          //  Toast.makeText(this, "Service is Destroyed", Toast.LENGTH_SHORT).show();
        }
       class TimeDisplay extends TimerTask {
      @Override
      public void run() {
          // run on another thread
          mHandler.post(new Runnable() {
            @Override
            public void run() {
              /*  MediaPlayer mp = MediaPlayer.create(MyService.this, R.raw.champagne_bottle_cork_pop_inside);
                mp.start();
                MediaPlayer mp1 = MediaPlayer.create(MyService.this, R.raw.c);
                mp1.start();*/
               // Toast.makeText(MyService.this, "Service is running", Toast.LENGTH_SHORT).show();
                try {
                    //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                     send_mgs_toset_vts("http://180.151.15.77:8080/truxapiv2/leads/getAccountOpenCount");
                     //send_mgs_toset_vts("http://crm.truxapp.com/truxapiv2/leads/getAccountOpenCount");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
    private void send_mgs_toset_vts(String SubURL){
        String URL =SubURL;
        final RequestQueue queue = Volley.newRequestQueue(this);
        queue.getCache().remove(URL);
        queue.getCache().clear();
        /*JSONObject jsonobject_one = new JSONObject();
        try {
          //  jsonobject_one.put("current_time", currentTime);
        }catch (JSONException e) {
            e.printStackTrace();
        }*/
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, URL,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject resultJson) {
                String resultObj = resultJson.toString();
                try {
                    if (resultObj.equals("")) {
                        return;
                    }
                    JSONObject resultObject = new JSONObject(resultObj);
                    if (resultObject.getString("errorCode").equals("100")) {
                        Responce rsp =  new Gson().fromJson(resultObj,Responce.class);
                        Model [] data = rsp.getData();
                        ArrayList<Model> list = new ArrayList<>(Arrays.asList(data));
                        on_Off_ring(list.get(0).getCount());
                    }else{
                       // Toast.makeText(MyService.this,resultObject.getString("errorMesaage"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }

        });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(300000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsObjRequest);
    }

    private void on_Off_ring(int count) {
         System.out.println("count" + count );
         if(count == 0){

          }else {
                 MediaPlayer mp = MediaPlayer.create(MyService.this, R.raw.champagne_bottle_cork_pop_inside);
                mp.start();
                MediaPlayer mp1 = MediaPlayer.create(MyService.this, R.raw.c);
                mp1.start();
         }

    }


}