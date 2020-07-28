/* Copyright 2017 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package com.my.company.mrsingh;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import com.my.company.mrsingh.R;

/** Main {@code Activity} class for the Camera app. */
public class CameraActivity extends Activity implements TextToSpeech.OnInitListener {
   TextToSpeech tts;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_camera);

    if (null == savedInstanceState) {
      getFragmentManager()
          .beginTransaction()
          .replace(R.id.container, Camera2BasicFragment.newInstance())
          .commit();
    }
    tts = new TextToSpeech(getApplicationContext(),this);

  }

  @Override
  public void onInit(int status) {
    if (status == TextToSpeech.SUCCESS) {
      speakOut("WELCOME");}
  }

  private void speakOut(String text) {


    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
      @Override
      public void onStart(String s) {

        final String keyword = s;
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(getApplicationContext(), "WELCOME" + keyword, Toast.LENGTH_SHORT).show();
          }
        });
      }


      @Override
      public void onDone(String s) {

        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(getApplicationContext(), "Done ", Toast.LENGTH_SHORT);
          }
        });
      }

      @Override
      public void onError(String s) {


        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
          }
        });
      }
    });

    Bundle params = new Bundle();
    params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");

    tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, "");
  }

  @Override
  public void onDestroy() {
    if (tts != null) {
      tts.stop();
      tts.shutdown();
    }
    super.onDestroy();
  }

}
