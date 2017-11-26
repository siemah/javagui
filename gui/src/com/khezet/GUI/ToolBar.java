package com.khezet.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class ToolBar extends JToolBar implements ActionListener {

    private JButton btn1, btn2;
    private ToolbarListener toolbarListener;

    ToolBar() {
        //initial a btns
        btn1 = new JButton();
        btn2 = new JButton();
        //add an tooltip to btns like title on html Tags
        this.btn1.setToolTipText("Save a data into DB");
        this.btn2.setToolTipText("Refresh to get data from DB");
        //comment a this instruction below to make a toolbar draggable
        this.setBorder(BorderFactory.createEtchedBorder());

        this.btn1.addActionListener(this);
        this.btn2.addActionListener(this);

        //add a icon to btn
        this.btn2.setIcon(Utils.createIcon("/images/145011.gif"));
        this.btn1.setIcon(Utils.createIcon("/images/146794.gif.png"));

        //add components to layout
        this.add(btn1);
        this.add(btn2);
    }

    public void setTextPanel(ToolbarListener toolbarListener) {
        this.toolbarListener = toolbarListener;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton btn = (JButton) actionEvent.getSource();
        if (btn == btn1) toolbarListener.saveEventOccured();
        else if (btn == btn2) toolbarListener.refreshEventOccured();
    }

    public void setToolbarListener(ToolbarListener toolbarListener) {
        this.toolbarListener = toolbarListener;
    }
}
