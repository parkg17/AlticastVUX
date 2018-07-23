package skku.alticastvux.voiceable;

import android.util.Patterns;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import skku.alticastvux.activity.MainActivity;
import skku.alticastvux.voiceable.pattern.FindSongArtistPattern;
import skku.alticastvux.voiceable.pattern.FindSongPattern;
import skku.alticastvux.voiceable.pattern.MoveForwardPattern;
import skku.alticastvux.voiceable.pattern.NumberTestPattern;
import skku.alticastvux.voiceable.pattern.VoiceablePattern;


/**
 * Created by dy.yoon on 2018-05-18.
 */

public class MainGrammar {
    private final static String GLOBAL_SCENE = "_global";

    private MultiValuedMap<String, String> patternsMap = new ArrayListValuedHashMap<String, String>();
    private MultiValuedMap<String, String> exampleTextMap = new ArrayListValuedHashMap<String, String>();

    public static final Class Patterns[] = {FindSongPattern.class, FindSongArtistPattern.class, MoveForwardPattern.class, NumberTestPattern.class};

    public MainGrammar() {
        for (int i = 0; i < Patterns.length; i++) {
            Class c = Patterns[i];
            try {
                VoiceablePattern pattern = (VoiceablePattern) c.getConstructor().newInstance(new Object[]{});
                registerPattern(pattern.getPattern(), new String[]{MainActivity.class.getSimpleName()});
            } catch (Exception e) {

            }
        }
    }

    private void registerPattern(VoiceablePattern pattern) {
        registerPattern(pattern.getPattern(), pattern.getScenes());
    }

    private void registerPattern(String pattern, String[] screenNames) {
        for (String screenName : screenNames) {
            patternsMap.put(screenName, pattern);
        }
    }

    public String[] getPatterns(String screenName) {
        ArrayList result = new ArrayList(patternsMap.get(GLOBAL_SCENE));
        result.addAll(patternsMap.get(screenName));
        String[] resultStr = new String[result.size()];
        result.toArray(resultStr);
        return resultStr;
    }
}
