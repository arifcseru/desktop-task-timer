/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timer.forms;

import com.timer.db.TaskTimerDBOperation;
import com.timer.tos.TaskTimerTO;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

/**
 *
 * @author BDTUNE
 */
public class AddTaskTimer extends javax.swing.JFrame {

    /**
     * Creates new form AddTaskTimer
     */
    int xMouse;
    int yMouse;
    public AddTaskTimer() {
        initComponents();
         this.setSize(600, 300);
        this.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4));
       
         if (TaskTimer.toBeActivatedTheme.equalsIgnoreCase("windows")) {
            this.backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/timer/pictures/AddTaskTimer_linux.gif"))); // NOI18N
            
        }else{
         this.backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/timer/pictures/AddTaskTimer.gif"))); // NOI18N
        
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        taskDetailsTextBox = new javax.swing.JTextField();
        timeLimitTextBox = new javax.swing.JTextField();
        addedSuccessfulLabel = new javax.swing.JLabel();
        setTaskLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cancelLabel = new javax.swing.JLabel();
        backgroundLabel = new javax.swing.JLabel();

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        taskDetailsTextBox.setFont(new java.awt.Font("Vrinda", 1, 18)); // NOI18N
        taskDetailsTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taskDetailsTextBoxActionPerformed(evt);
            }
        });
        getContentPane().add(taskDetailsTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 410, 30));

        timeLimitTextBox.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        getContentPane().add(timeLimitTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 260, -1));

        addedSuccessfulLabel.setForeground(new java.awt.Color(255, 0, 0));
        getContentPane().add(addedSuccessfulLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 210, 190, 20));

        setTaskLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setTaskLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setTaskLabelMouseClicked(evt);
            }
        });
        getContentPane().add(setTaskLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 130, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Time limit:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 100, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Task Details: ");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        cancelLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelLabelMouseClicked(evt);
            }
        });
        getContentPane().add(cancelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 230, 130, 30));

        backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/timer/pictures/AddTaskTimer_linux.gif"))); // NOI18N
        backgroundLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        backgroundLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                backgroundLabelMouseDragged(evt);
            }
        });
        backgroundLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                backgroundLabelMousePressed(evt);
            }
        });
        getContentPane().add(backgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1, 600, 310));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backgroundLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backgroundLabelMousePressed
        // TODO add your handling code here:
         xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_backgroundLabelMousePressed

    private void backgroundLabelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backgroundLabelMouseDragged
        // TODO add your handling code here:
         int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_backgroundLabelMouseDragged

    private void cancelLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelLabelMouseClicked
        // TODO add your handling code here:
        if (TaskTimer.activeTaskTimerWindow.equalsIgnoreCase("maximum")) {
            //TaskTimer taskTimer = new TaskTimer();
            //taskTimer.setVisible(true);
        }else if (TaskTimer.activeTaskTimerWindow.equalsIgnoreCase("minimum")) {
            //SmallTimerFrame smallTimerFrame = new SmallTimerFrame();
           // smallTimerFrame.setVisible(true);
        }else if(TaskTimer.activeTaskTimerWindow.equalsIgnoreCase("tiny")){
            TinyTimer tinyTimer = new TinyTimer();
            tinyTimer.setVisible(true);
        }
        
        this.dispose();
    }//GEN-LAST:event_cancelLabelMouseClicked

    private void setTaskLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setTaskLabelMouseClicked
        // TODO add your handling code here:
        try {
            TaskTimerDBOperation dBOperation = new TaskTimerDBOperation();
        TaskTimerTO taskTimerTO = new TaskTimerTO();
        taskTimerTO.setTaskDetails(taskDetailsTextBox.getText());
        String timeLImit = timeLimitTextBox.getText();
        String hr[];
            hr = timeLImit.split(" ");
            Integer hoursSeconds = Integer.parseInt(hr[0].substring(0, hr[0].indexOf('h')))*3600;
            Integer minutesSeconds = Integer.parseInt(hr[1].substring(0, hr[1].indexOf('m')))*60;
            Integer seconds = Integer.parseInt(hr[2].substring(0, hr[2].indexOf('s')))*1;
            System.out.println(hoursSeconds+minutesSeconds+seconds);
            Integer totalSeconds = hoursSeconds+minutesSeconds+seconds;
        taskTimerTO.setTimeLimit(totalSeconds);
       
        dBOperation.createTask(taskTimerTO);
        this.taskDetailsTextBox.setText("");
        this.timeLimitTextBox.setText("");
         addedSuccessfulLabel.setText("Successfully added the task");
        } catch (Exception e) {
            addedSuccessfulLabel.setText("Sorry try agaiin!");
            JOptionPane.showMessageDialog(null, e.getMessage()+"Please input like : 0h 0m 50s");
            
        }
    }//GEN-LAST:event_setTaskLabelMouseClicked

    private void taskDetailsTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taskDetailsTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_taskDetailsTextBoxActionPerformed

    /**
     * @param args the command line arguments
     */
   // public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       /* try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddTaskTimer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddTaskTimer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddTaskTimer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddTaskTimer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddTaskTimer().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addedSuccessfulLabel;
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JLabel cancelLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel setTaskLabel;
    private javax.swing.JTextField taskDetailsTextBox;
    private javax.swing.JTextField timeLimitTextBox;
    // End of variables declaration//GEN-END:variables
}
