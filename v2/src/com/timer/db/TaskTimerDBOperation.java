/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timer.db;

import com.timer.tos.TaskTimerTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author BDTUNE
 */
public class TaskTimerDBOperation {

    public void createTask(TaskTimerTO taskTimerTO) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            //System.out.println("Connection Successful");
            stmt = c.createStatement();
            String sql = "INSERT INTO TASKS(TASKDETAILS,TIMELIMIT) VALUES('" + taskTimerTO.getTaskDetails() + "'," + taskTimerTO.getTimeLimit() + ")";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        // System.out.println("Inserted Successful");
    }

    public TaskTimerTO retrieveOneTask(Integer id) {
        Connection c = null;
        Statement stmt = null;
        TaskTimerTO taskTimerTO = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            //System.out.println("Connection Successful");
            stmt = c.createStatement();
            String sql = "SELECT * FROM TASKS WHERE ID =" + id + " ";
            ResultSet rs = stmt.executeQuery(sql);
            taskTimerTO = new TaskTimerTO();
            taskTimerTO.setTaskDetails(rs.getString("taskDetails"));
            taskTimerTO.setTimeLimit(rs.getInt("timeLimit"));
            stmt.close();
            c.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return taskTimerTO;
    }

    public ArrayList<TaskTimerTO> retrieveLastFiveTasks() {
        Connection c = null;
        Statement stmt = null;
        TaskTimerTO taskTimerTO = null;
        List<TaskTimerTO> taskTimerTOList = new ArrayList<TaskTimerTO>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            //System.out.println("Connection Successful");
            stmt = c.createStatement();
            String sql = "SELECT * FROM TASKS ORDER BY  ID DESC LIMIT 5 ";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                taskTimerTO = new TaskTimerTO();
                taskTimerTO.setId(Integer.parseInt(rs.getString("id")));
                taskTimerTO.setTaskDetails(rs.getString("taskDetails"));
                taskTimerTO.setTimeLimit(rs.getInt("timeLimit"));
                taskTimerTOList.add(taskTimerTO);
            }
            stmt.close();
            c.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, "Initializing App.");
            createTable();
            addDefaultTasks();
            taskTimerTOList = retrieveLastFiveTasks();
        }
        return (ArrayList<TaskTimerTO>) taskTimerTOList;
    }

    public void createTable() {
        Connection c = null;

        Statement stmt = null;

        try {

            Class.forName("org.sqlite.JDBC");

            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");

            System.out.println("Database Opened...\n");

            stmt = c.createStatement();

            String sql = "CREATE TABLE TASKS "
                    + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " TASKDETAILS VARCHAR NOT NULL, "
                    + " TIMELIMIT INT NOT NULL) ";

            stmt.executeUpdate(sql);

            stmt.close();

            c.close();

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);

        }

        System.out.println("Table Product Created Successfully!!!");
    }

    private synchronized void addDefaultTasks() {
        try {
            System.out.println("Adding Default Tasks.");
            for (int i = 0; i < 5; i++) {
                TaskTimerTO taskTimerTO = new TaskTimerTO();
                taskTimerTO.setTaskDetails("Default Task..");
                taskTimerTO.setTimeLimit(100);
                this.createTask(taskTimerTO);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, String.valueOf(e.getMessage()));
        }
    }

    public TaskTimerTO retrieveLastTask() {
        Connection c = null;
        Statement stmt = null;
        TaskTimerTO taskTimerTO = null;
        //List<TaskTimerTO> taskTimerTOList = new ArrayList<TaskTimerTO>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            // System.out.println("Connection Successful");
            stmt = c.createStatement();
            String sql = "SELECT * FROM TASKS ORDER BY  ID DESC LIMIT 1 ";
            ResultSet rs = stmt.executeQuery(sql);
            //while (rs.next()) {                
            taskTimerTO = new TaskTimerTO();
            taskTimerTO.setId(Integer.parseInt(rs.getString("id")));
            taskTimerTO.setTaskDetails(rs.getString("taskDetails"));
            taskTimerTO.setTimeLimit(rs.getInt("timeLimit"));
            //taskTimerTOList.add(taskTimerTO);
            //}
            rs.close();
            stmt.close();
            c.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return taskTimerTO;
    }

    public ArrayList<TaskTimerTO> retrieveAllTasks() {
        Connection c = null;
        Statement stmt = null;
        TaskTimerTO taskTimerTO = null;
        List<TaskTimerTO> taskTimerTOList = new ArrayList<TaskTimerTO>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            System.out.println("Connection Successful");
            stmt = c.createStatement();
            String sql = "SELECT * FROM TASKS DESC  ";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                taskTimerTO = new TaskTimerTO();
                taskTimerTO.setId(Integer.parseInt(rs.getString("id")));
                taskTimerTO.setTaskDetails(rs.getString("taskDetails"));
                taskTimerTO.setTimeLimit(rs.getInt("timeLimit"));
                taskTimerTOList.add(taskTimerTO);
            }
            rs.close();
            stmt.close();
            c.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return (ArrayList<TaskTimerTO>) taskTimerTOList;
    }

    public void updateTask(Integer id) {

    }

    public void updateTasks(List<TaskTimerTO> taskTimerTOList) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            c.setAutoCommit(false);
            //System.out.println("Connection Successful");
            stmt = c.createStatement();

            for (TaskTimerTO taskTimerTO : taskTimerTOList) {
                String sql = "UPDATE TASKS SET TIMELIMIT = " + taskTimerTO.getTimeLimit() + " WHERE ID = " + taskTimerTO.getId() + " ";
                stmt.executeUpdate(sql);
                c.commit();
            }
            stmt.close();
            c.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        //System.out.println("Update Successful!");
    }

    public void deleteTask(Integer id) {
        List<TaskTimerTO> allTasks = retrieveAllTasks();

        if (allTasks.size() > 5) {
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
                // System.out.println("Connection Successful");
                stmt = c.createStatement();
                String sql = "DELETE FROM TASKS WHERE ID = " + id + "";
                stmt.executeUpdate(sql);
                //rs.close();
                stmt.close();
                c.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "You Cannot delete this task");
        }
    }

    public void deleteAllTasks() {

    }

    /*public static void main(String args[]){
        TaskTimerDBOperation bOperation = new TaskTimerDBOperation();
        
       //TaskTimerTO taskTimerTO = new TaskTimerTO();
        //taskTimerTO.setTaskDetails("Capture the moments");
       // taskTimerTO.setTimeLimit(300);
        
        //bOperation.createTask(taskTimerTO);
        //System.out.println(bOperation.retrieveLastFiveTasks().size()-1);
        bOperation.deleteTask(bOperation.retrieveLastTask().getId());
        System.out.println();
        
    }*/
}
