package com.database;

public class GP {

    private int gp_id;
    private String first_name;
    private String surname;
    private String salted;
    private String salt;

    public GP(int gp_id, String first_name, String surname, String salted, String salt) {
        this.gp_id = gp_id;
        this.first_name = first_name;
        this.surname = surname;
        this.salted = salted;
        this.salt = salt;
    }


    public void setGp_id(int gp_id) {
        this.gp_id = gp_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSalted(String salted) {
        this.salted = salted;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getGp_id() {
        return gp_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSalted() {
        return salted;
    }

    public String getSalt() {
        return salt;
    }
}
