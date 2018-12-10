package skku.alticastvux.voiceable.pattern;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Created by gyeol on 2018. 11. 25.
 */

public class FindMenuPattern extends VoiceablePattern {

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    private String menu;

    private static String PATTERN = "^(\\S+)?[선택]$";

    public static boolean matches(String response) {
        return Pattern.matches(PATTERN, response.replaceAll(" ",""));
    }

    @Override
    public void parse(String response) {
        response = response.replaceAll(" ","");
        menu = response.replaceAll("[선택]", "");
    }
}
