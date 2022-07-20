/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timer.forms;

import com.sun.java.swing.plaf.windows.resources.windows;
import com.timer.db.TaskTimerDBOperation;
import com.timer.tos.TaskTimerTO;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author BDTUNE
 */
public class TaskTimer extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form TaskTimer
     */
    public static String activeTaskTimerWindow = "Maximum";
    public static String toBeActivatedTheme = "Windows";
    public static Integer activeTaskId = 0;
    public static boolean isTimerStop = true;
    int timerToClose = 5;
    int counter = 0;
    int xMouse;
    int yMouse;

    Integer taskOneTimeLeft;
    Integer taskTwoTimeLeft;
    Integer taskThreeTimeLeft;
    Integer taskFourTimeLeft;
    Integer taskFiveTimeLeft;

    Timer timer = new Timer(1000, (ActionListener) this);

    Integer timeLimit;
    Integer min;
    Integer sec;
    Integer hr;
    TaskTimerDBOperation bOperation = new TaskTimerDBOperation();
    List<TaskTimerTO> taskTimerTOList;

    public TaskTimer() {

        initComponents();
        //this.setSize(350, 443);
        this.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getSize().getWidth() - 10), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - this.getSize().getHeight() - 40));
        timer.start();
        TaskTimer.isTimerStop = false;
        // this.closeLabelBtn.setVisible(false);
        this.checkEnabledTask();
        this.setContents();
        TaskTimer.activeTaskTimerWindow = "Maximum";
        this.changeThemeLabel.setText(TaskTimer.toBeActivatedTheme);
        startTimerOfTask1Button.setEnabled(true);
        startTimerOfTask2Button.setEnabled(true);
        startTimerOfTask3Button.setEnabled(true);
        startTimerOfTask4Button.setEnabled(true);
        startTimerOfTask5Button.setEnabled(true);

        stopTimerButton.setEnabled(false);

        if (TaskTimer.toBeActivatedTheme.equalsIgnoreCase("windows")) {
            backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/timer/pictures/BigWindowBackground_linux.gif")));
        } else {
            backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/timer/pictures/BigWindowBackground.gif"))); // NOI18N

        }
    }

    public void checkEnabledTask() {
        if (activeTaskId == 1) {
            startTimerOfTask1Button.setEnabled(false);
            startTimerOfTask2Button.setEnabled(true);
            startTimerOfTask3Button.setEnabled(true);
            startTimerOfTask4Button.setEnabled(true);
            startTimerOfTask5Button.setEnabled(true);
            stopTimerButton.setEnabled(true);
        } else if (activeTaskId == 2) {
            startTimerOfTask1Button.setEnabled(true);
            startTimerOfTask2Button.setEnabled(false);
            startTimerOfTask3Button.setEnabled(true);
            startTimerOfTask4Button.setEnabled(true);
            startTimerOfTask5Button.setEnabled(true);
            stopTimerButton.setEnabled(true);
        } else if (activeTaskId == 3) {
            startTimerOfTask1Button.setEnabled(true);
            startTimerOfTask2Button.setEnabled(true);
            startTimerOfTask3Button.setEnabled(false);
            startTimerOfTask4Button.setEnabled(true);
            startTimerOfTask5Button.setEnabled(true);
            stopTimerButton.setEnabled(true);
        } else if (activeTaskId == 4) {
            startTimerOfTask1Button.setEnabled(true);
            startTimerOfTask2Button.setEnabled(true);
            startTimerOfTask3Button.setEnabled(true);
            startTimerOfTask4Button.setEnabled(false);
            startTimerOfTask5Button.setEnabled(true);
            stopTimerButton.setEnabled(true);
        } else if (activeTaskId == 5) {
            startTimerOfTask1Button.setEnabled(true);
            startTimerOfTask2Button.setEnabled(true);
            startTimerOfTask3Button.setEnabled(true);
            startTimerOfTask4Button.setEnabled(true);
            startTimerOfTask5Button.setEnabled(false);
            stopTimerButton.setEnabled(true);
        }
    }

    public  void setContents() {

        taskTimerTOList = bOperation.retrieveLastFiveTasks();
        if (taskTimerTOList.size() >= 5) {
            this.taskOneLabel.setText(taskTimerTOList.get(0).getTaskDetails());
            taskOneTimeLeft = timeLimit = taskTimerTOList.get(0).getTimeLimit();
            min = timeLimit / 60;
            sec = timeLimit % 60;
            hr = min / 60;
            if (min >= 60) {
                min = min % 60;
            }
            this.taskOneTimeLeftLabel.setText(hr.toString() + " Hour " + min.toString() + " min " + sec.toString() + "sec");
            startTimerOfTask1Button.setText("Start");
            this.taskTwoLabel.setText(taskTimerTOList.get(1).getTaskDetails());
            taskTwoTimeLeft = timeLimit = taskTimerTOList.get(1).getTimeLimit();
            min = timeLimit / 60;
            sec = timeLimit % 60;
            hr = min / 60;
            if (min >= 60) {
                min = min % 60;
            }
            this.taskTwoTimeLeftLabel.setText(hr.toString() + " Hour " + min.toString() + " min " + sec.toString() + "sec");
            startTimerOfTask2Button.setText("Start");

            this.taskThreeLabel.setText(bOperation.retrieveLastFiveTasks().get(2).getTaskDetails());
            taskThreeTimeLeft = timeLimit = bOperation.retrieveLastFiveTasks().get(2).getTimeLimit();
            min = timeLimit / 60;
            sec = timeLimit % 60;
            hr = min / 60;
            if (min >= 60) {
                min = min % 60;
            }
            this.taskThreeTimeLeftLabel.setText(hr.toString() + " Hour " + min.toString() + " min " + sec.toString() + "sec");
            startTimerOfTask3Button.setText("Start");

            this.taskFourLabel.setText(bOperation.retrieveLastFiveTasks().get(3).getTaskDetails());
            taskFourTimeLeft = timeLimit = bOperation.retrieveLastFiveTasks().get(3).getTimeLimit();
            min = timeLimit / 60;
            sec = timeLimit % 60;
            hr = min / 60;
            if (min >= 60) {
                min = min % 60;
            }
            this.taskFourTimeLeftLabel.setText(hr.toString() + " Hour " + min.toString() + " min " + sec.toString() + "sec");
            startTimerOfTask4Button.setText("Start");

            this.taskFiveLabel.setText(bOperation.retrieveLastFiveTasks().get(4).getTaskDetails());
            taskFiveTimeLeft = timeLimit = bOperation.retrieveLastFiveTasks().get(4).getTimeLimit();
            min = timeLimit / 60;
            sec = timeLimit % 60;
            hr = min / 60;
            if (min >= 60) {
                min = min % 60;
            }
            this.taskFiveTimeLeftLabel.setText(hr.toString() + " Hour " + min.toString() + " min " + sec.toString() + "sec");
            startTimerOfTask5Button.setText("Start");
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

        jLabel1 = new javax.swing.JLabel();
        BackgroundPanel = new javax.swing.JPanel();
        loadTasksButton = new javax.swing.JLabel();
        taskFiveTimeLeftLabel = new javax.swing.JLabel();
        addLabel = new javax.swing.JLabel();
        minimizeLabel = new javax.swing.JLabel();
        closeLabelBtn = new javax.swing.JLabel();
        taskFiveLabel = new javax.swing.JLabel();
        taskTwoLabel = new javax.swing.JLabel();
        taskTwoTimeLeftLabel = new javax.swing.JLabel();
        startTimerOfTask2Button = new javax.swing.JButton();
        taskOneLabel = new javax.swing.JLabel();
        taskOneTimeLeftLabel = new javax.swing.JLabel();
        startTimerOfTask1Button = new javax.swing.JButton();
        taskThreeLabel = new javax.swing.JLabel();
        startTimerOfTask5Button = new javax.swing.JButton();
        taskThreeTimeLeftLabel = new javax.swing.JLabel();
        startTimerOfTask3Button = new javax.swing.JButton();
        taskFourLabel = new javax.swing.JLabel();
        taskFourTimeLeftLabel = new javax.swing.JLabel();
        startTimerOfTask4Button = new javax.swing.JButton();
        clockLabel = new javax.swing.JLabel();
        changeThemeLabel = new javax.swing.JLabel();
        stopTimerButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        backgroundLabel = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        BackgroundPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                BackgroundPanelMouseDragged(evt);
            }
        });
        BackgroundPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BackgroundPanelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                BackgroundPanelMouseReleased(evt);
            }
        });
        BackgroundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        loadTasksButton.setForeground(new java.awt.Color(255, 255, 255));
        loadTasksButton.setText("reload");
        loadTasksButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadTasksButtonMouseClicked(evt);
            }
        });
        BackgroundPanel.add(loadTasksButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, -1, -1));

        taskFiveTimeLeftLabel.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        taskFiveTimeLeftLabel.setForeground(new java.awt.Color(204, 255, 51));
        taskFiveTimeLeftLabel.setText("0h 0m 0s");
        BackgroundPanel.add(taskFiveTimeLeftLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 180, -1));

        addLabel.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        addLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addLabelMouseClicked(evt);
            }
        });
        BackgroundPanel.add(addLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 50));

        minimizeLabel.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        minimizeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minimizeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeLabelMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                minimizeLabelMouseReleased(evt);
            }
        });
        BackgroundPanel.add(minimizeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 30, 30));

        closeLabelBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        closeLabelBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeLabelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLabelBtnMouseClicked(evt);
            }
        });
        BackgroundPanel.add(closeLabelBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(313, 10, 30, 30));

        taskFiveLabel.setFont(new java.awt.Font("Vrinda", 1, 14)); // NOI18N
        taskFiveLabel.setText("Task 5");
        taskFiveLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                taskFiveLabelMouseClicked(evt);
            }
        });
        BackgroundPanel.add(taskFiveLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 210, 20));

        taskTwoLabel.setFont(new java.awt.Font("Vrinda", 1, 14)); // NOI18N
        taskTwoLabel.setText("Task 2");
        taskTwoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                taskTwoLabelMouseClicked(evt);
            }
        });
        BackgroundPanel.add(taskTwoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 210, 20));

        taskTwoTimeLeftLabel.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        taskTwoTimeLeftLabel.setForeground(new java.awt.Color(204, 255, 51));
        taskTwoTimeLeftLabel.setText("0h 0m 0s");
        BackgroundPanel.add(taskTwoTimeLeftLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 180, -1));

        startTimerOfTask2Button.setBackground(new java.awt.Color(255, 255, 153));
        startTimerOfTask2Button.setText("Start");
        startTimerOfTask2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTimerOfTask2ButtonActionPerformed(evt);
            }
        });
        BackgroundPanel.add(startTimerOfTask2Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, 80, -1));

        taskOneLabel.setFont(new java.awt.Font("Vrinda", 1, 14)); // NOI18N
        taskOneLabel.setText("Task 1");
        taskOneLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                taskOneLabelMouseClicked(evt);
            }
        });
        BackgroundPanel.add(taskOneLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 210, 20));

        taskOneTimeLeftLabel.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        taskOneTimeLeftLabel.setForeground(new java.awt.Color(204, 255, 51));
        taskOneTimeLeftLabel.setText("0h 0m 0s");
        BackgroundPanel.add(taskOneTimeLeftLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 180, -1));

        startTimerOfTask1Button.setBackground(new java.awt.Color(255, 255, 153));
        startTimerOfTask1Button.setText("Start");
        startTimerOfTask1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTimerOfTask1ButtonActionPerformed(evt);
            }
        });
        BackgroundPanel.add(startTimerOfTask1Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 80, -1));

        taskThreeLabel.setFont(new java.awt.Font("Vrinda", 1, 14)); // NOI18N
        taskThreeLabel.setText("Task 3");
        taskThreeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                taskThreeLabelMouseClicked(evt);
            }
        });
        BackgroundPanel.add(taskThreeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 210, 20));

        startTimerOfTask5Button.setBackground(new java.awt.Color(255, 255, 153));
        startTimerOfTask5Button.setText("Start");
        startTimerOfTask5Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTimerOfTask5ButtonActionPerformed(evt);
            }
        });
        BackgroundPanel.add(startTimerOfTask5Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, 80, -1));

        taskThreeTimeLeftLabel.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        taskThreeTimeLeftLabel.setForeground(new java.awt.Color(204, 255, 51));
        taskThreeTimeLeftLabel.setText("0h 0m 0s");
        BackgroundPanel.add(taskThreeTimeLeftLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 180, -1));

        startTimerOfTask3Button.setBackground(new java.awt.Color(255, 255, 153));
        startTimerOfTask3Button.setText("Start");
        startTimerOfTask3Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTimerOfTask3ButtonActionPerformed(evt);
            }
        });
        BackgroundPanel.add(startTimerOfTask3Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 260, 80, -1));

        taskFourLabel.setFont(new java.awt.Font("Vrinda", 1, 14)); // NOI18N
        taskFourLabel.setText("Task 4");
        taskFourLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                taskFourLabelMouseClicked(evt);
            }
        });
        BackgroundPanel.add(taskFourLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 210, 20));

        taskFourTimeLeftLabel.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        taskFourTimeLeftLabel.setForeground(new java.awt.Color(204, 255, 51));
        taskFourTimeLeftLabel.setText("0h 0m 0s");
        BackgroundPanel.add(taskFourTimeLeftLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 180, -1));

        startTimerOfTask4Button.setBackground(new java.awt.Color(255, 255, 153));
        startTimerOfTask4Button.setText("Start");
        startTimerOfTask4Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTimerOfTask4ButtonActionPerformed(evt);
            }
        });
        BackgroundPanel.add(startTimerOfTask4Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 310, 80, -1));

        clockLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        clockLabel.setForeground(new java.awt.Color(255, 255, 255));
        clockLabel.setText("loading clock...");
        BackgroundPanel.add(clockLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 17, 180, 20));

        changeThemeLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        changeThemeLabel.setForeground(new java.awt.Color(255, 255, 255));
        changeThemeLabel.setText("Windows");
        changeThemeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                changeThemeLabelMouseReleased(evt);
            }
        });
        BackgroundPanel.add(changeThemeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 50, 20));

        stopTimerButton.setBackground(new java.awt.Color(0, 153, 51));
        stopTimerButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        stopTimerButton.setText("Stop Now");
        stopTimerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stopTimerButtonMouseClicked(evt);
            }
        });
        stopTimerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopTimerButtonActionPerformed(evt);
            }
        });
        BackgroundPanel.add(stopTimerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, 80, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 255, 204));
        jLabel2.setText("Developed by : Md. Arifur Rahman, Email: arif.cse.ru@gmail.com");
        BackgroundPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 340, -1));

        backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/timer/pictures/BigWindowBackground_linux.gif"))); // NOI18N
        backgroundLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        BackgroundPanel.add(backgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 450));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BackgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BackgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackgroundPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackgroundPanelMousePressed
        // TODO add your handling code here:
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_BackgroundPanelMousePressed

    private void BackgroundPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackgroundPanelMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_BackgroundPanelMouseReleased

    private void BackgroundPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackgroundPanelMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_BackgroundPanelMouseDragged

    private void minimizeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeLabelMouseClicked

        if (activeTaskId != 0 && TaskTimer.isTimerStop == false) {
            SmallTimerFrame smallTimerFrame = new SmallTimerFrame();
            smallTimerFrame.setVisible(true);
            TaskTimer.activeTaskTimerWindow = "Minimum";
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Please Start Any task");
        }

    }//GEN-LAST:event_minimizeLabelMouseClicked

    private void addLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addLabelMouseClicked
        // TODO add your handling code here:
        TaskTimer.activeTaskTimerWindow = "Maximum";
        AddTaskTimer addTaskTimer = new AddTaskTimer();
        addTaskTimer.setVisible(true);
        //this.dispose();
    }//GEN-LAST:event_addLabelMouseClicked

    private void startTimerOfTask3ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTimerOfTask3ButtonActionPerformed
        // TODO add your handling code here:
        if (!timer.isRunning()) {
            timer.start();
            TaskTimer.isTimerStop = false;
        }
        startTimerOfTask1Button.setEnabled(true);
        startTimerOfTask2Button.setEnabled(true);
        startTimerOfTask3Button.setEnabled(false);
        startTimerOfTask4Button.setEnabled(true);
        startTimerOfTask5Button.setEnabled(true);
        stopTimerButton.setEnabled(true);
        activeTaskId = 3;
    }//GEN-LAST:event_startTimerOfTask3ButtonActionPerformed

    private void startTimerOfTask5ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTimerOfTask5ButtonActionPerformed
        // TODO add your handling code here:
        if (startTimerOfTask1Button.getText().equalsIgnoreCase("done")) {
            System.out.println("Ok");
            //bOperation.deleteTask(taskTimerTOList.get(0).getId());
        }
        System.out.println("Not Ok");
        if (!timer.isRunning()) {
            timer.start();
            TaskTimer.isTimerStop = false;
        }
        startTimerOfTask1Button.setEnabled(true);
        startTimerOfTask2Button.setEnabled(true);
        startTimerOfTask3Button.setEnabled(true);
        startTimerOfTask4Button.setEnabled(true);
        startTimerOfTask5Button.setEnabled(false);
        stopTimerButton.setEnabled(true);
        activeTaskId = 5;
    }//GEN-LAST:event_startTimerOfTask5ButtonActionPerformed

    private void startTimerOfTask1ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTimerOfTask1ButtonActionPerformed
        // TODO add your handling code here:
        /*if (startTimerOfTask1Button.getText().equalsIgnoreCase("done")) {
            System.out.println("done clicked!");
            bOperation.deleteTask(bOperation.retrieveLastFiveTasks().get(0).getId());
            this.setContents();
        }else{
        
        }*/
        if (!timer.isRunning()) {
            timer.start();
            TaskTimer.isTimerStop = false;
        }
        startTimerOfTask1Button.setEnabled(false);
        startTimerOfTask2Button.setEnabled(true);
        startTimerOfTask3Button.setEnabled(true);
        startTimerOfTask4Button.setEnabled(true);
        startTimerOfTask5Button.setEnabled(true);
        stopTimerButton.setEnabled(true);
        activeTaskId = 1;

    }//GEN-LAST:event_startTimerOfTask1ButtonActionPerformed

    private void startTimerOfTask2ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTimerOfTask2ButtonActionPerformed
        // TODO add your handling code here:
        if (!timer.isRunning()) {
            timer.start();
            TaskTimer.isTimerStop = false;
        }
        startTimerOfTask1Button.setEnabled(true);
        startTimerOfTask2Button.setEnabled(false);
        startTimerOfTask3Button.setEnabled(true);
        startTimerOfTask4Button.setEnabled(true);
        startTimerOfTask5Button.setEnabled(true);
        stopTimerButton.setEnabled(true);
        activeTaskId = 2;
    }//GEN-LAST:event_startTimerOfTask2ButtonActionPerformed

    private void startTimerOfTask4ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTimerOfTask4ButtonActionPerformed
        // TODO add your handling code here:
        if (!timer.isRunning()) {
            timer.start();
            TaskTimer.isTimerStop = false;
        }
        startTimerOfTask1Button.setEnabled(true);
        startTimerOfTask2Button.setEnabled(true);
        startTimerOfTask3Button.setEnabled(true);
        startTimerOfTask4Button.setEnabled(false);
        startTimerOfTask5Button.setEnabled(true);
        stopTimerButton.setEnabled(true);
        activeTaskId = 4;
    }//GEN-LAST:event_startTimerOfTask4ButtonActionPerformed

    private void stopTimerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopTimerButtonMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_stopTimerButtonMouseClicked

    private void stopTimerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopTimerButtonActionPerformed
        // TODO add your handling code here:
        if (timer.isRunning()) {
            timer.stop();
            TaskTimer.isTimerStop = true;
        }
        startTimerOfTask1Button.setEnabled(true);
        startTimerOfTask2Button.setEnabled(true);
        startTimerOfTask3Button.setEnabled(true);
        startTimerOfTask4Button.setEnabled(true);
        startTimerOfTask5Button.setEnabled(true);
        activeTaskId = 0;
        stopTimerButton.setEnabled(false);
    }//GEN-LAST:event_stopTimerButtonActionPerformed

    private void changeThemeLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeThemeLabelMouseReleased
        // TODO add your handling code here:
        if (TaskTimer.toBeActivatedTheme.equalsIgnoreCase("windows")) {
            backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/timer/pictures/BigWindowBackground.gif"))); // NOI18N
            TaskTimer.toBeActivatedTheme = "Linux";
            this.changeThemeLabel.setText(TaskTimer.toBeActivatedTheme);
            this.taskOneTimeLeftLabel.setForeground(Color.white);
            this.taskTwoTimeLeftLabel.setForeground(Color.white);
            this.taskThreeTimeLeftLabel.setForeground(Color.white);
            this.taskFourTimeLeftLabel.setForeground(Color.white);
            this.taskFiveTimeLeftLabel.setForeground(Color.white);
        } else {
            backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/timer/pictures/BigWindowBackground_linux.gif"))); // NOI18N
            TaskTimer.toBeActivatedTheme = "Windows";
            this.changeThemeLabel.setText(TaskTimer.toBeActivatedTheme);
            this.taskOneTimeLeftLabel.setForeground(Color.yellow);
            this.taskTwoTimeLeftLabel.setForeground(Color.yellow);
            this.taskThreeTimeLeftLabel.setForeground(Color.yellow);
            this.taskFourTimeLeftLabel.setForeground(Color.yellow);
            this.taskFiveTimeLeftLabel.setForeground(Color.yellow);
        }

    }//GEN-LAST:event_changeThemeLabelMouseReleased

    private void minimizeLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeLabelMouseReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_minimizeLabelMouseReleased

    private void taskOneLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taskOneLabelMouseClicked
        bOperation.deleteTask(taskTimerTOList.get(0).getId());
        this.setContents();
    }//GEN-LAST:event_taskOneLabelMouseClicked

    private void taskTwoLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taskTwoLabelMouseClicked
        bOperation.deleteTask(taskTimerTOList.get(1).getId());
        this.setContents();
    }//GEN-LAST:event_taskTwoLabelMouseClicked

    private void taskThreeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taskThreeLabelMouseClicked
        bOperation.deleteTask(taskTimerTOList.get(2).getId());
        this.setContents();
    }//GEN-LAST:event_taskThreeLabelMouseClicked

    private void taskFourLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taskFourLabelMouseClicked
        bOperation.deleteTask(taskTimerTOList.get(3).getId());
        this.setContents();
    }//GEN-LAST:event_taskFourLabelMouseClicked

    private void taskFiveLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taskFiveLabelMouseClicked
        bOperation.deleteTask(taskTimerTOList.get(4).getId());
        this.setContents();
    }//GEN-LAST:event_taskFiveLabelMouseClicked

    private void loadTasksButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loadTasksButtonMouseClicked
        this.setContents();
    }//GEN-LAST:event_loadTasksButtonMouseClicked

    private void closeLabelBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelBtnMouseClicked
        // TODO add your handling code here:
        System.out.println("Exit in progress..");
        System.exit(0);
        this.dispose();
    }//GEN-LAST:event_closeLabelBtnMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    	System.out.println("Main");
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TaskTimer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TaskTimer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TaskTimer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TaskTimer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TaskTimer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackgroundPanel;
    private javax.swing.JLabel addLabel;
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JLabel changeThemeLabel;
    private javax.swing.JLabel clockLabel;
    private javax.swing.JLabel closeLabelBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel loadTasksButton;
    private javax.swing.JLabel minimizeLabel;
    private javax.swing.JButton startTimerOfTask1Button;
    private javax.swing.JButton startTimerOfTask2Button;
    private javax.swing.JButton startTimerOfTask3Button;
    private javax.swing.JButton startTimerOfTask4Button;
    private javax.swing.JButton startTimerOfTask5Button;
    private javax.swing.JButton stopTimerButton;
    private javax.swing.JLabel taskFiveLabel;
    private javax.swing.JLabel taskFiveTimeLeftLabel;
    private javax.swing.JLabel taskFourLabel;
    private javax.swing.JLabel taskFourTimeLeftLabel;
    private javax.swing.JLabel taskOneLabel;
    private javax.swing.JLabel taskOneTimeLeftLabel;
    private javax.swing.JLabel taskThreeLabel;
    private javax.swing.JLabel taskThreeTimeLeftLabel;
    private javax.swing.JLabel taskTwoLabel;
    private javax.swing.JLabel taskTwoTimeLeftLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {

        if (taskOneTimeLeft <= 0) {
            taskOneTimeLeft = 0;
            //bOperation.deleteTask(taskTimerTOList.get(0).getId());
            //this.setContents();
            //activeTaskId = 99;
            //startTimerOfTask1Button.setEnabled(true);
            startTimerOfTask1Button.setText("Done");
            // this.updateTaskTimerRecords();

        }
        if (taskTwoTimeLeft <= 0) {
            taskTwoTimeLeft = 0;
            //bOperation.deleteTask(taskTimerTOList.get(1).getId());
            //this.setContents();
            //activeTaskId = 99;
            //startTimerOfTask2Button.setEnabled(true);
            startTimerOfTask2Button.setText("Done");
            // this.updateTaskTimerRecords();

        }
        if (taskThreeTimeLeft <= 0) {
            taskThreeTimeLeft = 0;
            //bOperation.deleteTask(taskTimerTOList.get(2).getId());
            //this.setContents();
            //activeTaskId = 99;
            //startTimerOfTask3Button.setEnabled(true);
            startTimerOfTask3Button.setText("Done");
            // this.updateTaskTimerRecords();

        }
        if (taskFourTimeLeft <= 0) {
            taskFourTimeLeft = 0;
            //bOperation.deleteTask(taskTimerTOList.get(3).getId());
            //this.setContents();
            //activeTaskId = 99;
            //startTimerOfTask4Button.setEnabled(true);
            startTimerOfTask4Button.setText("Done");
            // this.updateTaskTimerRecords();

        }
        if (taskFiveTimeLeft <= 0) {
            taskFiveTimeLeft = 0;
            //bOperation.deleteTask(taskTimerTOList.get(4).getId());
            //this.setContents();
            // activeTaskId = 99;
            //startTimerOfTask5Button.setEnabled(true);
            startTimerOfTask5Button.setText("Done");
            // this.updateTaskTimerRecords();

        }

        counter++;
        if (counter >= 20) {
            this.counter = 0;
            // this.update
            this.updateTaskTimerRecords();
            this.setContents();
            //System.out.println(this.taskOneTimeLeft);  
        }
        if (!this.startTimerOfTask1Button.isEnabled() || activeTaskId == 1) {
            this.taskOneTimeLeft--;
            min = taskOneTimeLeft / 60;
            sec = taskOneTimeLeft % 60;
            hr = min / 60;
            if (min >= 60) {
                min = min % 60;
            }
            this.taskOneTimeLeftLabel.setText(hr.toString() + " Hour " + min.toString() + " min " + sec.toString() + "sec");
        } else if (!this.startTimerOfTask2Button.isEnabled() || activeTaskId == 2) {
            this.taskTwoTimeLeft--;
            min = taskTwoTimeLeft / 60;
            sec = taskTwoTimeLeft % 60;
            hr = min / 60;
            if (min >= 60) {
                min = min % 60;
            }
            this.taskTwoTimeLeftLabel.setText(hr.toString() + " Hour " + min.toString() + " min " + sec.toString() + "sec");
        } else if (!this.startTimerOfTask3Button.isEnabled() || activeTaskId == 3) {
            this.taskThreeTimeLeft--;
            min = taskThreeTimeLeft / 60;
            sec = taskThreeTimeLeft % 60;
            hr = min / 60;
            if (min >= 60) {
                min = min % 60;
            }
            this.taskThreeTimeLeftLabel.setText(hr.toString() + " Hour " + min.toString() + " min " + sec.toString() + "sec");
        } else if (!this.startTimerOfTask4Button.isEnabled() || activeTaskId == 4) {
            this.taskFourTimeLeft--;
            min = taskFourTimeLeft / 60;
            sec = taskFourTimeLeft % 60;
            hr = min / 60;
            if (min >= 60) {
                min = min % 60;
            }
            this.taskFourTimeLeftLabel.setText(hr.toString() + " Hour " + min.toString() + " min " + sec.toString() + "sec");
        } else if (!this.startTimerOfTask5Button.isEnabled() || activeTaskId == 5) {
            this.taskFiveTimeLeft--;
            min = taskFiveTimeLeft / 60;
            sec = taskFiveTimeLeft % 60;
            hr = min / 60;
            if (min >= 60) {
                min = min % 60;
            }
            this.taskFiveTimeLeftLabel.setText(hr.toString() + " Hour " + min.toString() + " min " + sec.toString() + "sec");
        }
        Calendar today = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy : h-m-s");

        this.clockLabel.setText(dateFormat.format(today.getTime()));
        /*if (timerToClose <= 0) {
            System.exit(0);
        }*/

    }

    private void updateTaskTimerRecords() {
        if (taskTimerTOList != null) {
            taskTimerTOList.get(0).setTimeLimit(taskOneTimeLeft);
            taskTimerTOList.get(1).setTimeLimit(taskTwoTimeLeft);
            taskTimerTOList.get(2).setTimeLimit(taskThreeTimeLeft);
            taskTimerTOList.get(3).setTimeLimit(taskFourTimeLeft);
            taskTimerTOList.get(4).setTimeLimit(taskFiveTimeLeft);
            bOperation.updateTasks(taskTimerTOList);
        }

    }
}
