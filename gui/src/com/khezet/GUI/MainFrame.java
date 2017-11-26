package com.khezet.GUI;

import com.khezet.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class MainFrame extends JFrame {

    private ToolBar toolBar;
    private TextPanel textPanel;
    private TablePanel tablePanel;
    private FormPanel formPanel;
    private JFileChooser fileChooser;
    private Controller controller;
    private PrefsDialog prefsDialog;
    private Preferences prefs;
    private JSplitPane splitPane;
    private JTabbedPane tabPane;
    private MessagesPanel messagesPanel;

    MainFrame(){

        super("Izan dayen");

        toolBar    = new ToolBar();
        textPanel  = new TextPanel();
        formPanel  = new FormPanel();
        fileChooser= new JFileChooser();
        controller = new Controller();
        tablePanel = new TablePanel();
        prefsDialog= new PrefsDialog(this);
        tabPane = new JTabbedPane();
        messagesPanel = new MessagesPanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tabPane);
        splitPane.setOneTouchExpandable(true);//make 2 icon to split (show/hide) with those icon
        //preferences initial to save a preferences data
        prefs = Preferences.userRoot().node("db");

        //add a table and textFienld to tabbedPane
        this.tabPane.add(tablePanel, "Database");
        this.tabPane.add(messagesPanel, "Messages");

        //add a data to  tablePanel
        tablePanel.setData(controller.getPeople());

        // add a filter to fileChooser
        fileChooser.addChoosableFileFilter(new PersonFileFilter());

        //set a border layout to use
        this.setLayout( new BorderLayout());
        //add a component to this layout
        this.add(toolBar, BorderLayout.PAGE_START);
        this.add(splitPane, BorderLayout.CENTER);

        //add a event listener to toolBar
        this.toolBar.setToolbarListener(new ToolbarListener() {
            @Override
            public void saveEventOccured() {
                controller.connect();

                try {
                    controller.save();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Cannot to save data" + e.getMessage(), "Database Connection", JOptionPane.ERROR_MESSAGE);
                }

                System.out.println("save");
            }

            @Override
            public void refreshEventOccured() {
                controller.connect();
                try {
                    controller.load();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MainFrame.this, e.getMessage(), "Database Connection", JOptionPane.ERROR_MESSAGE);
                }
                tablePanel.refresh();
            }
        });
        //add an event to formPanel to show data inserted into a textPanel into a textPanel
        this.formPanel.setFormEventOccured(new FormListener(){
            public void formEventOccurred(FormEvent event){
                controller.addPerson(event);
                tablePanel.refresh();
            }
        });

        //add a menu bar
        this.setJMenuBar(createMenuBar());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.disconnect();
                dispose();
                System.gc();
            }
        });

        this.setMinimumSize(new Dimension(500, 350));
        this.setSize(600, 400);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JMenuBar createMenuBar(){
        //menu bar au above on top of window
        JMenuBar menuBar = new JMenuBar();
        //create a menu item of file section
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportDataItem = new JMenuItem("Export Data..");
        JMenuItem importDataItem = new JMenuItem("Import Data..");
        JMenuItem exitItem       = new JMenuItem("Exit");
        //add event listener to iport & export item to choose a file
        importDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if ( fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION ){
                    try {
                        controller.loadFromFile( fileChooser.getSelectedFile() );
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(
                                MainFrame.this,
                                "Load Data From a chosen file.",
                                "Import Data Error", JOptionPane.ERROR_MESSAGE
                        );
                        tablePanel.refresh();
                    }
                    System.out.println();
                }
            }
        });

        exportDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.saveToFile( fileChooser.getSelectedFile() );
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(
                                MainFrame.this,
                                "Choosen file error.",
                                "Export Data Error", JOptionPane.ERROR_MESSAGE
                        );
                    }
                    System.out.println(fileChooser.getSelectedFile());
                }
            }
        });

        fileMenu.add(exportDataItem);
        fileMenu.add(importDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        //add a key event (raccourci)
        fileMenu.setMnemonic(KeyEvent.VK_F);

        //add Mnemonic & Accelerato to exitItem
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));//with ctrl x exit

        //add a shortcut `ctrl + i` to import a data(handle a importDataItem with key )
        importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        exportDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));


        //add event lisener to exitItem
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int action = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure exit app?", "Exit", JOptionPane.OK_CANCEL_OPTION);
                if( action == JOptionPane.OK_OPTION ){
                    WindowListener[] windowListener = MainFrame.this.getWindowListeners();
                    for (WindowListener listener : windowListener) {
                        listener.windowClosing(new WindowEvent(MainFrame.this, 3));
                    }
                }
            }
        });

        tablePanel.setPersonTableListener(new PersonTableListener(){
            public void deletePerson(int row) {
                controller.removePerson(row);
                //we can add refresh here but for perfirmance no tablePanel.refresh();
            }
        });

        prefsDialog.setDefault("", "", 3306);
        prefsDialog.setPrefsListener(new PrefsListener(){
            @Override
            public void setPreferences(String user, String password, int port) {
                prefs.put("user", user);
                prefs.put("password", password);
                prefs.putInt("port", port);
            }
        });

        //add submenu to menu
        JMenu windowMenu = new JMenu("Window");
        JMenu showMenu = new JMenu("Show");
        JMenuItem preferences = new JMenuItem("Preferences");

        //add accelerator to preferences
        preferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        final JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
        showFormItem.setSelected(true);
        showMenu.add(showFormItem);
        windowMenu.add(showMenu);
        windowMenu.add(preferences);

        //add event listener to a show formMenuItem to show or hide a FormPanel
        showFormItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
                if (showFormItem.isSelected())
                    splitPane.setDividerLocation( (int) formPanel.getMinimumSize().getWidth());
                formPanel.setVisible( menuItem.isSelected() );
            }
        });

        //add event to preferences
        preferences.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                prefsDialog.setVisible(true);
            }
        });

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        return menuBar;
    }

    private class ActionListenerCustom implements ActionListener{
        private String text = null;
        ActionListenerCustom(String text) {
            this.text = text;
        }
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            textPanel.appendText(java.lang.String.valueOf(text));
        }
    }

}
