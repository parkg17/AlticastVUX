package skku.alticastvux.voiceable.pattern;

import com.alticast.mmuxclient.ClientAPI;

import java.util.ArrayList;

/**
 * Created by woorim on 2018. 7. 20..
 */

public abstract class VoiceablePattern {
    public abstract void parseEntities(ArrayList<ClientAPI.Entity> entities);
    public abstract boolean checkPattern(String pattern);
    public abstract String getPattern();
    public abstract String[] getScenes();
}
