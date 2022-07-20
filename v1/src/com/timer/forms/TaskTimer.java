// 
// Decompiled by Procyon v0.5.36
// 
package com.timer.forms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.EventQueue;
import javax.swing.UnsupportedLookAndFeelException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Component;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.timer.tos.TaskTimerTO;
import java.util.List;
import com.timer.db.TaskTimerDBOperation;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TaskTimer extends JFrame implements ActionListener {

    public static Integer activeTaskId;
    public static boolean isTimerStop;
    int timerToClose;
    int counter;
    int xMouse;
    int yMouse;
    Integer taskOneTimeLeft;
    Integer taskTwoTimeLeft;
    Integer taskThreeTimeLeft;
    Integer taskFourTimeLeft;
    Integer taskFiveTimeLeft;
    Timer timer;
    Integer timeLimit;
    Integer min;
    Integer sec;
    Integer hr;
    TaskTimerDBOperation bOperation;
    List<TaskTimerTO> taskTimerTOList;
    private JPanel BackgroundPanel;
    private JLabel addLabel;
    private JLabel backgroundLabel;
    private JLabel clockLabel;
    private JLabel closeLabel;
    private JLabel jLabel1;
    private JLabel minimizeLabel;
    private JButton startTimerOfTask1Button;
    private JButton startTimerOfTask2Button;
    private JButton startTimerOfTask3Button;
    private JButton startTimerOfTask4Button;
    private JButton startTimerOfTask5Button;
    private JButton stopTimerButton;
    private JLabel taskFiveLabel;
    private JLabel taskFiveTimeLeftLabel;
    private JLabel taskFourLabel;
    private JLabel taskFourTimeLeftLabel;
    private JLabel taskOneLabel;
    private JLabel taskOneTimeLeftLabel;
    private JLabel taskThreeLabel;
    private JLabel taskThreeTimeLeftLabel;
    private JLabel taskTwoLabel;
    private JLabel taskTwoTimeLeftLabel;

    static {
        TaskTimer.activeTaskId = 0;
        TaskTimer.isTimerStop = true;
    }

    public TaskTimer() {
        this.timerToClose = 5;
        this.counter = 0;
        this.timer = new Timer(1000, this);
        this.bOperation = new TaskTimerDBOperation();
        this.initComponents();
        this.setSize(350, 443);
        this.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getSize().getWidth() - 10.0), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - this.getSize().getHeight() - 40.0));
        this.timer.start();
        TaskTimer.isTimerStop = false;
        this.checkEnabledTask();
        this.setContents();
        this.startTimerOfTask1Button.setEnabled(true);
        this.startTimerOfTask2Button.setEnabled(true);
        this.startTimerOfTask3Button.setEnabled(true);
        this.startTimerOfTask4Button.setEnabled(true);
        this.startTimerOfTask5Button.setEnabled(true);
        this.stopTimerButton.setEnabled(false);
    }

    public void checkEnabledTask() {
        if (TaskTimer.activeTaskId == 1) {
            this.startTimerOfTask1Button.setEnabled(false);
            this.startTimerOfTask2Button.setEnabled(true);
            this.startTimerOfTask3Button.setEnabled(true);
            this.startTimerOfTask4Button.setEnabled(true);
            this.startTimerOfTask5Button.setEnabled(true);
            this.stopTimerButton.setEnabled(true);
        } else if (TaskTimer.activeTaskId == 2) {
            this.startTimerOfTask1Button.setEnabled(true);
            this.startTimerOfTask2Button.setEnabled(false);
            this.startTimerOfTask3Button.setEnabled(true);
            this.startTimerOfTask4Button.setEnabled(true);
            this.startTimerOfTask5Button.setEnabled(true);
            this.stopTimerButton.setEnabled(true);
        } else if (TaskTimer.activeTaskId == 3) {
            this.startTimerOfTask1Button.setEnabled(true);
            this.startTimerOfTask2Button.setEnabled(true);
            this.startTimerOfTask3Button.setEnabled(false);
            this.startTimerOfTask4Button.setEnabled(true);
            this.startTimerOfTask5Button.setEnabled(true);
            this.stopTimerButton.setEnabled(true);
        } else if (TaskTimer.activeTaskId == 4) {
            this.startTimerOfTask1Button.setEnabled(true);
            this.startTimerOfTask2Button.setEnabled(true);
            this.startTimerOfTask3Button.setEnabled(true);
            this.startTimerOfTask4Button.setEnabled(false);
            this.startTimerOfTask5Button.setEnabled(true);
            this.stopTimerButton.setEnabled(true);
        } else if (TaskTimer.activeTaskId == 5) {
            this.startTimerOfTask1Button.setEnabled(true);
            this.startTimerOfTask2Button.setEnabled(true);
            this.startTimerOfTask3Button.setEnabled(true);
            this.startTimerOfTask4Button.setEnabled(true);
            this.startTimerOfTask5Button.setEnabled(false);
            this.stopTimerButton.setEnabled(true);
        }
    }

    private void addDefaultTasks() {
        try {
            System.out.println("Adding Default Tasks.");
            final TaskTimerDBOperation dBOperation = new TaskTimerDBOperation();
            for (int i = 0; i < 5; i++) {
                TaskTimerTO taskTimerTO = new TaskTimerTO();
                taskTimerTO.setTaskDetails("Default Task..");
                taskTimerTO.setTimeLimit(100);
                taskTimerTO.setTimeLeft(100);
                taskTimerTO.setStatus(0);
                dBOperation.createTask(taskTimerTO);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, String.valueOf(e.getMessage()) + "Please input like : 0h 0m 50s");
        }
    }

    public void setContents() {
        this.taskTimerTOList = this.bOperation.retrieveLastFiveTasks();
        System.out.println("taskTimerTOList: " + taskTimerTOList.size());
        if (taskTimerTOList.size() < 5) {
            addDefaultTasks();
            this.taskTimerTOList = this.bOperation.retrieveLastFiveTasks();
        }
        this.taskOneLabel.setText(this.taskTimerTOList.get(0).getTaskDetails());
        final Integer timeLimit = this.taskTimerTOList.get(0).getTimeLimit();
        this.timeLimit = timeLimit;
        this.taskOneTimeLeft = timeLimit;
        this.min = this.timeLimit / 60;
        this.sec = this.timeLimit % 60;
        this.hr = this.min / 60;
        if (this.min >= 60) {
            this.min %= 60;
        }
        this.taskOneTimeLeftLabel.setText(String.valueOf(this.hr.toString()) + " Hour " + this.min.toString() + " min " + this.sec.toString() + "sec");
        this.taskTwoLabel.setText(this.taskTimerTOList.get(1).getTaskDetails());
        final Integer timeLimit2 = this.taskTimerTOList.get(1).getTimeLimit();
        this.timeLimit = timeLimit2;
        this.taskTwoTimeLeft = timeLimit2;
        this.min = this.timeLimit / 60;
        this.sec = this.timeLimit % 60;
        this.hr = this.min / 60;
        if (this.min >= 60) {
            this.min %= 60;
        }
        this.taskTwoTimeLeftLabel.setText(String.valueOf(this.hr.toString()) + " Hour " + this.min.toString() + " min " + this.sec.toString() + "sec");
        this.taskThreeLabel.setText(this.bOperation.retrieveLastFiveTasks().get(2).getTaskDetails());
        final Integer timeLimit3 = this.bOperation.retrieveLastFiveTasks().get(2).getTimeLimit();
        this.timeLimit = timeLimit3;
        this.taskThreeTimeLeft = timeLimit3;
        this.min = this.timeLimit / 60;
        this.sec = this.timeLimit % 60;
        this.hr = this.min / 60;
        if (this.min >= 60) {
            this.min %= 60;
        }
        this.taskThreeTimeLeftLabel.setText(String.valueOf(this.hr.toString()) + " Hour " + this.min.toString() + " min " + this.sec.toString() + "sec");
        this.taskFourLabel.setText(this.bOperation.retrieveLastFiveTasks().get(3).getTaskDetails());
        final Integer timeLimit4 = this.bOperation.retrieveLastFiveTasks().get(3).getTimeLimit();
        this.timeLimit = timeLimit4;
        this.taskFourTimeLeft = timeLimit4;
        this.min = this.timeLimit / 60;
        this.sec = this.timeLimit % 60;
        this.hr = this.min / 60;
        if (this.min >= 60) {
            this.min %= 60;
        }
        this.taskFourTimeLeftLabel.setText(String.valueOf(this.hr.toString()) + " Hour " + this.min.toString() + " min " + this.sec.toString() + "sec");
        this.taskFiveLabel.setText(this.bOperation.retrieveLastFiveTasks().get(4).getTaskDetails());
        final Integer timeLimit5 = this.bOperation.retrieveLastFiveTasks().get(4).getTimeLimit();
        this.timeLimit = timeLimit5;
        this.taskFiveTimeLeft = timeLimit5;
        this.min = this.timeLimit / 60;
        this.sec = this.timeLimit % 60;
        this.hr = this.min / 60;
        if (this.min >= 60) {
            this.min %= 60;
        }
        this.taskFiveTimeLeftLabel.setText(String.valueOf(this.hr.toString()) + " Hour " + this.min.toString() + " min " + this.sec.toString() + "sec");
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.BackgroundPanel = new JPanel();
        this.taskFiveTimeLeftLabel = new JLabel();
        this.addLabel = new JLabel();
        this.closeLabel = new JLabel();
        this.minimizeLabel = new JLabel();
        this.taskFiveLabel = new JLabel();
        this.taskTwoLabel = new JLabel();
        this.taskTwoTimeLeftLabel = new JLabel();
        this.startTimerOfTask2Button = new JButton();
        this.taskOneLabel = new JLabel();
        this.taskOneTimeLeftLabel = new JLabel();
        this.startTimerOfTask1Button = new JButton();
        this.taskThreeLabel = new JLabel();
        this.startTimerOfTask5Button = new JButton();
        this.taskThreeTimeLeftLabel = new JLabel();
        this.startTimerOfTask3Button = new JButton();
        this.taskFourLabel = new JLabel();
        this.taskFourTimeLeftLabel = new JLabel();
        this.startTimerOfTask4Button = new JButton();
        this.stopTimerButton = new JButton();
        this.clockLabel = new JLabel();
        this.backgroundLabel = new JLabel();
        this.jLabel1.setText("jLabel1");
        this.setDefaultCloseOperation(3);
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.setResizable(false);
        this.BackgroundPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(final MouseEvent evt) {
                TaskTimer.this.BackgroundPanelMouseDragged(evt);
            }
        });
        this.BackgroundPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent evt) {
                TaskTimer.this.BackgroundPanelMousePressed(evt);
            }

            @Override
            public void mouseReleased(final MouseEvent evt) {
                TaskTimer.this.BackgroundPanelMouseReleased(evt);
            }
        });
        this.BackgroundPanel.setLayout(new AbsoluteLayout());
        this.taskFiveTimeLeftLabel.setFont(new Font("Tahoma", 1, 18));
        this.taskFiveTimeLeftLabel.setForeground(new Color(255, 0, 51));
        this.taskFiveTimeLeftLabel.setText("Total Time left");
        this.BackgroundPanel.add(this.taskFiveTimeLeftLabel, new AbsoluteConstraints(60, 380, -1, -1));
        this.addLabel.setFont(new Font("Tahoma", 1, 48));
        this.addLabel.setCursor(new Cursor(12));
        this.addLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                TaskTimer.this.addLabelMouseClicked(evt);
            }
        });
        this.taskOneLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                TaskTimer.this.deleteTaskOne();
            }
        });
        this.taskTwoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                TaskTimer.this.deleteTaskTwo();
            }
        });
        this.taskThreeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                TaskTimer.this.deleteTaskThree();
            }
        });
        this.taskFourLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                TaskTimer.this.deleteTaskFour();
            }
        });
        this.taskFiveLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                TaskTimer.this.deleteTaskFive();
            }
        });
        this.BackgroundPanel.add(this.addLabel, new AbsoluteConstraints(0, 0, 60, 50));
        this.closeLabel.setFont(new Font("Tahoma", 1, 24));
        this.closeLabel.setCursor(new Cursor(12));
        this.closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                TaskTimer.this.closeLabelMouseClicked(evt);
            }
        });
        this.BackgroundPanel.add(this.closeLabel, new AbsoluteConstraints(313, 0, 40, 40));
        this.minimizeLabel.setFont(new Font("Tahoma", 1, 48));
        this.minimizeLabel.setCursor(new Cursor(12));
        this.minimizeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                TaskTimer.this.minimizeLabelMouseClicked(evt);
            }
        });
        this.BackgroundPanel.add(this.minimizeLabel, new AbsoluteConstraints(270, 0, 40, 30));
        this.taskFiveLabel.setFont(new Font("Vrinda", 1, 18));
        this.taskFiveLabel.setText("Task 5");
        this.BackgroundPanel.add(this.taskFiveLabel, new AbsoluteConstraints(10, 360, 330, 20));
        this.taskTwoLabel.setFont(new Font("Vrinda", 1, 18));
        this.taskTwoLabel.setText("Task 2");
        this.BackgroundPanel.add(this.taskTwoLabel, new AbsoluteConstraints(10, 180, 330, 20));
        this.taskTwoTimeLeftLabel.setFont(new Font("Tahoma", 1, 18));
        this.taskTwoTimeLeftLabel.setForeground(new Color(255, 0, 51));
        this.taskTwoTimeLeftLabel.setText("Total Time left");
        this.BackgroundPanel.add(this.taskTwoTimeLeftLabel, new AbsoluteConstraints(60, 210, -1, -1));
        this.startTimerOfTask2Button.setText("Start");
        this.startTimerOfTask2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                TaskTimer.this.startTimerOfTask2ButtonActionPerformed(evt);
            }
        });
        this.BackgroundPanel.add(this.startTimerOfTask2Button, new AbsoluteConstraints(280, 200, -1, -1));
        this.taskOneLabel.setFont(new Font("Vrinda", 1, 18));
        this.taskOneLabel.setText("Task 1");
        this.BackgroundPanel.add(this.taskOneLabel, new AbsoluteConstraints(10, 120, 330, 20));
        this.taskOneTimeLeftLabel.setFont(new Font("Tahoma", 1, 18));
        this.taskOneTimeLeftLabel.setForeground(new Color(255, 0, 51));
        this.taskOneTimeLeftLabel.setText("Total Time left");
        this.BackgroundPanel.add(this.taskOneTimeLeftLabel, new AbsoluteConstraints(60, 150, -1, -1));
        this.startTimerOfTask1Button.setText("Start");
        this.startTimerOfTask1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                TaskTimer.this.startTimerOfTask1ButtonActionPerformed(evt);
            }
        });
        this.BackgroundPanel.add(this.startTimerOfTask1Button, new AbsoluteConstraints(280, 140, -1, -1));
        this.taskThreeLabel.setFont(new Font("Vrinda", 1, 18));
        this.taskThreeLabel.setText("Task 3");
        this.BackgroundPanel.add(this.taskThreeLabel, new AbsoluteConstraints(10, 240, 330, 20));
        this.startTimerOfTask5Button.setText("Start");
        this.startTimerOfTask5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                TaskTimer.this.startTimerOfTask5ButtonActionPerformed(evt);
            }
        });
        this.BackgroundPanel.add(this.startTimerOfTask5Button, new AbsoluteConstraints(280, 380, -1, -1));
        this.taskThreeTimeLeftLabel.setFont(new Font("Tahoma", 1, 18));
        this.taskThreeTimeLeftLabel.setForeground(new Color(255, 0, 51));
        this.taskThreeTimeLeftLabel.setText("Total Time left");
        this.BackgroundPanel.add(this.taskThreeTimeLeftLabel, new AbsoluteConstraints(60, 270, -1, -1));
        this.startTimerOfTask3Button.setText("Start");
        this.startTimerOfTask3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                TaskTimer.this.startTimerOfTask3ButtonActionPerformed(evt);
            }
        });
        this.BackgroundPanel.add(this.startTimerOfTask3Button, new AbsoluteConstraints(280, 260, -1, -1));
        this.taskFourLabel.setFont(new Font("Vrinda", 1, 18));
        this.taskFourLabel.setText("Task 4");
        this.BackgroundPanel.add(this.taskFourLabel, new AbsoluteConstraints(10, 300, 330, 20));
        this.taskFourTimeLeftLabel.setFont(new Font("Tahoma", 1, 18));
        this.taskFourTimeLeftLabel.setForeground(new Color(255, 0, 51));
        this.taskFourTimeLeftLabel.setText("Total Time left");
        this.BackgroundPanel.add(this.taskFourTimeLeftLabel, new AbsoluteConstraints(60, 320, -1, -1));
        this.startTimerOfTask4Button.setText("Start");
        this.startTimerOfTask4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                TaskTimer.this.startTimerOfTask4ButtonActionPerformed(evt);
            }
        });
        this.BackgroundPanel.add(this.startTimerOfTask4Button, new AbsoluteConstraints(280, 320, -1, -1));
        this.stopTimerButton.setText("Stop Timer");
        this.stopTimerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                TaskTimer.this.stopTimerButtonMouseClicked(evt);
            }
        });
        this.stopTimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                TaskTimer.this.stopTimerButtonActionPerformed(evt);
            }
        });
        this.BackgroundPanel.add(this.stopTimerButton, new AbsoluteConstraints(10, 410, 330, -1));
        this.clockLabel.setFont(new Font("Tahoma", 1, 14));
        this.clockLabel.setForeground(new Color(255, 255, 255));
        this.clockLabel.setText("testing.....");
        this.BackgroundPanel.add(this.clockLabel, new AbsoluteConstraints(30, 0, 180, -1));
        this.backgroundLabel.setIcon(new ImageIcon(this.getClass().getResource("/com/timer/pictures/BigWindowBackground.gif")));
        this.backgroundLabel.setCursor(new Cursor(13));
        this.BackgroundPanel.add(this.backgroundLabel, new AbsoluteConstraints(0, -20, 350, 480));
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.BackgroundPanel, GroupLayout.Alignment.TRAILING, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.BackgroundPanel, -2, -1, -2).addGap(0, 11, 32767)));
        this.pack();
    }

    private void closeLabelMouseClicked(final MouseEvent evt) {
        System.exit(0);
    }

    private void BackgroundPanelMousePressed(final MouseEvent evt) {
        this.xMouse = evt.getX();
        this.yMouse = evt.getY();
    }

    private void BackgroundPanelMouseReleased(final MouseEvent evt) {
    }

    private void BackgroundPanelMouseDragged(final MouseEvent evt) {
        final int x = evt.getXOnScreen();
        final int y = evt.getYOnScreen();
        this.setLocation(x - this.xMouse, y - this.yMouse);
    }

    private void minimizeLabelMouseClicked(final MouseEvent evt) {
        final SmallTimerFrame smallTimerFrame = new SmallTimerFrame();
        smallTimerFrame.setVisible(true);
        this.dispose();
    }

    private void addLabelMouseClicked(final MouseEvent evt) {
        final AddTaskTimer addTaskTimer = new AddTaskTimer();
        addTaskTimer.setVisible(true);
        this.dispose();
    }

    private void deleteTaskOne() {
        TaskTimerTO taskTimerTO = taskTimerTOList.get(0);
        System.out.println("Task Id: " + taskTimerTO.getId());
        this.bOperation.deleteTask(taskTimerTO.getId());
        setContents();
    }

    private void deleteTaskTwo() {
        TaskTimerTO taskTimerTO = taskTimerTOList.get(1);
        System.out.println("Task Id: " + taskTimerTO.getId());
        this.bOperation.deleteTask(taskTimerTO.getId());
        setContents();
    }

    private void deleteTaskThree() {
        TaskTimerTO taskTimerTO = taskTimerTOList.get(2);
        System.out.println("Task Id: " + taskTimerTO.getId());
        this.bOperation.deleteTask(taskTimerTO.getId());
        setContents();
    }

    private void deleteTaskFour() {
        TaskTimerTO taskTimerTO = taskTimerTOList.get(3);
        System.out.println("Task Id: " + taskTimerTO.getId());
        this.bOperation.deleteTask(taskTimerTO.getId());
        setContents();
    }

    private void deleteTaskFive() {
        TaskTimerTO taskTimerTO = taskTimerTOList.get(4);
        System.out.println("Task Id: " + taskTimerTO.getId());
        this.bOperation.deleteTask(taskTimerTO.getId());
        setContents();
    }

    private void startTimerOfTask3ButtonActionPerformed(final ActionEvent evt) {
        if (!this.timer.isRunning()) {
            this.timer.start();
            TaskTimer.isTimerStop = false;
        }
        this.startTimerOfTask1Button.setEnabled(true);
        this.startTimerOfTask2Button.setEnabled(true);
        this.startTimerOfTask3Button.setEnabled(false);
        this.startTimerOfTask4Button.setEnabled(true);
        this.startTimerOfTask5Button.setEnabled(true);
        this.stopTimerButton.setEnabled(true);
        TaskTimer.activeTaskId = 3;
    }

    private void startTimerOfTask5ButtonActionPerformed(final ActionEvent evt) {
        if (this.startTimerOfTask5Button.getText().equalsIgnoreCase("done")) {
            System.out.println("Ok");
        }
        System.out.println("Not Ok");
        if (!this.timer.isRunning()) {
            this.timer.start();
            TaskTimer.isTimerStop = false;
        }
        this.startTimerOfTask1Button.setEnabled(true);
        this.startTimerOfTask2Button.setEnabled(true);
        this.startTimerOfTask3Button.setEnabled(true);
        this.startTimerOfTask4Button.setEnabled(true);
        this.startTimerOfTask5Button.setEnabled(false);
        this.stopTimerButton.setEnabled(true);
        TaskTimer.activeTaskId = 5;
    }

    private void startTimerOfTask1ButtonActionPerformed(final ActionEvent evt) {
        TaskTimerTO taskTimerTO = this.taskTimerTOList.get(0);
        System.out.println("Time Left: " + taskTimerTO.getTimeLimit());

        if (taskTimerTO.getTimeLimit() > 0 && !this.timer.isRunning()) {
            this.timer.start();
            TaskTimer.isTimerStop = false;
        }
        this.startTimerOfTask1Button.setEnabled(false);
        this.startTimerOfTask2Button.setEnabled(true);
        this.startTimerOfTask3Button.setEnabled(true);
        this.startTimerOfTask4Button.setEnabled(true);
        this.startTimerOfTask5Button.setEnabled(true);
        this.stopTimerButton.setEnabled(true);
        TaskTimer.activeTaskId = 1;
    }

    private void startTimerOfTask2ButtonActionPerformed(final ActionEvent evt) {
        if (!this.timer.isRunning()) {
            this.timer.start();
            TaskTimer.isTimerStop = false;
        }
        this.startTimerOfTask1Button.setEnabled(true);
        this.startTimerOfTask2Button.setEnabled(false);
        this.startTimerOfTask3Button.setEnabled(true);
        this.startTimerOfTask4Button.setEnabled(true);
        this.startTimerOfTask5Button.setEnabled(true);
        this.stopTimerButton.setEnabled(true);
        TaskTimer.activeTaskId = 2;
    }

    private void startTimerOfTask4ButtonActionPerformed(final ActionEvent evt) {
        if (!this.timer.isRunning()) {
            this.timer.start();
            TaskTimer.isTimerStop = false;
        }
        this.startTimerOfTask1Button.setEnabled(true);
        this.startTimerOfTask2Button.setEnabled(true);
        this.startTimerOfTask3Button.setEnabled(true);
        this.startTimerOfTask4Button.setEnabled(false);
        this.startTimerOfTask5Button.setEnabled(true);
        this.stopTimerButton.setEnabled(true);
        TaskTimer.activeTaskId = 4;
    }

    private void stopTimerButtonMouseClicked(final MouseEvent evt) {
    }

    private void stopTimerButtonActionPerformed(final ActionEvent evt) {
        if (this.timer.isRunning()) {
            this.timer.stop();
            TaskTimer.isTimerStop = true;
        }
        this.startTimerOfTask1Button.setEnabled(true);
        this.startTimerOfTask2Button.setEnabled(true);
        this.startTimerOfTask3Button.setEnabled(true);
        this.startTimerOfTask4Button.setEnabled(true);
        this.startTimerOfTask5Button.setEnabled(true);
        TaskTimer.activeTaskId = 0;
        this.stopTimerButton.setEnabled(false);
    }

    public static void main(final String[] args) {
        try {
            UIManager.LookAndFeelInfo[] installedLookAndFeels;
            for (int length = (installedLookAndFeels = UIManager.getInstalledLookAndFeels()).length, i = 0; i < length; ++i) {
                final UIManager.LookAndFeelInfo info = installedLookAndFeels[i];
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TaskTimer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex2) {
            Logger.getLogger(TaskTimer.class.getName()).log(Level.SEVERE, null, ex2);
        } catch (IllegalAccessException ex3) {
            Logger.getLogger(TaskTimer.class.getName()).log(Level.SEVERE, null, ex3);
        } catch (UnsupportedLookAndFeelException ex4) {
            Logger.getLogger(TaskTimer.class.getName()).log(Level.SEVERE, null, ex4);
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskTimer().setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (this.taskOneTimeLeft <= 0) {
            this.taskOneTimeLeft = 0;
            this.startTimerOfTask1Button.setEnabled(true);
            this.startTimerOfTask1Button.setText("Done");
        }
        if (this.taskTwoTimeLeft <= 0) {
            this.taskTwoTimeLeft = 0;
            this.startTimerOfTask2Button.setEnabled(true);
            this.startTimerOfTask2Button.setText("Done");
        }
        if (this.taskThreeTimeLeft <= 0) {
            this.taskThreeTimeLeft = 0;
            this.startTimerOfTask3Button.setEnabled(true);
            this.startTimerOfTask3Button.setText("Done");
        }
        if (this.taskFourTimeLeft <= 0) {
            this.taskFourTimeLeft = 0;
            this.startTimerOfTask4Button.setEnabled(true);
            this.startTimerOfTask4Button.setText("Done");
        }
        if (this.taskFiveTimeLeft <= 0) {
            this.taskFiveTimeLeft = 0;
            this.startTimerOfTask5Button.setEnabled(true);
            this.startTimerOfTask5Button.setText("Done");
        }
        ++this.counter;
        if (this.counter >= 20) {
            this.counter = 0;
            this.updateTaskTimerRecords();
            this.setContents();
        }
        if (!this.startTimerOfTask1Button.isEnabled() || TaskTimer.activeTaskId == 1) {
            --this.taskOneTimeLeft;
            this.min = this.taskOneTimeLeft / 60;
            this.sec = this.taskOneTimeLeft % 60;
            this.hr = this.min / 60;
            if (this.min >= 60) {
                this.min %= 60;
            }
            this.taskOneTimeLeftLabel.setText(String.valueOf(this.hr.toString()) + " Hour " + this.min.toString() + " min " + this.sec.toString() + "sec");
        } else if (!this.startTimerOfTask2Button.isEnabled() || TaskTimer.activeTaskId == 2) {
            --this.taskTwoTimeLeft;
            this.min = this.taskTwoTimeLeft / 60;
            this.sec = this.taskTwoTimeLeft % 60;
            this.hr = this.min / 60;
            if (this.min >= 60) {
                this.min %= 60;
            }
            this.taskTwoTimeLeftLabel.setText(String.valueOf(this.hr.toString()) + " Hour " + this.min.toString() + " min " + this.sec.toString() + "sec");
        } else if (!this.startTimerOfTask3Button.isEnabled() || TaskTimer.activeTaskId == 3) {
            --this.taskThreeTimeLeft;
            this.min = this.taskThreeTimeLeft / 60;
            this.sec = this.taskThreeTimeLeft % 60;
            this.hr = this.min / 60;
            if (this.min >= 60) {
                this.min %= 60;
            }
            this.taskThreeTimeLeftLabel.setText(String.valueOf(this.hr.toString()) + " Hour " + this.min.toString() + " min " + this.sec.toString() + "sec");
        } else if (!this.startTimerOfTask4Button.isEnabled() || TaskTimer.activeTaskId == 4) {
            --this.taskFourTimeLeft;
            this.min = this.taskFourTimeLeft / 60;
            this.sec = this.taskFourTimeLeft % 60;
            this.hr = this.min / 60;
            if (this.min >= 60) {
                this.min %= 60;
            }
            this.taskFourTimeLeftLabel.setText(String.valueOf(this.hr.toString()) + " Hour " + this.min.toString() + " min " + this.sec.toString() + "sec");
        } else if (!this.startTimerOfTask5Button.isEnabled() || TaskTimer.activeTaskId == 5) {
            --this.taskFiveTimeLeft;
            this.min = this.taskFiveTimeLeft / 60;
            this.sec = this.taskFiveTimeLeft % 60;
            this.hr = this.min / 60;
            if (this.min >= 60) {
                this.min %= 60;
            }
            this.taskFiveTimeLeftLabel.setText(String.valueOf(this.hr.toString()) + " Hour " + this.min.toString() + " min " + this.sec.toString() + "sec");
        }
        final Calendar today = Calendar.getInstance();
        final DateFormat dateFormat = new SimpleDateFormat("dd-MMM > h-m-s");
        this.clockLabel.setText(dateFormat.format(today.getTime()));
    }

    private void updateTaskTimerRecords() {
        if (this.taskTimerTOList != null) {
            this.taskTimerTOList.get(0).setTimeLimit(this.taskOneTimeLeft);
            this.taskTimerTOList.get(1).setTimeLimit(this.taskTwoTimeLeft);
            this.taskTimerTOList.get(2).setTimeLimit(this.taskThreeTimeLeft);
            this.taskTimerTOList.get(3).setTimeLimit(this.taskFourTimeLeft);
            this.taskTimerTOList.get(4).setTimeLimit(this.taskFiveTimeLeft);
            this.bOperation.updateTasks(this.taskTimerTOList);
        }
    }
}
