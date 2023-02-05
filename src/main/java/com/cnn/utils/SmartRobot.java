package com.cnn.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

public class SmartRobot {
	
    public static final String API_KEY = "2d85bc5b705b4321b8beddc5eff1d8be";
    public static final String API_URL = "http://www.tuling123.com/openapi/api";
    
    private String setParameter(String msg) {
        try {
            return API_URL + "?key=" + API_KEY + "&info=" + URLEncoder.encode(msg, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getString(String json){
        try {
            JSONObject object = new JSONObject(json);
            return object.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    pu