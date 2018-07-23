package skku.alticastvux.voiceable.pattern;

import java.util.ArrayList;
import com.alticast.mmuxclient.ClientAPI;

/**
 * Created by woorim on 2018. 7. 23..
 */

public class FindSongArtistPattern extends VoiceablePattern {
    private static String PATTERN = "[방금|지금] [나온|나오는|들리는|이] (노래|[배경]음악|BGM|브금) (검색[해줘]|찾아[줘]|알려줘|뭐냐|뭐야|뭐여)";

    @Override
    public void parseEntities(ArrayList<ClientAPI.Entity> entities) {

    }

    @Override
    public boolean checkPattern(String pattern) {
        return false;
    }

    @Override
    public String getPattern() {
        return PATTERN;
    }

    @Override
    public String[] getScenes() {
        return new String[0];
    }
}
