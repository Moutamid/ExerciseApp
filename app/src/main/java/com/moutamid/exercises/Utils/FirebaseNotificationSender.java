package com.moutamid.exercises.Utils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONObject;

public class FirebaseNotificationSender {

    private static final String FCM_API_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY = "AAAAFp-OyHA:APA91bGndaKxmUVhoqvOGw6N2bA079pgW-6kiRcfvsb2-9Iw6tiW4aAnIGVZI4eb22uF3qzhOthzmQocVlhHb4wkRvPiqCC2OS_DWKYehZzNf3mL6Qztn4keRM09CiSn4JsrFn6TmA0e"; // Replace with your server key

    public static void sendPushNotification(final List<String> tokens, final String title, final String message) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // URL for the Firebase Cloud Messaging API
                    URL url = new URL("https://fcm.googleapis.com/fcm/send");

                    // Create an HTTP connection to the URL
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Authorization", "key=AAAAFp-OyHA:APA91bGndaKxmUVhoqvOGw6N2bA079pgW-6kiRcfvsb2-9Iw6tiW4aAnIGVZI4eb22uF3qzhOthzmQocVlhHb4wkRvPiqCC2OS_DWKYehZzNf3mL6Qztn4keRM09CiSn4JsrFn6TmA0e"); // Replace with your actual server key
                    conn.setDoOutput(true);

                    // Create the JSON payload
                    JSONObject payload = new JSONObject();
                    JSONArray registrationIds = new JSONArray(tokens);
                    payload.put("registration_ids", registrationIds);

                    JSONObject notification = new JSONObject();
                    notification.put("title", title);
                    notification.put("body", message);
                    payload.put("notification", notification);

                    // Write the JSON payload to the output stream
                    OutputStream os = conn.getOutputStream();
                    os.write(payload.toString().getBytes("UTF-8"));
                    os.close();

                    // Get the response code
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Handle successful response
                        System.out.println("Notification sent successfully");
                    } else {
                        // Handle unsuccessful response
                        System.out.println("Failed to send notification");
                    }
                } catch (Exception e) {
                    System.out.println("Notification sent successfully"+ e.toString());

                }
            }
        });
    }
}
