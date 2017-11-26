package com.khezet.GUI;

import com.khezet.model.Person;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PersonTableModel extends AbstractTableModel {

    private List<Person> db;
    private String[] colNames = {"ID", "Name", "Occupation", "Age Cat", "Employement", "DzCitizen", "TaxID"};

    PersonTableModel(){}

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public int getRowCount() {
        return this.db.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Person person = this.db.get(row);
        switch (col){
            case 0: return person.getId();
            case 1: return person.getName();
            case 2: return person.getOccupation();
            case 3: return person.getAge();
            case 4: return person.getEmpCat();
            case 5: return person.getUsCitizen();
            case 6: return person.getTaxId();
        }
        return null;
    }

    public void setData(List<Person> db) {
        this.db = db;
    }
}
