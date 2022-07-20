// 
// Decompiled by Procyon v0.5.36
// 

package com.timer.forms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Cursor;
import java.awt.LayoutManager;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JLabel;
import com.timer.tos.TaskTimerTO;
import java.util.List;
import com.timer.db.TaskTimerDBOperation;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class SmallTimerFrame extends JFrame implements ActionListener
{
    int counter;
    int xMouse;
    int yMouse;
    Integer taskTimeLeft;
    Timer timer;
    Integer timeLimit;
    Integer min;
    Integer sec;
    Integer hr;
    TaskTimerDBOperation bOperation;
    List<TaskTimerTO> taskTimerTOList;
    Integer selectedTaskId;
    private JLabel backgroundLabel;
    private JLabel clockLabel;
    private JLabel closeLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel maximizeLabel;
    private JButton startButton;
    private JButton stopButton;
    private JLabel taskDetailsLabel;
    private JLabel taskTimeLeftLabel;
    
    public SmallTimerFrame() {
        this.counter = 0;
        this.timer = new Timer(1000, this);
        this.bOperation = new TaskTimerDBOperation();
        this.selectedTaskId = TaskTimer.activeTaskId;
        this.initComponents();
        this.setSize(300, 100);
        this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getSize().getWidth() - 10.0), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - this.getSize().getHeight() - 40.0));
        this.taskTimerTOList = this.bOperation.retrieveLastFiveTasks();
        if (this.selectedTaskId != 0 && !TaskTimer.isTimerStop) {
            this.timer.start();
            this.taskTimeLeft = this.taskTimerTOList.get(this.selectedTaskId - 1).getTimeLimit();
            this.startButton.setEnabled(false);
            this.stopButton.setEnabled(true);
        }
        else {
            this.startButton.setEnabled(false);
            this.stopButton.setEnabled(false);
        }
    }
    
    private void updateTaskTimerRecords() {
        if (this.taskTimerTOList != null) {
            this.taskTimerTOList.get(TaskTimer.activeTaskId - 1).setTimeLimit(this.taskTimeLeft);
            this.bOperation.updateTasks(this.taskTimerTOList);
        }
    }
    
    private void initComponents() {
        this.maximizeLabel = new JLabel();
        this.jLabel1 = new JLabel();
        this.closeLabel = new JLabel();
        this.jLabel2 = new JLabel();
        this.taskTimeLeftLabel = new JLabel();
        this.taskDetailsLabel = new JLabel();
        this.startButton = new JButton();
        this.stopButton = new JButton();
        this.clockLabel = new JLabel();
        this.backgroundLabel = new JLabel();
        this.setDefaultCloseOperation(3);
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.getContentPane().setLayout(new AbsoluteLayout());
        this.maximizeLabel.setCursor(new Cursor(12));
        this.maximizeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                SmallTimerFrame.this.maximizeLabelMouseClicked(evt);
            }
            
            @Override
            public void mousePressed(final MouseEvent evt) {
                SmallTimerFrame.this.maximizeLabelMousePressed(evt);
            }
        });
        this.getContentPane().add(this.maximizeLabel, new AbsoluteConstraints(270, 0, 10, 10));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setCursor(new Cursor(12));
        this.jLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                SmallTimerFrame.this.jLabel1MouseClicked(evt);
            }
        });
        this.getContentPane().add(this.jLabel1, new AbsoluteConstraints(0, 0, 20, 20));
        this.closeLabel.setFont(new Font("Tahoma", 1, 14));
        this.closeLabel.setCursor(new Cursor(12));
        this.closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                SmallTimerFrame.this.closeLabelMouseClicked(evt);
            }
        });
        this.getContentPane().add(this.closeLabel, new AbsoluteConstraints(290, 0, 10, 10));
        this.jLabel2.setCursor(new Cursor(12));
        this.jLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                SmallTimerFrame.this.jLabel2MouseClicked(evt);
            }
        });
        this.getContentPane().add(this.jLabel2, new AbsoluteConstraints(240, 0, 20, 20));
        this.taskTimeLeftLabel.setFont(new Font("Tahoma", 1, 18));
        this.taskTimeLeftLabel.setForeground(new Color(255, 51, 0));
        this.getContentPane().add(this.taskTimeLeftLabel, new AbsoluteConstraints(10, 60, 220, 30));
        this.taskDetailsLabel.setFont(new Font("Vrinda", 1, 14));
        this.getContentPane().add(this.taskDetailsLabel, new AbsoluteConstraints(10, 30, 220, 30));
        this.startButton.setText("Start");
        this.startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SmallTimerFrame.this.startButtonActionPerformed(evt);
            }
        });
        this.getContentPane().add(this.startButton, new AbsoluteConstraints(230, 30, -1, -1));
        this.stopButton.setText("Stop");
        this.stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SmallTimerFrame.this.stopButtonActionPerformed(evt);
            }
        });
        this.getContentPane().add(this.stopButton, new AbsoluteConstraints(230, 60, -1, -1));
        this.clockLabel.setFont(new Font("Tahoma", 1, 11));
        this.clockLabel.setForeground(new Color(51, 153, 0));
        this.getContentPane().add(this.clockLabel, new AbsoluteConstraints(30, 0, 180, 20));
        this.backgroundLabel.setIcon(new ImageIcon(this.getClass().getResource("/com/timer/pictures/backgroundImage.gif")));
        this.backgroundLabel.setCursor(new Cursor(13));
        this.backgroundLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(final MouseEvent evt) {
                SmallTimerFrame.this.backgroundLabelMouseDragged(evt);
            }
        });
        this.backgroundLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent evt) {
                SmallTimerFrame.this.backgroundLabelMousePressed(evt);
            }
        });
        this.getContentPane().add(this.backgroundLabel, new AbsoluteConstraints(0, 0, 300, 100));
        this.pack();
    }
    
    private void closeLabelMouseClicked(final MouseEvent evt) {
        System.exit(0);
    }
    
    private void maximizeLabelMousePressed(final MouseEvent evt) {
    }
    
    private void maximizeLabelMouseClicked(final MouseEvent evt) {
        if (!this.timer.isRunning()) {
            TaskTimer.activeTaskId = 0;
        }
        final TaskTimer taskTimer = new TaskTimer();
        taskTimer.setVisible(true);
        this.setVisible(false);
    }
    
    private void jLabel1MouseClicked(final MouseEvent evt) {
        final AddTaskTimer addTaskTimer = new AddTaskTimer();
        addTaskTimer.setVisible(true);
        this.dispose();
    }
    
    private void backgroundLabelMousePressed(final MouseEvent evt) {
        this.xMouse = evt.getX();
        this.yMouse = evt.getY();
    }
    
    private void backgroundLabelMouseDragged(final MouseEvent evt) {
        final int x = evt.getXOnScreen();
        final int y = evt.getYOnScreen();
        this.setLocation(x - this.xMouse, y - this.yMouse);
    }
    
    private void jLabel2MouseClicked(final MouseEvent evt) {
        final TinyTimer tinyTimer = new TinyTimer();
        tinyTimer.setVisible(true);
        this.dispose();
    }
    
    private void stopButtonActionPerformed(final ActionEvent evt) {
        if (this.timer.isRunning()) {
            System.out.println("Timer stopped!");
            this.timer.stop();
            TaskTimer.isTimerStop = true;
            this.startButton.setEnabled(true);
            this.stopButton.setEnabled(false);
        }
    }
    
    private void startButtonActionPerformed(final ActionEvent evt) {
        if (!this.timer.isRunning()) {
            this.timer.start();
            TaskTimer.isTimerStop = false;
            System.out.println("Timer started!");
            this.startButton.setEnabled(false);
            this.stopButton.setEnabled(true);
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (this.taskTimeLeft <= 0) {
            this.taskTimeLeft = 0;
            this.timer.stop();
        }
        --this.taskTimeLeft;
        this.min = this.taskTimeLeft / 60;
        this.sec = this.taskTimeLeft % 60;
        this.hr = this.min / 60;
        if (this.min >= 60) {
            this.min %= 60;
        }
        this.taskDetailsLabel.setText(this.taskTimerTOList.get(this.selectedTaskId - 1).getTaskDetails());
        this.taskTimeLeftLabel.setText(String.valueOf(this.hr.toString()) + " Hour " + this.min.toString() + " min " + this.sec.toString() + "sec");
        ++this.counter;
        final Calendar today = Calendar.getInstance();
        final DateFormat dateFormat = new SimpleDateFormat("dd-MMM > h-m-s");
        this.clockLabel.setText(dateFormat.format(today.getTime()));
        if (this.counter >= 20) {
            this.counter = 0;
            this.updateTaskTimerRecords();
        }
    }
}
