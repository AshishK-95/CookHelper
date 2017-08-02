package ca.uottawa.cookhelper;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.*;
import okhttp3.Request;

import static android.R.attr.host;

/**
 * Created by Alek on 2016-12-05.
 */

public class RequestHandler {

    private static final String HOST = "http://35.160.116.176/"; //url for host of server
    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    /**
     * deletes the JSON object specified by the URL
     * @param endpointURL specific location to delete from
     */
    public static void delete(String endpointURL) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Request request = new Request.Builder().url(HOST + endpointURL).delete().build();

        try {
            client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * posts a JSON object to the specified URL
     * @param endpointURL specific location to delete from
     * @param jsonBody information to add to server
     */
    public static void post(String endpointURL, String jsonBody) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder().url(HOST + endpointURL).post(body).build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets the JSON object from the specified URL
     * @param endpointURL specific location to delete from
     * @return JSON object
     */
    public static String get(String endpointURL) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        okhttp3.Request request = new Request.Builder().url(HOST + endpointURL).build();
        try {
            return client.newCall(request).execute().body().string();
        } catch (IOException e) {
            return "error";
        }
    }





}
