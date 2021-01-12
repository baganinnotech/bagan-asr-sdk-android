package com.example.ASRSample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bit.asrsdk.ASRConstant;
import com.bit.asrsdk.AsrButton;
import com.bit.asrsdk.AudioActivity;
import com.bit.asrsdk.ConfigSdk;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class ClassificationActivity extends AppCompatActivity {
    public String responses = "";
    private TextView info, weather,city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        info = findViewById(R.id.info);
        weather = findViewById(R.id.weather);
        city = findViewById(R.id.city);
        info.setText("");
        weather.setText("");
        city.setText("");
        try {
            ConfigSdk config = ConfigSdk.Builder.newInstance()
                    .setProjectId("xpULS1aoXfs7jDWyziTPQLzx6iMbPFFE")
                    .setCategoryId("jNTgZyo8GfGtpxfZZknksHiboCTf4GfJ")
                    .build();
            config.init(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        AsrButton asrButton = findViewById(R.id.asrbutton);
        asrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AudioActivity.class);
                startActivityForResult(intent, ASRConstant.VOICE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ASRConstant.VOICE_REQUEST_CODE) {
            String result = "";
            try {
                result = data.getStringExtra(ASRConstant.VOICE_REQUEST_DATA);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                JSONObject resultJsonObject = new JSONObject(result);
                Log.e("ASR", "Result Value : " + resultJsonObject.toString());
                if (resultJsonObject.getString("status").equalsIgnoreCase("success")) {
                    if (resultJsonObject.getString("text").equalsIgnoreCase("ရန်ကုန်ရာသီဥတုအခြေအနေ")) {
                        ShowWeather("16.9143", "96.1527", "en", "711ee92fd33574da815726480ce8399f", "minutely,hourly,daily,alerts", "metric","Yangon");
                    } else if (resultJsonObject.getString("text").equalsIgnoreCase("မန္တလေးရာသီဥတုအခြေအနေ")) {
                        ShowWeather("21.5619", "95.8987", "en", "711ee92fd33574da815726480ce8399f", "minutely,hourly,daily,alerts", "metric","Mandalay");
                    }
                    else if (resultJsonObject.getString("text").equalsIgnoreCase("နေပြည်တော်ရာသီဥတုအခြေအနေ")) {
                        ShowWeather("19.7633", "96.0785", "en", "711ee92fd33574da815726480ce8399f", "minutely,hourly,daily,alerts", "metric","NayPyiTaw");
                    }
                    else {
                        info.setText("");
                        weather.setText("");
                        city.setText("");
                    }
                } else {
                    info.setText("");
                    weather.setText("");
                    city.setText("");
                    info.setText(resultJsonObject.getString("text"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void ShowWeather(String mlat, String mlon, String mlang, String mappid, String mexclude, String munits,String mcity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        String lat = URLEncoder.encode(mlat, "UTF-8");
                        String lon = URLEncoder.encode(mlon, "UTF-8");
                        String lang = URLEncoder.encode(mlang, "UTF-8");
                        String appid = URLEncoder.encode(mappid, "UTF-8");
                        String exclude = URLEncoder.encode(mexclude, "UTF-8");
                        String units = URLEncoder.encode(munits, "UTF-8");
                        HttpClient Client = new DefaultHttpClient();
                        String URL = "http://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&lang=" + lang + "&exclude=" + exclude + "&appid=" + appid + "&units=" + units;
                        try {
                            String SetServerString = "";
                            HttpGet httpget = new HttpGet(URL);
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            SetServerString = Client.execute(httpget, responseHandler);
                            JSONObject resultJsonObject = new JSONObject(SetServerString);
                            JSONObject obj = resultJsonObject.getJSONObject("current");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        info.setText("");
                                        weather.setText("");
                                        city.setText("");
                                        info.setText(obj.getString("temp") + "˚C");
                                        JSONArray array = obj.getJSONArray("weather");
                                        JSONObject desc = (JSONObject) array.get(0);
                                        weather.setText(desc.getString("description"));
                                        city.setText(mcity);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        } catch (Exception ex) {
                            ex.printStackTrace();
                            // content.setText("Fail!");
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}
