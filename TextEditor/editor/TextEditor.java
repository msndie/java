package editor;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class TextEditor extends JFrame {

    private String fullText = null;
    private int index = 0;
    private final TextAndButtonsPanel tabp;
    private final Menus menus;
    private final Text text;
    private final JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    private String reg = null;
    private final ArrayList<Words> words = new ArrayList<>();

    public TextEditor() {
        super("Text Editor");
        jfc.setName("FileChooser");
        add(jfc);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        tabp = new TextAndButtonsPanel();
        tabp.addPanelToFrame(this);
        tabp.saveAddAction(e -> saveAction());
        tabp.loadAddAction(e -> loadAction());
        tabp.searchAddAction(e -> startThread());
        tabp.nextAddAction(e -> nextAction());
        tabp.prevAddAction(e -> prevAction());
        menus = new Menus();
        initMenu();
        text = new Text(this);
        setVisible(true);
    }

    private void nextAction() {
        if (tabp.getText().isEmpty() || words.size() == 0) {
            return;
        }
        if (index + 1 < words.size()) {
            index++;
        } else {
            index = 0;
        }
        text.setCaretPosition(words.get(index).getEnd());
        text.select(words.get(index).getStart(), words.get(index).getEnd());
        text.grabFocus();
    }

    private void prevAction() {
        if (tabp.getText().isEmpty() || words.size() == 0) {
            return;
        }
        if (index > 0) {
            index--;
        } else {
            index = words.size() - 1;
        }
        text.setCaretPosition(words.get(index).getEnd());
        text.select(words.get(index).getStart(), words.get(index).getEnd());
        text.grabFocus();
    }

    private void loadAction() {
        int val = jfc.showOpenDialog(null);

        if (val == JFileChooser.APPROVE_OPTION) {
            try {
                text.setText(null);
                FileReader fileReader = new FileReader(jfc.getSelectedFile().getAbsoluteFile());
                text.areaRead(fileReader, null);
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null, "Cannot read file: " + exception.getMessage());
                text.setText("");
            }
        }
    }

    private void saveAction() {
        int val = jfc.showSaveDialog(null);

        if (val == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                text.areaWrite(writer);
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null, "An exception occurred " + exception.getMessage());
            }
        }
    }

    private void initMenu() {
        menus.setJMenuBar(this);
        menus.exitAddAction(e -> {
            dispose();
            System.exit(0);
        });
        menus.loadAddAction(e -> loadAction());
        menus.saveAddAction(e -> saveAction());
        menus.startAddAction(e -> startThread());
        menus.nextAddAction(e -> nextAction());
        menus.prevAddAction(e -> prevAction());
        menus.regexAddAction(e -> tabp.setCheckBox(!tabp.getCheckBoxValue()));
    }

    private void startThread() {
        words.clear();
        index = 0;
        reg = tabp.getText();
        SearchWorker worker = new SearchWorker(tabp.getCheckBoxValue(), reg, text.getText(), words, text.getArea());
        worker.execute();
    }
}