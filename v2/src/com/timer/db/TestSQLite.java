/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BDTUNE
 */
public class TestSQLite {
    /*public static void main(String args[]){
    Connection c = null;
    Statement stmt =null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            System.out.println("Connection Successful");
           stmt = c.createStatement();
           String sql = "INSERT INTO TASKS(TASKDETAILS,TIMELIMIT) VALUES('Create entities',300)";
           stmt.executeUpdate(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
            
          System.out.println("Inserted Successful");
    }*/
    
    
}
