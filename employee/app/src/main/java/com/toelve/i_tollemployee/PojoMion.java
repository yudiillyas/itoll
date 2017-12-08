package com.toelve.i_tollemployee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by boyke purnomo 085883746312 on 7/12/2016.
 * copyright this script is under license of boyke purnomo please respect the author
 */
public class PojoMion {
    private static JSONObject jsonObject;
    private static JSONArray jsonArray;

    public static ArrayList<String> AmbilArray(String b, String datanya, String c) {
        ArrayList<String> Arrays =  new ArrayList<>();
        try {
            Arrays.clear();
            jsonObject = new JSONObject(b);
            jsonArray = jsonObject.getJSONArray(c);
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                String isi = jo.getString(datanya);
                Arrays.add(isi);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        return Arrays;

    }

    public static ArrayList<String> AmbilArray2(String b, String datanya, String c) {
        ArrayList<String> Arrays =  new ArrayList<>();
        try {
            Arrays.clear();
            jsonObject = new JSONObject(b);
            jsonArray = jsonObject.getJSONArray(c);
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                String isi = jo.getString(datanya);
                if(!Arrays.contains(isi)){
                    Arrays.add(isi);
                }

                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        return Arrays;

    }

}
