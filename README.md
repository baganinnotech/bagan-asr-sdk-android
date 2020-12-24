# Bagan Automatic Speech Recognition SDK for Android

Add a myanmar language speech recognition system to your application. With no or minimal changes to the existing UI.

## How to start

To bring voice control to an Android application:

1. [Sign up for Bagan ASR Console](https://cms.baganasr.com/)
2. Create Project 
 ![Alt text](assets/img1.png "Title")

    You can create two type of project (Recognition and Classification).

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
            implementation 'com.github.baganinnotech:internal-bagan-asr-sdk-android:1.0.2'
        }
    ```



4. Use the Bagan ASR Android SDK to embed to your application.
    


## Example  app

You can download and build this repository for example app

or [Download apk](https://cms.baganasr.com/)

## Have questions?
If you have any questions or if something is missing in the documentation, please [contact us](mailto:baganinnotech@gmail.com). We love hearing from you!)
