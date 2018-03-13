package com.example.evilj.bakingapp.utils;

import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by JjaviMS on 12/03/2018.
 *
 * @author JJaviMS
 */

public class NetworkUtils {

    private static final String JSONURL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    /**
     * Get the information from the server
     * @return The information of the bakery in JSON format
     * @throws IOException Related with the information retrieving
     */
    @Nullable
    public static String bakeryServerResponse () throws IOException {
        URL url = new URL(JSONURL);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        try {
            connection.setConnectTimeout(5000);//If we don´t get response within 5 seconds stop the connection

            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                return scanner.next();
            } else
                return null;
        }finally {
            connection.disconnect();
        }

    }
}
