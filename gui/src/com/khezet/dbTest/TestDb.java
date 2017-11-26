package com.khezet.dbTest;

import com.khezet.model.*;

import java.sql.SQLException;


public class TestDb {

    public static void main(String[] args){

        Database db = new Database();
        System.out.println("test");
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.addPerson(new Person("khezer", "builder", AgeCategorie.senior, EmployementCategory.selfEmployed, "777", true, Gender.male));
        db.addPerson(new Person("dayen", "dev", AgeCategorie.adult, EmployementCategory.employed, "456", true, Gender.male));
        System.out.println(db.getPeople());
        try {
            db.save();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //db.disconnect();

    }

}
