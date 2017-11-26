package com.khezet.controller;

import com.khezet.GUI.FormEvent;
import com.khezet.GUI.MainFrame;
import com.khezet.model.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Controller {

    private Database db = new Database();

    public void addPerson(FormEvent event) {
        String name = event.getName();
        String occupation = event.getOccupation() ;
        int ageCatId = event.getAge();
        String empCatText = event.getEmpCat() ;
        String id = event.getTaxId();
        Boolean usCitizen = event.getUsCitizen();
        String genderText = event.getGender();
        AgeCategorie ageCat = (ageCatId == 0)?
                              AgeCategorie.child :
                              (ageCatId == 1)? AgeCategorie.adult :AgeCategorie.senior;
        EmployementCategory empCat =(empCatText.equals("employed"))?
                                    EmployementCategory.employed :
                                    (empCatText.equals("unemployed"))? EmployementCategory.unemployed :
                                    (empCatText.equals("self-emplyed"))? EmployementCategory.selfEmployed :
                                    EmployementCategory.other;
        Gender gender = genderText.equals("Female")? Gender.female : Gender.male;

        Person person = new Person(name, occupation, ageCat, empCat, id, usCitizen, gender);
        db.addPerson(person);
    }

    public List<Person> getPeople() {
        return this.db.getPeople();
    }

    /**
     * serialize a object into File file
     * @param file filename of where save a object
     */
    public void saveToFile(File file) throws IOException {
        this.db.saveToFile(file);
    }

    /**
     * deserialeze a File file
     * @param file filename of where extract the object
     * @throws IOException
     */
    public void loadFromFile(File file) throws IOException {
        this.db.loadFromFile(file);
    }

    /**
     * remove a row from people list
     * @param row
     */
    public void removePerson(int row) {
        this.db.removePerson(row);
    }

    /**
     * connect to DB
     */
    public void connect() {
        try {
            this.db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * load data
     */
    public void load () throws SQLException {
        this.db.load();
    }

    /**
     * save data into DB
     */
    public void save() throws SQLException {
        this.db.save();
    }

    /**
     * disconnect a DB
     */
    public void disconnect() {
        this.db.disconnect();
    }


}
