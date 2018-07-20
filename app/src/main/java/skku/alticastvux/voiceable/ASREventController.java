package skku.alticastvux.voiceable;

import android.content.Context;
import android.os.Bundle;

import com.alticast.mmuxclient.ClientAPI;

import java.util.ArrayList;

import skku.alticastvux.activity.MainActivity;


/**
 * Created by dy.yoon on 2018-05-18.
 */

public class ASREventController implements  ClientAPI.Callback<ClientAPI.ASRResult> {
    private ClientAPI.ASRContext asrContext = null;
    private CommandListener listener = null;

    private Context context = null;
    private MainGrammar grammar = null;

    public static ASREventController getInstance() {
        return SingletonHolder.instance;
    }
    private static class SingletonHolder {
        private static final ASREventController instance = new ASREventController();
    }

    @Override
    public void callback(ClientAPI.ASRResult asrResult) {
        String response = asrResult.getSpokenResponse();
        String pattern = asrResult.getMatchedPattern();
        ArrayList<ClientAPI.Entity> entities = asrResult.getMatchedEntities();

        if (listener != null)
            listener.receiveCommand(pattern, response, entities);
    }

    public void setCommandListener(CommandListener listener, String sceneName) {
        if(grammar!=null)
            resetGrammar(grammar, sceneName);
        this.listener = listener;
    }

    public void removeCommandListener(CommandListener listener) {
        if (this.listener != null && this.listener.equals(listener))
            this.listener = null;
    }

    public void createASRContext(final Context context) {
        this.context = context;
        asrContext = ClientAPI.createASRContext(context, "Main", new ClientAPI.Callback<Bundle>() {
            @Override
            public void callback(Bundle bundle) {
                grammar = new MainGrammar();
                resetGrammar(grammar, MainActivity.class.getSimpleName());
                openEventPipe();
            }
        });
    }

    public void destroyASRContext() {
        if (asrContext != null) {
            asrContext.enableScreenContext();
            ClientAPI.destroyASRContext(asrContext);
        }
        if (context != null) {
            ClientAPI.unbind(context);
        }
    }

    public void resetGrammar(final MainGrammar grammar, final String sceneName) {
        asrContext.clearASRPatterns(
                new ClientAPI.Callback<ClientAPI.ASRResult>() {
                    @Override
                    public void callback(ClientAPI.ASRResult asrResult) {
                        for( String pattern : grammar.getPatterns(sceneName) ) {
                            asrContext.addASRPattern(pattern, ASREventController.this);
                        }
                    }
                }
        );

        for( String pattern : grammar.getPatterns(sceneName) ) {
            asrContext.addASRPattern(pattern, ASREventController.this);
        }
    }

    public void clearASRPatterns() {
        if(asrContext==null) return;
        asrContext.clearASRPatterns(new ClientAPI.Callback<ClientAPI.ASRResult>() {
            @Override
            public void callback(ClientAPI.ASRResult asrResult) {
                // Callback Body
            }
        });
    }

    public void showVoicePrompt() {
        if(asrContext == null) return;
        asrContext.showVoicePrompt();

    }

    public void hideVoicePrompt() {
        if(asrContext == null) return;
        asrContext.hideVoicePrompt();
    }

    public void openEventPipe(){
        if(asrContext == null) return;
        asrContext.openEventPipe();
        registerAPIEvents();
    }

    public void registerAPIEvents(){
        ClientAPI.Callback<ClientAPI.ClientEvent> callback = new ClientAPI.Callback<ClientAPI.ClientEvent>() {
            @Override
            public void callback(ClientAPI.ClientEvent event) {
                event.performAction();
            }
        };
        asrContext.registerHandledEvents("scrollDown", callback);
        asrContext.registerHandledEvents("scrollLeft", callback);
        asrContext.registerHandledEvents("scrollRight", callback);
        asrContext.registerHandledEvents("scrollUp", callback);
    }
}