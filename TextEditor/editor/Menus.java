package editor;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Menus {
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("File");
    private final JMenu searchMenu = new JMenu("Search");
    private final JMenuItem loadMenu = new JMenuItem("Open");
    private final JMenuItem saveMenu = new JMenuItem("Save");
    private final JMenuItem exitMenu = new JMenuItem("Exit");
    private final JMenuItem startMenu = new JMenuItem("Start search");
    private final JMenuItem prevMenu = new JMenuItem("Previous search");
    private final JMenuItem nextMenu = new JMenuItem("Next match");
    private final JMenuItem regexMenu = new JMenuItem("Use regular expressions");

    public Menus() {
        fileMenu.setName("MenuFile");
        loadMenu.setName("MenuOpen");
        saveMenu.setName("MenuSave");
        exitMenu.setName("MenuExit");
        startMenu.setName("MenuStartSearch");
        prevMenu.setName("MenuPreviousMatch");
        nextMenu.setName("MenuNextMatch");
        regexMenu.setName("MenuUseRegExp");
        searchMenu.setName("MenuSearch");
        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        fileMenu.add(loadMenu);
        fileMenu.add(saveMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenu);
        searchMenu.add(startMenu);
        searchMenu.add(prevMenu);
        searchMenu.add(nextMenu);
        searchMenu.add(regexMenu);
    }

    public void startAddAction(ActionListener e) {
        startMenu.addActionListener(e);
    }

    public void prevAddAction(ActionListener e) {
        prevMenu.addActionListener(e);
    }

    public void nextAddAction(ActionListener e) {
        nextMenu.addActionListener(e);
    }

    public void regexAddAction(ActionListener e) {
        regexMenu.addActionListener(e);
    }

    public void loadAddAction(ActionListener e) {
        loadMenu.addActionListener(e);
    }

    public void saveAddAction(ActionListener e) {
        saveMenu.addActionListener(e);
    }

    public void exitAddAction(ActionListener e) {
        exitMenu.addActionListener(e);
    }

    public void setJMenuBar(JFrame frame) {
        frame.setJMenuBar(menuBar);
    }
}