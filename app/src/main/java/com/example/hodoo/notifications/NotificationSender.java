package com.example.hodoo.notifications;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hodoo.controller.ValueEventCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationSender {
    private RequestQueue requestQueue;
    private String URL = "https://fcm.googleapis.com/fcm/send" ;


    public NotificationSender(Context contextIn){
        requestQueue = Volley.newRequestQueue(contextIn);
    }

    public void registerToReceiveNotification(boolean register){
        if(register){
            FirebaseMessaging.getInstance().subscribeToTopic("post_room");

        }else{
            FirebaseMessaging.getInstance().unsubscribeFromTopic("post_room");

        }
    }



    public void getUpdatedString(ValueEventCallBack callBack){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        callBack.onComplete(token);
                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, "Token registared", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void sendNotification(String destination,String title, String message, Map<String, Object> extra){
        JSONObject object = new JSONObject();
        try {
            object.put("to", destination);
            JSONObject notification = new JSONObject();
            notification.put("title",title);
            notification.put("body",message);
            notification.put("data", extra);

            object.put("notification", notification);


            JsonObjectRequest jRequest = new JsonObjectRequest(Request.Method.POST, URL, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAAF_DeGok:APA91bH9CyZO04rSRF1CR3u7qAZ80F1UV9wXMSMREbeettf8zcWeUH8O6nc1ayKi5mQ5QIN0_3E_kGYMSKfuAz_n5Ov1bbZpu865NpkEVE5eNpkvfYL2XmTbsIVhAsJ0SpegqV-1pVpn");

                    return header;
                }
            };
            requestQueue.add(jRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
