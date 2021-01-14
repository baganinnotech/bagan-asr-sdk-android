package com.example.ASRSample;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bit.asrsdk.ASRConstant;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnClass, btnReg;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = findViewById(R.id.info);
        info.setText("");
        btnClass = findViewById(R.id.btnClass);
        btnReg = findViewById(R.id.btnReg);

        btnClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClassificationActivity.class);
                startActivity(intent);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecognitionActivity.class);
                startActivity(intent);
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
                info.setText(resultJsonObject.toString() + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}