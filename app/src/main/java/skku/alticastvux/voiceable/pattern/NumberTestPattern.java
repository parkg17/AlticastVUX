package skku.alticastvux.voiceable.pattern;

import com.alticast.mmuxclient.ClientAPI;

import java.util.ArrayList;

/**
 * Created by woorim on 2018. 7. 23..
 */

public class NumberTestPattern extends VoiceablePattern {
    @Override
    public void parseEntities(ArrayList<ClientAPI.Entity> entities) {

    }

    @Override
    public boolean checkPattern(String pattern) {
        return false;
    }

    @Override
    public String getPattern() {
        return "${number}번";
    }

    @Override
    public String[] getScenes() {
        return new String[0];
    }
}
