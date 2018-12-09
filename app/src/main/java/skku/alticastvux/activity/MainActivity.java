/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package skku.alticastvux.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.File;
import java.util.regex.Pattern;

import skku.alticastvux.R;
import skku.alticastvux.activity.base.BaseActivity;
import skku.alticastvux.model.VideoInfo;
import skku.alticastvux.util.DBUtil;
import skku.alticastvux.util.Util;
import skku.alticastvux.voiceable.ASREventController;
import skku.alticastvux.voiceable.CommandListener;
import skku.alticastvux.voiceable.pattern.VoiceablePattern;

/*
 * MainActivity class that loads {@link MainFragment}.
 */
public class MainActivity extends BaseActivity {

    private final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        final Uri contentUri = Uri.fromFile(new File("/sdcard/test.mp4"));
        scanIntent.setData(contentUri);
        sendBroadcast(scanIntent);
        */
        checkPermissions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ASREventController.getInstance().destroyASRContext();
    }

    private void init() {
        ArrayList<VideoInfo> videos = Util.getAllVideos();
        for(VideoInfo v: videos) {
            try {
                Log.e("test", "check file: " + "/sdcard/txt/" + v.getName().split(Pattern.quote("."))[0] + ".txt");
                File f = new File("/sdcard/txt/" + v.getName().split(Pattern.quote("."))[0] + ".txt");
                if (f.exists()) {
                    v.setDescription(getStringFromFile(f.getPath()));
                } else {
                    v.setDescription("설명 없음");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ASREventController.getInstance().createASRContext(getApplicationContext());
        DBUtil.getInstance().addVideos(0, videos);
        setContentView(R.layout.activity_main);
    }

    private String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        fin.close();
        return ret;
    }

    private String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }


    private void checkPermissions() {
        boolean notgranted = false;
        for (String permission : permissions) {
            Log.e("check permission", ContextCompat.checkSelfPermission(getApplicationContext(),
                    permission)+"");
            if(ContextCompat.checkSelfPermission(getApplicationContext(),
                    permission) != PackageManager.PERMISSION_GRANTED) {
                notgranted = true;
            }
        }

        if (notgranted) {
            boolean showrationale = true;
            for (String permission : permissions) {
                showrationale = showrationale && (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        permission));
            }
            if (showrationale) {
                Toast.makeText(this, "권한 부족, 설정 확인", 0).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        permissions,
                        100);
            }
        } else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        init();
                    } else {
                        Toast.makeText(this, "권한 부족, 설정 확인", 0).show();
                    }
                }
            }
        }
    }


    CommandListener listener;

    public void setListener(CommandListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean receiveCommand(VoiceablePattern pattern) {
        if (listener != null) listener.receiveCommand(pattern);
        return false;
    }
}
