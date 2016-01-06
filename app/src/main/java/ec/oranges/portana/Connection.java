package ec.oranges.portana;

import android.os.AsyncTask;

import java.net.*;
import javax.net.ssl.*;

import java.io.*;
//import java.net.URLEncoder.*;

public class Connection extends AsyncTask<String, Void, String> {
    Portana context;
    Parser parser;

    Connection(Portana context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... phrase) {
        String res;
        try {
            URL url = new URL("https://www.bing.com/search?q=" + URLEncoder.encode(phrase[0], "UTF-8"));
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.addRequestProperty("X-BM-Client","BingWP/2.1/assistant");

            conn.setRequestProperty("X-BM-Theme", "000000;BBBBBB");

            InputStream in = new BufferedInputStream(conn.getInputStream());
            res = readStream(in);

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return res;
    }

    protected void onPostExecute(String result) {
        if (result.length() > 0) {
            parser = new Parser(result);
            context.loadWeb(result);
            context.speak(parser.getWordsToSay());
        } else {
            context.loadWeb("<h2>Error.</h2>");
        }
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}