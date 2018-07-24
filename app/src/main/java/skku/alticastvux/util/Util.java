package skku.alticastvux.util;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;

import org.apache.commons.collections4.map.HashedMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import skku.alticastvux.app.SKKUVuxApp;
import skku.alticastvux.model.VideoInfo;

/**
 * Created by woorim on 2018. 7. 20..
 */

public class Util {

    public static ArrayList<VideoInfo> getAllVideos() {
        ArrayList<VideoInfo> videoInfoArrayList = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.TITLE, MediaStore.Video.Media.RESOLUTION, MediaStore.Video.Media.DATE_ADDED};
        Cursor cur = SKKUVuxApp.getInstance().getContentResolver().query(uri, projection, null, null, null);

        int[] col = new int[projection.length];
        for (int i = 0; i < col.length; i++) {
            col[i] = cur.getColumnIndex(projection[i]);
        }

        while (cur.moveToNext()) {
            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setId(cur.getLong(col[0]));
            videoInfo.setPath(cur.getString(col[1]));
            videoInfo.setName(cur.getString(col[2]));
            videoInfo.setDuration(cur.getLong(col[3]));
            videoInfo.setSize(cur.getLong(col[4]));
            videoInfo.setTitle(cur.getString(col[5]));
            String res[] = cur.getString(col[6]).split("x");
            videoInfo.setWidth(Integer.parseInt(res[0]));
            videoInfo.setHeight(Integer.parseInt(res[1]));
            videoInfo.setAddedDate(cur.getString(col[7]));
            videoInfoArrayList.add(videoInfo);
        }
        return videoInfoArrayList;
    }

    public static float dpToPixels(Context context, float dp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;
    }

    static HashMap<String, byte[]> map;

    public static byte[] getThumbnailByteArray(String filePath) {
        if(map == null) {
            map = new HashMap<>();
        }
        if(map.get(filePath) == null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Util.getThumbnail(filePath).compress(Bitmap.CompressFormat.PNG, 80, stream);
            map.put(filePath,stream.toByteArray());
        }
        return map.get(filePath);
    }

    public static Bitmap getThumbnail(String filePath) {
        return ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MINI_KIND);
    }

    public static String makeFFmpegCommand(String input, String output, int start, int duration) {
        return String.format("-ss %d -t %d -i %s -acodec pcm_s16le -y %s", start, duration, input, output);
    }

    public static String getAssetAsString(Context context, String assetName) {
        String assetString = null;
        InputStream assetStream;
        try {
            assetStream = context.getAssets().open(assetName);
            if (assetStream != null) {
                java.util.Scanner s = new java.util.Scanner(assetStream).useDelimiter("\\A");
                assetString = s.hasNext() ? s.next() : "";
                assetStream.close();
            } else {
            }
        } catch (IOException e) {
        }
        return assetString;
    }
}
