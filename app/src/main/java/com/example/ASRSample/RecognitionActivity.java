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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class RecognitionActivity extends AppCompatActivity {
    private TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_recognition);
        info = findViewById(R.id.info);
        info.setText("");
        try {
            ConfigSdk config = ConfigSdk.Builder.newInstance()
                    .setProjectKey("F1i2aEjDaUC8aXZlPSOUxgPboRA9CF0D")
                    .build();
            config.init(getApplicationContext());
            Log.e("coming:","coming:");
        } catch (IOException e) {
            e.printStackTrace();
        }

        AsrButton asrButton = (AsrButton) findViewById(R.id.asrbutton);
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
                info.setText(resultJsonObject.getString("text")+ "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
