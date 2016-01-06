package ec.oranges.portana;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import java.util.Locale;

public class Speak {
    public TextToSpeech TTS;
    private Context context;

    Speak(Context context) {
        this.context = context;
        TTS = new TextToSpeech(this.context,
              new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR){
                            TTS.setLanguage(Locale.ITALIAN);
                        }
                    }
            }
        );
    }

    @SuppressWarnings("deprecation")
    public void saySomething(String s) {
        TTS.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }
}
