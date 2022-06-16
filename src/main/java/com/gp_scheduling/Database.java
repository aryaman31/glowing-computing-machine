package com.gp_scheduling;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;;

public class Database {
    private Connection c;

    public Database(String dburl) throws SQLException {
        try { 
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(dburl);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public void setupDatabase() throws SQLException {
        DatabaseMetaData dbm = c.getMetaData();
        ResultSet tables = null;
        Statement stmt = null;
        String sql = "";

        // Check for clinics table
        tables = dbm.getTables(null, null, "clinics", null);
        if (!tables.next()) {
            stmt = c.createStatement();
            sql = "CREATE TABLE Clinics ( " + 
                "clinic_id INT PRIMARY KEY NOT NULL, " + 
                 ")";
            stmt.executeQuery(sql);
            stmt.close();
        }

        // Check for Hospital table
        tables = dbm.getTables(null, null, "Hospital", null);
        if (!tables.next()) {
            stmt = c.createStatement();
            sql = "CREATE TABLE Hospitals ( " + 
                "hospital_id INT PRIMARY KEY NOT NULL, " + 
                 ")";
            stmt.executeQuery(sql);
            stmt.close();
        }

        // Check for patients table
        tables = dbm.getTables(null, null, "patients", null);
        if (!tables.next()) {
            stmt = c.createStatement();
            sql = "CREATE TABLE Patients ( " + 
                "patient_id INT PRIMARY KEY NOT NULL, " + 
                "firstname TEXT NOT NULL," + 
                "secondname TEXT NOT NULL," + 
                "clinic_id INT," + 
                "hospital_id INT," +
                "FOREIGN KEY (clinic_id) REFERENCES Clinics(clinic_id)," + 
                "FOREIGN KEY (hospital_id) REFERENCES Hospitals (hospital_id))";
            stmt.executeQuery(sql);
            stmt.close();
        }  
    }


    // public static void main(String[] args) throws SQLException {
    //     Connection c = null; 
    //     try {
    //         Class.forName("org.postgresql.Driver");
    //         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb", "postgres", "123");
    //         System.out.println("Opened database successfully");

    //         Statement stmt = c.createStatement();
    //         String sql = "CREATE TABLE PATIENTS (" + 
    //             "Patient_id int ," +
    //             "firstname varchar(255) ," +
    //             "secondname varchar(255)" + 
    //             ")";
    //         stmt.executeUpdate(sql);
    //         stmt.close();
    //         c.close();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         System.err.println(e.getClass().getName()+": "+e.getMessage());
    //         System.exit(0);
    //     }
    //     System.out.println("Table created succesfully");
    // }
}
