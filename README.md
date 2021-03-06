
# Bagan voice assistant SDK for Android

[Bagan Platform](https://Bagan.app/) • [Bagan Studio](https://studio.Bagan.app/register) • [Docs](https://Bagan.app/docs) • [FAQ](https://Bagan.app/docs/usage/additional/faq) •
[Blog](https://Bagan.app/blog/) • [Twitter](https://twitter.com/Baganvoiceai)

[![GitHub release (latest by date)](https://img.shields.io/github/v/release/Bagan-ai/Bagan-sdk-android)](https://github.com/Bagan-ai/Bagan-sdk-android/releases)
[![CircleCI](https://circleci.com/gh/Bagan-ai/Bagan-sdk-android.svg?style=shield)](https://circleci.com/gh/Bagan-ai/Bagan-sdk-android)

Add a voice assistant to your application. With no or minimal changes to the existing UI.


## How to start

To bring voice control to an Android application:


1. [Sign up for Bagan ASR Console](https://cms.baganasr.com/)
2. Create Project 
 ![Alt text](assets/img1.png "Title")

    You can create two type of project (Recognition and Classification).
    For classification we need to add categories and  items. 

3. Add a reference as a Maven dependency
    Do the following:
    Open the build.gradle file at the project level.
    In repositories block, add maven { url 'https://jitpack.io' } .
    ```gradle
    allprojects {
        repositories {
            google()
            jcenter()
            maven { url 'https://jitpack.io' }  // add this line
        }
    }
    ```

    Open the build.gradle file at the module level.
    In the dependencies block, add the dependency configuration for the Bagan ASR Android SDK.

    ```gradle
        dependencies {
            implementation 'com.github.baganinnotech:internal-bagan-asr-sdk-android:1.1.4'
        }
    ```

4. Add AsrButton in your layout file.
    ```xml
        <com.bit.asrsdk.AsrButton
                android:id="@+id/asrbutton"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_margin="@dimen/fab_margin"
                app:srcCompat="@drawable/active_mic"
                tools:ignore="VectorDrawableCompat"
                android:layout_gravity="bottom|end"
                android:clickable="false" />
    ```
5. The following is the example code to use asr sdk.
    ```java
        ConfigSdk config = ConfigSdk.Builder.newInstance()
                    .setProjectKey("YOUR_PROJECT_ID_HERE")
                    .build();
        config.init(getApplicationContext());


        AsrButton asrButton = (AsrButton) findViewById(R.id.asrbutton);
        asrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AudioActivity.class);
                startActivityForResult(intent, ASRConstant.VOICE_REQUEST_CODE);
            }
        });

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
    ```
6. For classification project. Do the following:
    Create category in web console.
    Add item in that category.
    Copy category id from web console.
    ![Alt text](assets/img2.png "Title")

    Add category id in your code.
    ```java
         ConfigSdk config = ConfigSdk.Builder.newInstance()
                    .setProjectKey("YOUR_PROJECT_ID_HERE")
                    .setCategoryKey("YOUR_CATEGORY_ID_HERE")
                    .build();

    ```
    
7. Add activity declaration in your Android Manifest .
```xml
        <activity android:name="com.bit.asrsdk.AudioActivity"
            android:theme="@style/AppTheme.NoActionBar">
        </activity>
```
8. Add permissions in your Android Manifest.
```xml
 <uses-permission android:name="android.permission.INTERNET" />


```
## Example  app

You can download and build this repository for example app

or [Download apk](https://github.com/baganinnotech/bagan-asr-sdk-android/releases/download/v1.2/app-release.apk)

## Have questions?
If you have any questions or if something is missing in the documentation, please [contact us](mailto:baganinnovation@gmail.com). We love hearing from you!)

