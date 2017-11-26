package com.khezet.model;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Database {

    private List<Person> people;
    private Connection con;

    public Database() {
        people = new LinkedList();
    }

    public void addPerson(Person person) {
        this.people.add(person);
    }

    public List<Person> getPeople() {
        return Collections.unmodifiableList(people);
    }

    /**
     * serialize a Database class
     * @param file a file receiver of this object serialezable
     * @throws IOException
     */
    public void saveToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos= new ObjectOutputStream(fos);

        Person[] persons = this.people.toArray(new Person[this.people.size()]);

        oos.writeObject(persons);
        fos.close();
        oos.close();
    }

    /**
     * desrialize this object
     * @param file a file to get a serialezable object
     * @throws IOException
     */
    public void loadFromFile(File file) throws IOException {
        try(
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
                ) {
            Person[] persons = (Person[]) ois.readObject();

            people.clear();

            people.addAll(Arrays.asList(persons));

            fis.close();
            ois.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete a 1 row of people (it's person)
     * @param row int
     */
    public void removePerson(int row) {
        this.people.remove(row);
    }

    /**
     * connection with DB
     * @throws Exception
     */
    public void connect() throws Exception {
        if (this.con != null) return;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/java";
        con = DriverManager.getConnection(url, "root", "");
        System.out.println("connect");

    }

    /**
     * insert or update the data into DB
     */
    public void save() throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM people WHERE id = ?";
        String insertSql= "INSERT INTO people (`name`, `employement_status`, `occupation`, `gender`, `dz_citizen`, `tax_id`, `age`) VALUES(?,?,?,?,?,?,?)";
        String updateSql= "UPDATE people SET name=?, occupation=?, employement_status=?, gender=? WHERE id = ?";

        PreparedStatement checkStmt = this.con.prepareStatement(checkSql);
        PreparedStatement insertStmt= this.con.prepareStatement(insertSql);
        PreparedStatement updateStmt= this.con.prepareStatement(updateSql);
        System.out.println("save"+this.people);
        for (Person person : people) {
            int id = person.getId();

            checkStmt.setInt(1, id);
            //save a result from DB
            ResultSet checkResult = checkStmt.executeQuery();
            //move to next row for avoid to destroy a earlier value of checkResult
            checkResult.next();
            int count = checkResult.getInt(1);

            int col = 0;
            if (count == 0){
                System.out.println("save"+person.getUsCitizen());
//                insertStmt.setInt(++col, person.getId());
                insertStmt.setString(++col, person.getName());
                insertStmt.setString(++col, person.getEmpCat().name());
                insertStmt.setString(++col, person.getOccupation());
                insertStmt.setString(++col, person.getGender().name());
                insertStmt.setBoolean(++col, person.getUsCitizen());
                insertStmt.setString(++col, person.getTaxId());
                insertStmt.setString(++col, person.getAge().name());

                insertStmt.executeUpdate();
            }else {

                updateStmt.setString(++col, person.getName());
                updateStmt.setString(++col, person.getOccupation());
                updateStmt.setString(++col, person.getEmpCat().name());
                updateStmt.setString(++col, person.getGender().name());
                updateStmt.setInt(++col, person.getId());

                updateStmt.executeUpdate();
            }


            System.out.printf("count of person have id %d is %d%n", id, count);
        }

        insertStmt.close();
        checkStmt.close();

    }

    public void load() throws SQLException {

        //clear all data into List people
        this.people.clear();
        Statement selectStmt = this.con.createStatement();
        String selectSql = "SELECT * FROM people";
        //result in ResultSet
        ResultSet result = selectStmt.executeQuery(selectSql);
        System.out.println("load");
        while (result.next()){
            int id = result.getInt("id");
            String name = result.getString("name");
            String occu = result.getString("occupation");
            Boolean isDz = result.getBoolean("dz_citizen");
            String emp  = result.getString("employement_status");
            String gend = result.getString("gender");
            String taxI = result.getString("tax_id");
            String age = result.getString("age");

            this.addPerson(
                    new Person(
                            id, name, occu, AgeCategorie.valueOf(age), EmployementCategory.valueOf(emp), taxI, isDz, Gender.valueOf(gend)
                    )
            );
        }
        System.out.println("people" + this.people);

    }

    /**
     * disconnect a connection to DB
     */
    public void disconnect() {

        if ( this.con != null ) {

            try {
                this.con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        System.out.println("dis");
    }
    
}
