package skku.alticastvux.voiceable.pattern;

import android.media.MediaCodec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woorim on 2018. 7. 25..
 */

public class MovePattern {

    private int seconds;

    public MovePattern(String s) {
        String p = "^(\\d+(시간|분|초)){1,3}(앞(으로)?|뒤(로)?)?$";
        System.out.println(Pattern.matches(p, s));
        Pattern pa = Pattern.compile("(\\d+(시간|분|초))");
        Matcher m = pa.matcher(s);
        seconds = 0;
        while(m.find()) {
            String time = m.group();
            if(time.endsWith("시간")) {
                seconds += Integer.parseInt(time.substring(0, time.length()-2)) * 3600;
            } else if(time.endsWith("분")) {
                seconds += Integer.parseInt(time.substring(0, time.length()-1)) * 60;
            } else if(time.endsWith("초")) {
                seconds += Integer.parseInt(time.substring(0, time.length()-1));
            }
        }
        if(s.contains("앞")) {
            seconds *= -1;
        }
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
