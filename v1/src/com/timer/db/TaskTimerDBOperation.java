// 
// Decompiled by Procyon v0.5.36
// 
package com.timer.db;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import com.timer.tos.TaskTimerTO;

public class TaskTimerDBOperation {

    public void createTask(final TaskTimerTO taskTimerTO) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            stmt = c.createStatement();
            final String sql = "INSERT INTO TASKS(TASKDETAILS,TIMELIMIT) VALUES('" + taskTimerTO.getTaskDetails() + "'," + taskTimerTO.getTimeLimit() + ")";
            stmt.executeUpdate(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex2) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex2);
            JOptionPane.showMessageDialog(null, ex2.getMessage());
        }
    }

    public TaskTimerTO retrieveOneTask(final Integer id) {
        Connection c = null;
        Statement stmt = null;
        TaskTimerTO taskTimerTO = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            stmt = c.createStatement();
            final String sql = "SELECT * FROM TASKS WHERE ID =" + id + " ";
            final ResultSet rs = stmt.executeQuery(sql);
            taskTimerTO = new TaskTimerTO();
            taskTimerTO.setTaskDetails(rs.getString("taskDetails"));
            taskTimerTO.setTimeLimit(rs.getInt("timeLimit"));
            taskTimerTO.setTimeLeft(rs.getInt("timeLeft"));
            taskTimerTO.setStatus(rs.getInt("status"));
            stmt.close();
            c.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex2) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex2);
            JOptionPane.showMessageDialog(null, ex2.getMessage());
        }
        return taskTimerTO;
    }

    public ArrayList<TaskTimerTO> retrieveLastFiveTasks() {
        Connection connection = null;
        Statement stmt = null;
        TaskTimerTO taskTimerTO = null;
        final List<TaskTimerTO> taskTimerTOList = new ArrayList<TaskTimerTO>();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            stmt = connection.createStatement();
            final String sql = "SELECT * FROM TASKS where TIMELIMIT > 0 ORDER BY ID DESC LIMIT 5";
            final ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("timeLimit: "+rs.getInt("timeLimit"));
                taskTimerTO = new TaskTimerTO();
                taskTimerTO.setId(Integer.parseInt(rs.getString("id")));
                taskTimerTO.setTaskDetails(rs.getString("taskDetails"));
                taskTimerTO.setTimeLimit(rs.getInt("timeLimit"));
                taskTimerTO.setTimeLeft(rs.getInt("timeLeft"));
                taskTimerTO.setStatus(rs.getInt("status"));
                taskTimerTOList.add(taskTimerTO);
            }
            stmt.close();
            connection.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex2) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex2);
            JOptionPane.showMessageDialog(null, ex2.getMessage());
        }
        return (ArrayList<TaskTimerTO>) (ArrayList) taskTimerTOList;
    }

    public TaskTimerTO retrieveLastTask() {
        Connection c = null;
        Statement stmt = null;
        TaskTimerTO taskTimerTO = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            stmt = c.createStatement();
            final String sql = "SELECT * FROM TASKS ORDER BY  ID DESC LIMIT 1 ";
            final ResultSet rs = stmt.executeQuery(sql);
            taskTimerTO = new TaskTimerTO();
            taskTimerTO.setId(Integer.parseInt(rs.getString("id")));
            taskTimerTO.setTaskDetails(rs.getString("taskDetails"));
            taskTimerTO.setTimeLimit(rs.getInt("timeLimit"));
            taskTimerTO.setTimeLeft(rs.getInt("timeLeft"));
            taskTimerTO.setStatus(rs.getInt("status"));
            rs.close();
            stmt.close();
            c.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex2) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex2);
            JOptionPane.showMessageDialog(null, ex2.getMessage());
        }
        return taskTimerTO;
    }

    public ArrayList<TaskTimerTO> retrieveAllTasks() {
        Connection c = null;
        Statement stmt = null;
        TaskTimerTO taskTimerTO = null;
        final List<TaskTimerTO> taskTimerTOList = new ArrayList<TaskTimerTO>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            System.out.println("Connection Successful");
            stmt = c.createStatement();
            final String sql = "SELECT * FROM TASKS DESC  ";
            final ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                taskTimerTO = new TaskTimerTO();
                taskTimerTO.setId(Integer.parseInt(rs.getString("id")));
                taskTimerTO.setTaskDetails(rs.getString("taskDetails"));
                taskTimerTO.setTimeLimit(rs.getInt("timeLimit"));
                taskTimerTO.setTimeLeft(rs.getInt("timeLeft"));
                taskTimerTO.setStatus(rs.getInt("status"));
                taskTimerTOList.add(taskTimerTO);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex2) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex2);
            JOptionPane.showMessageDialog(null, ex2.getMessage());
        }
        return (ArrayList<TaskTimerTO>) (ArrayList) taskTimerTOList;
    }

    public void updateTask(final Integer id) {
    }

    public void updateTasks(final List<TaskTimerTO> taskTimerTOList) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            for (final TaskTimerTO taskTimerTO : taskTimerTOList) {
                final String sql = "UPDATE TASKS SET TIMELIMIT = " + taskTimerTO.getTimeLimit() + " WHERE ID = " + taskTimerTO.getId() + " ";
                stmt.executeUpdate(sql);
                c.commit();
            }
            stmt.close();
            c.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex2) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex2);
            JOptionPane.showMessageDialog(null, ex2.getMessage());
        }
    }

    public void deleteTask(final Integer id) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TaskTimer.db");
            stmt = c.createStatement();
            final String sql = "DELETE FROM TASKS WHERE ID = " + id;
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex2) {
            Logger.getLogger(TestSQLite.class.getName()).log(Level.SEVERE, null, ex2);
            JOptionPane.showMessageDialog(null, ex2.getMessage());
        }
    }

    public void deleteAllTasks() {
    }
}
