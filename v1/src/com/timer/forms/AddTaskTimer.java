// 
// Decompiled by Procyon v0.5.36
// 

package com.timer.forms;

import javax.swing.JOptionPane;
import com.timer.tos.TaskTimerTO;
import com.timer.db.TaskTimerDBOperation;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Cursor;
import java.awt.Color;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import java.awt.Font;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JFrame;

public class AddTaskTimer extends JFrame
{
    int xMouse;
    int yMouse;
    private JLabel addedSuccessfulLabel;
    private JLabel cancelLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel5;
    private JLabel setTaskLabel;
    private JTextField taskDetailsTextBox;
    private JTextField timeLimitTextBox;
    
    public AddTaskTimer() {
        this.initComponents();
        this.setSize(600, 300);
        this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2.0), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2.0));
    }
    
    private void initComponents() {
        this.jLabel5 = new JLabel();
        this.taskDetailsTextBox = new JTextField();
        this.timeLimitTextBox = new JTextField();
        this.addedSuccessfulLabel = new JLabel();
        this.setTaskLabel = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel2 = new JLabel();
        this.cancelLabel = new JLabel();
        this.jLabel1 = new JLabel();
        this.jLabel5.setText("jLabel5");
        this.setDefaultCloseOperation(3);
        this.setUndecorated(true);
        this.getContentPane().setLayout(new AbsoluteLayout());
        this.taskDetailsTextBox.setFont(new Font("Vrinda", 1, 18));
        this.getContentPane().add(this.taskDetailsTextBox, new AbsoluteConstraints(130, 120, 420, 30));
        this.timeLimitTextBox.setFont(new Font("Tahoma", 1, 14));
        this.getContentPane().add(this.timeLimitTextBox, new AbsoluteConstraints(130, 190, 250, -1));
        this.addedSuccessfulLabel.setForeground(new Color(255, 0, 0));
        this.getContentPane().add(this.addedSuccessfulLabel, new AbsoluteConstraints(410, 200, 150, 20));
        this.setTaskLabel.setCursor(new Cursor(12));
        this.setTaskLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                AddTaskTimer.this.setTaskLabelMouseClicked(evt);
            }
        });
        this.getContentPane().add(this.setTaskLabel, new AbsoluteConstraints(110, 230, 180, 60));
        this.jLabel3.setFont(new Font("Tahoma", 1, 12));
        this.jLabel3.setText("Time limit:");
        this.getContentPane().add(this.jLabel3, new AbsoluteConstraints(10, 190, 100, -1));
        this.jLabel2.setFont(new Font("Tahoma", 1, 12));
        this.jLabel2.setText("Task Details: ");
        this.getContentPane().add(this.jLabel2, new AbsoluteConstraints(10, 130, -1, -1));
        this.cancelLabel.setCursor(new Cursor(12));
        this.cancelLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                AddTaskTimer.this.cancelLabelMouseClicked(evt);
            }
        });
        this.getContentPane().add(this.cancelLabel, new AbsoluteConstraints(300, 240, 180, 40));
        this.jLabel1.setIcon(new ImageIcon(this.getClass().getResource("/com/timer/pictures/AddTaskTimer.gif")));
        this.jLabel1.setCursor(new Cursor(13));
        this.jLabel1.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(final MouseEvent evt) {
                AddTaskTimer.this.jLabel1MouseDragged(evt);
            }
        });
        this.jLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent evt) {
                AddTaskTimer.this.jLabel1MousePressed(evt);
            }
        });
        this.getContentPane().add(this.jLabel1, new AbsoluteConstraints(0, 1, 600, 310));
        this.pack();
    }
    
    private void jLabel1MousePressed(final MouseEvent evt) {
        this.xMouse = evt.getX();
        this.yMouse = evt.getY();
    }
    
    private void jLabel1MouseDragged(final MouseEvent evt) {
        final int x = evt.getXOnScreen();
        final int y = evt.getYOnScreen();
        this.setLocation(x - this.xMouse, y - this.yMouse);
    }
    
    private void cancelLabelMouseClicked(final MouseEvent evt) {
        final TaskTimer taskTimer = new TaskTimer();
        taskTimer.setVisible(true);
        this.dispose();
    }
    
    private void setTaskLabelMouseClicked(final MouseEvent evt) {
        try {
            final TaskTimerDBOperation dBOperation = new TaskTimerDBOperation();
            final TaskTimerTO taskTimerTO = new TaskTimerTO();
            taskTimerTO.setTaskDetails(this.taskDetailsTextBox.getText());
            final String timeLImit = this.timeLimitTextBox.getText();
            final String[] hr = timeLImit.split(" ");
            final Integer hoursSeconds = Integer.parseInt(hr[0].substring(0, hr[0].indexOf(104))) * 3600;
            final Integer minutesSeconds = Integer.parseInt(hr[1].substring(0, hr[1].indexOf(109))) * 60;
            final Integer seconds = Integer.parseInt(hr[2].substring(0, hr[2].indexOf(115))) * 1;
            System.out.println(hoursSeconds + minutesSeconds + seconds);
            final Integer totalSeconds = hoursSeconds + minutesSeconds + seconds;
            taskTimerTO.setTimeLimit(totalSeconds);
            dBOperation.createTask(taskTimerTO);
            this.taskDetailsTextBox.setText("");
            this.timeLimitTextBox.setText("");
            this.addedSuccessfulLabel.setText("Successfully added the task");
        }
        catch (Exception e) {
            this.addedSuccessfulLabel.setText("Sorry try agaiin!");
            JOptionPane.showMessageDialog(null, String.valueOf(e.getMessage()) + "Please input like : 0h 0m 50s");
        }
    }
}
