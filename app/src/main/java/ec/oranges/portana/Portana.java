package ec.oranges.portana;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.webkit.*;

public class Portana extends ActionBarActivity {
    private ImageButton imageButton;
    private ProgressBar progressBar;
    private EditText input;
    private WebView webview;
    private SpeechRec speechRec;
    private Speak speak;
    private Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portana);

        bondElements();
        addListeners();
        loadStartupPage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_portana, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return (id == R.id.action_settings) || super.onOptionsItemSelected(item);
    }

    private void bondElements() {
        imageButton = (ImageButton) findViewById(R.id.imageStartSpeech);
        webview = (WebView) findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);

        /*
        webview.setInitialScale(1);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        */

        input = (EditText) findViewById(R.id.editText);

        speechRec = new SpeechRec(this);
        speak = new Speak(this);
    }

    private void addListeners() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                loadStartupPage();
                speechRec.startListening();
            }
        });
    }

    public void updateTextRecognized(String text) {
        input.setText(text);
    }

    public void updateTextAndReply(String text) {
        this.updateTextRecognized(text);
        conn = new Connection(this);
        conn.execute(text);
    }

    public void loadWeb(String html) {
        webview.loadData(html, "text/html", "UTF-8");
    }

    public void loadUrl(String url) {
        webview.loadUrl(url);
    }

    public void callJS(String jscode) {
        loadUrl("javascript:" + jscode);
    }

    public void loadStartupPage() {
        loadUrl("file:///android_asset/loading.html");
    }

    public void speak(String text) {
        speak.saySomething(text);
    }

    public void changeAudioVol(int val) {
        callJS("set("+val+")");
    }
}
