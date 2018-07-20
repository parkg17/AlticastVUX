package skku.alticastvux.voiceable;

import com.alticast.mmuxclient.ClientAPI;

import java.util.ArrayList;

/**
 * Created by dy.yoon on 2018-05-18.
 */

public interface CommandListener {
    boolean receiveCommand(String pattern, String response, ArrayList<ClientAPI.Entity> entities);
}
