package com.example.musicapplication;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GetDataFirebaseAPITask extends AsyncTask<Void,Void,String> {

    @Override
    protected String doInBackground(Void... voids) {

        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL("https://mymusic-0000-255417.firebaseio.com/songs.json");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            return "";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        } catch (ProtocolException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            conn.disconnect();
        }

    }
}
