package com.khezet.GUI;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {

    private JTextArea textArea;

    TextPanel() {
        //initiale a textArea
        textArea = new JTextArea();
        //set a layout of this to  BorderLayout
        this.setLayout(new BorderLayout());
        // add a component to this JPanel with a possibilite to scroll of textarea like overflow: scroll on Css
        this.add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void appendText(String text) {
        this.textArea.append(text);
    }
}
