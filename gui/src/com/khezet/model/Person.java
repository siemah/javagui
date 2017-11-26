package com.khezet.model;

import java.io.Serializable;

public class Person implements Serializable {

    private static int count = 1;
    private int id;
    private String name;
    private String occupation;
    private AgeCategorie age;
    private EmployementCategory empCat;
    private String taxId;
    private Boolean usCitizen;
    private Gender gender;

    public Person( String name, String occupation, AgeCategorie age, EmployementCategory empCat, String taxID, Boolean usCitizen, Gender gender) {
        setName(name);
        setOccupation(occupation);
        setAge(age);
        setEmpCat(empCat);
        setTaxId(taxID);
        setUsCitizen(usCitizen);
        this.gender = gender;
        count++;
        this.id = count;
    }

    public Person( int id, String name, String occupation, AgeCategorie age, EmployementCategory empCat, String taxID, Boolean usCitizen, Gender gender) {
        this(name, occupation, age, empCat, taxID, usCitizen, gender);
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setAge(AgeCategorie age) {
        this.age = age;
    }

    public AgeCategorie getAge() {
        return age;
    }

    public void setEmpCat(EmployementCategory empCat) {
        this.empCat = empCat;
    }

    public EmployementCategory getEmpCat() {
        return empCat;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public Boolean getUsCitizen() {
        return usCitizen;
    }

    public void setUsCitizen(Boolean usCitizen) {
        this.usCitizen = usCitizen;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("id: %d, name: %s", this.getId(), this.getName());
    }
}
