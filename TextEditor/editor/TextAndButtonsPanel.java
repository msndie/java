package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TextAndButtonsPanel {

    private final JPanel panel = new JPanel();
    private final JButton load = new JButton(new ImageIcon("editor/load2-upload-icon.png"));
    private final JButton save = new JButton(new ImageIcon("editor/load2-download-icon.png"));
    private final JButton search = new JButton(new ImageIcon("editor/button-round-question-icon.png"));
    private final JButton prev = new JButton(new ImageIcon("editor/arrow-left-icon.png"));
    private final JButton next = new JButton(new ImageIcon("editor/arrow-right-icon.png"));
    private final JCheckBox checkBox = new JCheckBox("Use regex");
    private final JTextField text = new JTextField(20);

    public TextAndButtonsPanel() {
        FlowLayout layout = new FlowLayout();

        panel.setLayout(layout);
        text.setName("SearchField");
        save.setName("SaveButton");
        load.setName("OpenButton");
        search.setName("StartSearchButton");
        prev.setName("PreviousMatchButton");
        next.setName("NextMatchButton");
        checkBox.setName("UseRegExCheckbox");
        panel.add(load);
        panel.add(save);
        panel.add(text);
        panel.add(search);
        panel.add(prev);
        panel.add(next);
        panel.add(checkBox);
    }

    public void addPanelToFrame(JFrame frame) {
        frame.add(panel, BorderLayout.NORTH);
    }

    public void setCheckBox(boolean onoff) {
        checkBox.setSelected(onoff);
    }

    public void loadAddAction(ActionListener actionListener) {
        load.addActionListener(actionListener);
    }

    public void saveAddAction(ActionListener actionListener) {
        save.addActionListener(actionListener);
    }

    public void searchAddAction(ActionListener actionListener) {
        search.addActionListener(actionListener);
    }

    public void prevAddAction(ActionListener actionListener) {
        prev.addActionListener(actionListener);
    }

    public void nextAddAction(ActionListener actionListener) {
        next.addActionListener(actionListener);
    }

    public boolean getCheckBoxValue() {
        return checkBox.isSelected();
    }

    public String getText() {
        return text.getText();
    }
}