package com.khezet.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FormPanel extends JPanel {

    private JLabel nameLabel, occupationLabel, ageLabel, empLabel, taxLabel;
    private JTextField nameText, occupationText, taxField;
    private JButton submitBtn;
    private FormListener formListener;
    private JList<AgeCategory> ageList;
    private JComboBox empCombo;
    private JCheckBox citizen;
    private JRadioButton maleRadio, femaleRadio;
    private ButtonGroup genderGroup;

    FormPanel(){

        nameLabel = new JLabel("Name: ");
        occupationLabel = new JLabel("Occupation: ");
        empLabel = new JLabel("employement: ");
        empLabel.setFont(new Font("Comic Sans Ms", Font.BOLD, 14));
        ageLabel = new JLabel("age: ");
        taxLabel = new JLabel("Tax: ");
        taxField = new JTextField(10);
        citizen  = new JCheckBox();
        maleRadio = new JRadioButton("male");
        femaleRadio = new JRadioButton("female");
        genderGroup = new ButtonGroup();
        //set up tax
        taxField.setEnabled(false);
        taxLabel.setEnabled(false);
        //set up a gendregroup
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        //default value
        maleRadio.setSelected(true);
        //what we want to get when select one of those button radio
        maleRadio.setActionCommand("Male");
        femaleRadio.setActionCommand("Female");

        //add listener to checkbox
        citizen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                taxField.setEnabled(citizen.isSelected());
                taxLabel.setEnabled(citizen.isSelected());
            }
        });

        nameText = new JTextField(10);
        occupationText = new JTextField(10);
        ageList = new JList<>();
        empCombo = new JComboBox();
        //set up a ageList
        ageList.setBorder(BorderFactory.createEtchedBorder());
        ageList.setPreferredSize(new Dimension(120, 68));

        DefaultListModel defaultListModel = new DefaultListModel();
        defaultListModel.addElement( new AgeCategory(0, "Under 18") );
        defaultListModel.addElement( new AgeCategory(1, "25 to 40") );
        defaultListModel.addElement( new AgeCategory(2, "over 40") );

        ageList.setModel(defaultListModel);
        ageList.setSelectedIndex(1);//select by default an item

        //set up empCombo
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        defaultComboBoxModel.addElement("employed");
        defaultComboBoxModel.addElement("self-emplyed");
        defaultComboBoxModel.addElement("unemployed");
        this.empCombo.setModel(defaultComboBoxModel);

        submitBtn = new JButton("OK");

        // create a new Dimension to set a FormPanel dimension
        Dimension dim = getPreferredSize();// retrieve a default dimension
        dim.width = 250;// set a width
        this.setPreferredSize(dim);
        this.setMinimumSize(dim);
        // add a inner and outside border to FormPanel
        Border outsideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border innerBorder   = BorderFactory.createTitledBorder("Add Person");
        this.setBorder(BorderFactory.createCompoundBorder(outsideBorder, innerBorder));

        //add Mnemonic to nameLabel to select a nameTaxt
        nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        nameLabel.setLabelFor(nameText);

        //add a keyEvent Mnemonic
        submitBtn.setMnemonic(KeyEvent.VK_O);
        //listen to click event of submitBtn
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameText.getText();
                String occupation = occupationText.getText();
                String genderCommand = genderGroup.getSelection().getActionCommand();

                AgeCategory age = (AgeCategory) ageList.getSelectedValue();
                FormEvent ev = new FormEvent(this,
                        name,
                        occupation,
                        age.getId(),
                        (String)empCombo.getSelectedItem(),
                        (String)taxField.getText(),
                        citizen.isSelected(),
                        genderCommand
                );

                if( !ev.equals(null) ) formListener.formEventOccurred(ev);
                //reset a form filed
                FormPanel.this.femaleRadio.setSelected(false);
                FormPanel.this.maleRadio.setSelected(true);
                FormPanel.this.ageList.setSelectedIndex(2);
                FormPanel.this.nameText.setText("");
                FormPanel.this.occupationText.setText("");
                FormPanel.this.taxField.setText("");
                FormPanel.this.taxField.setText("");
                FormPanel.this.taxField.setEnabled(false);
                FormPanel.this.empCombo.setSelectedIndex(2);
                FormPanel.this.citizen.setSelected(false);

            }
        });

        layoutComponents();

    }

    private void layoutComponents() {
        //add this components inside a FromPanel
        this.setLayout(new GridBagLayout());

        //generate a layout of the components
        GridBagConstraints gc = new GridBagConstraints();

        //drew a FormPanel grid of nameLabel
        gc.gridx = 0;//column 0
        gc.gridy = 0;//row 0
        gc.weightx = 0.1;//width of this horizontale
        gc.weighty = 0.1;//width of this vertical
        //specifie if this components take the width and height size
        gc.fill = GridBagConstraints.NONE;

        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        this.add(nameLabel, gc);

        //drew a FormPanel grid of occupationLabel
        gc.gridx = 1;
        gc.gridy = 0;
        gc.weightx = 0.1;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        this.add(nameText, gc);

        //drew a occupation components
        gc.gridy++;
        gc.gridx = 0;
        gc.weightx = 0.1;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        this.add(occupationLabel, gc);

        gc.gridx = 1;
        gc.weightx = 0.1;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        this.add(occupationText, gc);

        //drew a JList  label of this
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        this.add(ageLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(ageList, gc);



        //drew a empCombo
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        this.add(empLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(empCombo, gc);

        //drew a checkbox and tax
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.insets = new Insets(0, 0 ,0, 5);
        this.add(new JLabel("is citizen: "), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(citizen, gc);

        //drew a tax components

        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.insets = new Insets(0,0,0,5);
        this.add(taxLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(taxField, gc);

        //drew a tax gender radio

        gc.weightx = 0.1;
        gc.weighty = 0.05;

        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        this.add(new JLabel("Gendre: "), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(maleRadio, gc);

        gc.gridy++;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(femaleRadio, gc);

        //drew a btn component
        gc.gridx = 1;
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(submitBtn, gc);
    }

    public void setFormEventOccured(FormListener formListener) {
        this.formListener = formListener;
    }

    //this class manage a index of JList age
    class AgeCategory {
        private int id;
        private String text;

        AgeCategory(int id, String text) {
            this.id = id;
            this.text = text;
        }

        public int getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return this.getText();
        }
    }

}
