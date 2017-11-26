package com.khezet.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrefsDialog extends JDialog {

    private JSpinner portSpinner;
    private JButton okBtn, cancelBtn;
    private JTextField userField;
    private JPasswordField passField;
    private PrefsListener prefsListener;

    PrefsDialog(JFrame parent) {
        super(parent, "Preferences", false);

        //assign a vars
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(3306, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerNumberModel);
        userField = new JTextField(10);
        passField = new JPasswordField(10);
        passField.setEchoChar('*');

        // add a component to our JDualog into a layout

        layoutControlles();

        okBtn.addActionListener(new SpinnerActionListener());
        cancelBtn.addActionListener(new SpinnerActionListener());

        this.setLocationRelativeTo(parent);
        this.setSize(320, 220);
        this.setVisible(false);
    }

    private void layoutControlles() {

        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        Insets rightPadding = new Insets(0,0,0,5);

        //border
        Border titledBorder = BorderFactory.createTitledBorder("DataBase Preferences: ");
        Border spaceBorder  = BorderFactory.createEmptyBorder(15, 5, 15, 5);


        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titledBorder));

        controlsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //put all component int a gridBagLayout
        gc.weighty = 1;
        gc.weightx = 1;



        gc.fill = GridBagConstraints.NONE;//give a space to components (fill all(No in this case) the space between component)

        gc.gridy = 0;
        gc.gridx = 0;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;

        controlsPanel.add(new JLabel("User: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(userField, gc);
        //pass postios

        gc.gridx = 0;
        gc.gridy++;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Password: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(passField, gc);

        //port position
        gc.gridx = 0;
        gc.gridy++;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Port: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(portSpinner, gc);

        //buttons panel
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okBtn, gc);
        buttonsPanel.add(cancelBtn, gc);
        //set a size of ok btn to the same size of cancel btn
        okBtn.setPreferredSize(cancelBtn.getPreferredSize());

        //add panel to prefDialog
        this.setLayout(new BorderLayout());
        this.add(controlsPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     * this is like setActionListener to fire a event to save a user, password and port data
     * @param prefsListener
     */
    public void setPrefsListener(PrefsListener prefsListener) {
        this.prefsListener = prefsListener;
    }

    /**
     * set by default and to save a recent value inserted by userClient
     * @param user username of DB
     * @param pass password of DB
     * @param port port number of DB
     */
    public void setDefault(String user, String pass, int port){
        userField.setText(user);
        passField.setText(pass);
        portSpinner.setValue(port);
    }

    class SpinnerActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource().equals(okBtn)) {
                Integer port = (Integer) portSpinner.getValue();
                String  user = userField.getText();
                String  pass = new String( passField.getPassword() );
                prefsListener.setPreferences(user, pass, port);
            }
            PrefsDialog.this.setVisible(false);
        }
    }


}
