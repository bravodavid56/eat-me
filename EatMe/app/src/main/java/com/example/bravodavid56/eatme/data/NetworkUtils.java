package com.example.bravodavid56.eatme.data;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by bravodavid56 on 7/30/2017.
 */

public class NetworkUtils {
    private static final String BASE_URL = "https://api.yelp.com/v3/businesses";

    // for getting the access token from yelp api
    // the combination of these make up the api key, see the examples below on
    // how to use them
    private static final String CLIENT_ID = "Qq2dCmbZGWbTeF8TQcsQkg";
    private static final String CLIENT_SECRET = "lCgxUyFwqtt7l7kpWL4h7owQQpEp5BboKfO5Pl8WZ6dtRc92DjqfuGIshZrW0c72";


    // this access token is just for testing
    // try to use the getToken() method instead because these tokens expire after a certain amount of time
    private static final String access_token = "1jRfR08nR8qBa5FrU0_BWQXVSilOu3QRS424lW-FiHKnGU8c83cOlT94yzP8ykJ7fp585-glHX2Ek-6TUhsHqfa1Dr3VR-jDCVmyBXYlDa20Q66rTxSvq-YwXpZmWXYx";

    // this is just an example of ONE of the api calls
    // in this example, we call the /search method of the yelp api
    // this will be different if we use a different method of the api
    public static URL buildUrl(String location) {
        Uri builtUri = Uri.parse(BASE_URL+"/search").buildUpon()
                .appendQueryParameter("location", location)
                .appendQueryParameter("categories","food")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getToken() throws MalformedURLException {
        String oauthURL = "https://api.yelp.com/oauth2/token";
        Uri uri = Uri.parse(oauthURL);

        URL url = new URL(uri.toString());
        HttpURLConnection client = null;
        try {
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setDoOutput(true);
            DataOutputStream outputPost = new DataOutputStream(client.getOutputStream());
            String body = "client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET;
            outputPost.writeBytes(body);

            InputStream in = client.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            String test = "Nada";
            if (hasInput) {
                test = scanner.next();
            }

            client.disconnect();
            return test;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getResponse(URL url) throws IOException, JSONException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        // note that we use the getToken() function instead of using the static token defined
        // above
        JSONObject tokenJSON = new JSONObject(getToken());
        String token = tokenJSON.getString("access_token");

        urlConnection.setRequestProperty("Authorization", "Bearer "+token);

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }

    public static String getSpecificResponse(String businessID) {
        return "";
    }

    public static ArrayList<BusinessItem> parseSearchJson(String rawJson) throws JSONException {
        // This method parses the JSON received when calling the /search API
        // with a location attached





        JSONObject rawObject = new JSONObject(rawJson);
        JSONArray businesses = rawObject.getJSONArray("businesses");

        ArrayList<BusinessItem> bi = new ArrayList<>();

        for (int i = 0; i < businesses.length(); i++) {
            try {
                JSONObject test = (JSONObject) businesses.get(i);

                String id = test.getString("id");

                String name = test.getString("name");


                String image_url = test.getString("image_url");
                String url = test.getString("url");
                String display_phone = test.getString("display_phone");
                int review_count = test.getInt("review_count");

                JSONArray display_address = test.getJSONObject("location")
                        .getJSONArray("display_address");
                String address = (String) display_address.get(0) + ", "
                        + display_address.get(1);
                try {
                    // this is to check if an address has an apartment number/suite number
                    String address_3 = (String) display_address.get(2);
                    address = address + " " + address_3;
                } catch (JSONException e) {

                    // do nothing
                }


                double rating = test.getDouble("rating");

                // getting the category
                // this only gets one cateogry; we can add more later
                JSONArray allCategories = test.getJSONArray("categories");
                JSONObject category = (JSONObject) allCategories.get(0);
                String category_name = category.getString("alias");

                char[] price_array = test.getString("price").toCharArray();
                int length = price_array.length;
                String price = String.valueOf(length);

                bi.add(new BusinessItem(id, name, image_url,url,display_phone,review_count,
                        address,rating,category_name, price));
            } catch (JSONException e) {
                Log.e(TAG, "parseSearchJson: We crashed at " +i );
            }


        }




        return bi;




    }

}
