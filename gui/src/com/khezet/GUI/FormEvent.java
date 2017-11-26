package com.khezet.GUI;

import java.util.EventObject;

public class FormEvent extends EventObject {

    private String name;
    private String occupation;
    private int age;
    private String empCat;
    private String taxId;
    private Boolean usCitizen;
    private String gender;

    public FormEvent(Object source) {
        super(source);
    }

    public FormEvent(Object source, String name, String occupation, int age, String empCat, String taxID, Boolean usCitizen, String gender) {
        super(source);
        setName(name);
        setOccupation(occupation);
        setAge(age);
        setEmpCat(empCat);
        setTaxId(taxID);
        setUsCitizen(usCitizen);
        this.gender = gender;
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

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setEmpCat(String empCat) {
        this.empCat = empCat;
    }

    public String getEmpCat() {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
