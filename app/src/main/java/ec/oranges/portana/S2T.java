package ec.oranges.portana;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import java.util.ArrayList;
import android.util.Log;

public class S2T implements RecognitionListener {
    Portana context;
    String part;

    S2T(Portana context) {
        this.context = context;
    }

    public void onReadyForSpeech(Bundle params) {
        context.updateTextRecognized("Listening...");
    }
    public void onBeginningOfSpeech() { }
    public void onRmsChanged(float rmsdB) {
        context.changeAudioVol((int) (10 * Math.pow(10, ((double) rmsdB / 10.0))));
    }
    public void onBufferReceived(byte[] buffer) { }
    public void onEndOfSpeech() {  }
    public void onError(int error) {  }
    public void onResults(Bundle results) {
        String str = "";
        ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        if (data.size() > 0)
            str = (String) data.get(0);

        if (str.equals(""))
            context.updateTextRecognized("");
        else
            context.updateTextAndReply(str);

    }
    public void onPartialResults(Bundle partialResults) {
        ArrayList data = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (data.size() > 0) {
            part = (String) data.get(0);
            if (!part.equals(""))
                context.updateTextRecognized(part);
        }
    }
    public void onEvent(int eventType, Bundle params) {

    }
}
