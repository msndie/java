package editor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class Text {
    private final JTextArea area = new JTextArea();

    public Text(JFrame frame) {
        JScrollPane spane = new JScrollPane(area);
        Border border = spane.getBorder();
        Border marginBorder = new EmptyBorder(new Insets(5, 5, 5, 5));
        spane.setBorder(border == null ? marginBorder : new CompoundBorder(marginBorder, border));
        spane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        spane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        area.setName("TextArea");
        spane.setName("ScrollPane");
        frame.getContentPane().add(spane, BorderLayout.CENTER);
    }

    public JTextArea getArea() {
        return area;
    }

    public void grabFocus() {
        area.grabFocus();
    }

    public void select(int start, int end) {
        area.select(start, end);
    }

    public void setCaretPosition(int end) {
        area.setCaretPosition(end);
    }

    public String getText() {
        return area.getText();
    }

    public void setText(String s) {
        area.setText(s);
    }

    public void areaRead(Reader reader, Object desc) throws IOException {
        area.read(reader, desc);
    }

    public void areaWrite(Writer out) throws IOException {
        area.write(out);
    }
}