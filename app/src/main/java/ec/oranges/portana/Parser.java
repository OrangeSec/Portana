package ec.oranges.portana;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.*;
import java.net.URLDecoder.*;

public class Parser {
    private String html;
    private String wordsToSay;

    Parser(String html) {
        this.html = html;
        findWordsToSay();
    }

    private void findWordsToSay() {
        try {
            String datassml = "";
            Pattern p = Pattern.compile("<div data-ssml=\"(\\S+)\">");
            Matcher m = p.matcher(html);
            while (m.find()) {
                datassml = m.group(1);
            }

            wordsToSay = URLDecoder.decode(datassml, "UTF-8").replaceAll("\\<.*?\\>", "");
        } catch (Exception e) {
            wordsToSay = "";
        }
    }

    public String getWordsToSay() {
        return wordsToSay;
    }
}
