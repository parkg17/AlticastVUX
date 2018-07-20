package skku.alticastvux.activity.base;

import android.app.Activity;

import com.alticast.mmuxclient.ClientAPI;

import java.util.ArrayList;

import skku.alticastvux.voiceable.ASREventController;
import skku.alticastvux.voiceable.CommandListener;

/**
 * Created by woorim on 2018. 7. 20..
 */

public class BaseActivity extends Activity implements CommandListener {
    @Override
    protected void onResume() {
        super.onResume();
        ASREventController.getInstance().setCommandListener(this, this.getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        ASREventController.getInstance().removeCommandListener(this);
    }

    @Override
    public boolean receiveCommand(String pattern, String response, ArrayList<ClientAPI.Entity> entities) {
        return false;
    }
}
