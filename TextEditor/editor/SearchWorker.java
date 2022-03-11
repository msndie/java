package editor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchWorker extends SwingWorker<Object, Words> {

    private final boolean regex;
    private final String toFind;
    private final String text;
    private final ArrayList<Words> words;
    private final JTextArea area;

    public SearchWorker(boolean regex, String toFind, String text, ArrayList<Words> words, JTextArea area) {
        this.regex = regex;
        this.toFind = toFind;
        this.text = text;
        this.words = words;
        this.area = area;
    }

    @Override
    protected Object doInBackground() {
        Pattern pattern;
        if (regex) {
            pattern = Pattern.compile(toFind);
        } else {
            pattern = Pattern.compile(toFind, Pattern.LITERAL);
        }
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            words.add(new Words(matcher.start(), matcher.end()));
            area.setCaretPosition(words.get(0).getEnd());
            area.select(words.get(0).getStart(), words.get(0).getEnd());
            area.grabFocus();
            while (matcher.find()) {
                words.add(new Words(matcher.start(), matcher.end()));
            }
        }
        return null;
    }
}