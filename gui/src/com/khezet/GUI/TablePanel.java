package com.khezet.GUI;

import com.khezet.model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TablePanel extends JPanel {

    private JTable jTable;
    private PersonTableModel tableModel;
    private JPopupMenu popupMenu;
    private PersonTableListener personTableListener;

    TablePanel() {
        tableModel = new PersonTableModel();
        jTable = new JTable(tableModel);
        popupMenu = new JPopupMenu();
        JMenuItem remove = new JMenuItem("remove");

        this.popupMenu.add(remove);
        // select(design by blue) a row to delete
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //get a row
                int row = jTable.rowAtPoint(e.getPoint());
                //select this row
                jTable.getSelectionModel().setSelectionInterval(row, row);
                if(e.getButton() == MouseEvent.BUTTON3)
                    popupMenu.show(jTable, e.getX(), e.getY());
            }
        });
        //add a actionListener to remove popup to deletea row
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = jTable.getSelectedRow();
                personTableListener.deletePerson(row);
                tableModel.fireTableRowsDeleted(row, row);
            }
        });

        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(jTable), BorderLayout.CENTER);

    }

    public void setData(List<Person> db) {
        tableModel.setData(db);
    }

    public void refresh() {
        this.tableModel.fireTableDataChanged();
    }

    public void setPersonTableListener(PersonTableListener listener) {
        this.personTableListener = listener;
    }
}
