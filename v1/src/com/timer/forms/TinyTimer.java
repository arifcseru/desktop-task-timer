// 
// Decompiled by Procyon v0.5.36
// 

package com.timer.forms;

import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import java.awt.Color;
import java.awt.Font;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.Toolkit;
import javax.swing.JLabel;
import com.timer.tos.TaskTimerTO;
import java.util.List;
import com.timer.db.TaskTimerDBOperation;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class TinyTimer extends JFrame implements ActionListener
{
    int xMouse;
    int yMouse;
    int counter;
    Integer taskTimeLeft;
    Timer timer;
    Integer timeLimit;
    Integer min;
    Integer sec;
    Integer hr;
    TaskTimerDBOperation bOperation;
    List<TaskTimerTO> taskTimerTOList;
    Integer selectedTaskId;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel taskTimeLeftLabel;
    
    public TinyTimer() {
        this.counter = 0;
        this.timer = new Timer(1000, this);
        this.bOperation = new TaskTimerDBOperation();
        this.selectedTaskId = TaskTimer.activeTaskId;
        this.initComponents();
        this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2.0 - 100.0), 0);
        this.taskTimerTOList = this.bOperation.retrieveLastFiveTasks();
        System.out.println("Sel: " + this.selectedTaskId);
        if (this.selectedTaskId != 0 && !TaskTimer.isTimerStop) {
            this.taskTimeLeft = this.taskTimerTOList.get(this.selectedTaskId - 1).getTimeLimit();
            this.timer.start();
        }
    }
    
    private void updateTaskTimerRecords() {
        if (this.taskTimerTOList != null) {
            this.taskTimerTOList.get(TaskTimer.activeTaskId - 1).setTimeLimit(this.taskTimeLeft);
            this.bOperation.updateTasks(this.taskTimerTOList);
        }
    }
    
    private void initComponents() {
        this.taskTimeLeftLabel = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel1 = new JLabel();
        this.setDefaultCloseOperation(3);
        this.setAlwaysOnTop(true);
        this.setCursor(new Cursor(13));
        this.setMinimumSize(new Dimension(150, 25));
        this.setUndecorated(true);
        this.setPreferredSize(new Dimension(150, 29));
        this.setResizable(false);
        this.getContentPane().setLayout(new AbsoluteLayout());
        this.taskTimeLeftLabel.setFont(new Font("Tahoma", 1, 14));
        this.taskTimeLeftLabel.setForeground(new Color(204, 0, 204));
        this.taskTimeLeftLabel.setText("--h --m --s");
        this.getContentPane().add(this.taskTimeLeftLabel, new AbsoluteConstraints(40, 10, 100, 20));
        this.jLabel3.setFont(new Font("Tahoma", 1, 12));
        this.jLabel3.setForeground(new Color(204, 0, 204));
        this.jLabel3.setText("rem.. ");
        this.getContentPane().add(this.jLabel3, new AbsoluteConstraints(0, 10, 40, 20));
        this.jLabel2.setCursor(new Cursor(12));
        this.jLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                TinyTimer.this.jLabel2MouseClicked(evt);
            }
        });
        this.getContentPane().add(this.jLabel2, new AbsoluteConstraints(130, 0, 20, 10));
        this.jLabel1.setIcon(new ImageIcon(this.getClass().getResource("/com/timer/pictures/tinyTimer.gif")));
        this.jLabel1.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(final MouseEvent evt) {
                TinyTimer.this.jLabel1MouseDragged(evt);
            }
        });
        this.jLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent evt) {
                TinyTimer.this.jLabel1MousePressed(evt);
            }
        });
        this.getContentPane().add(this.jLabel1, new AbsoluteConstraints(0, 0, 150, 30));
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
    
    private void jLabel2MouseClicked(final MouseEvent evt) {
        final SmallTimerFrame smallTimerFrame = new SmallTimerFrame();
        smallTimerFrame.setVisible(true);
        this.dispose();
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
        this.taskTimeLeftLabel.setText(String.valueOf(this.hr.toString()) + " h " + this.min.toString() + " m " + this.sec.toString() + "s");
        ++this.counter;
        if (this.counter >= 20) {
            this.counter = 0;
            this.updateTaskTimerRecords();
        }
    }
}
