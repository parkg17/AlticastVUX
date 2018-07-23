package skku.alticastvux.voiceable.pattern;

import java.util.ArrayList;
import com.alticast.mmuxclient.ClientAPI;

/**
 * Created by woorim on 2018. 7. 23..
 */

public class MoveForwardPattern extends VoiceablePattern {

    @Override
    public void parseEntities(ArrayList<ClientAPI.Entity> entities) {

    }

    @Override
    public boolean checkPattern(String pattern) {
        return false;
    }

    @Override
    public String getPattern() {
        return "${time} 앞으로";
    }

    @Override
    public String[] getScenes() {
        return new String[0];
    }
}
