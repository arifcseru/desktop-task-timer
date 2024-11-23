/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timer.forms;

import com.timer.coagent.Announcer;
import static com.timer.coagent.Announcer.configProperties;
import com.timer.coagent.UxBoxDrawer;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author ZBG
 */
public class TaskTimerMain {

    private static TaskTimer taskTimer = null;

    private static void hideAppIcon() {
        if (SystemTray.isSupported()) {
//			System.out.println("hide App icon1");
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage("icon.png"); // Provide an icon image if needed
            TrayIcon trayIcon = new TrayIcon(image, "Hidden Icon");

            try {
                tray.add(trayIcon);
                tray.remove(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    private static void makeFrameDraggable(JFrame frame) {
        UxBoxDrawer uxBoxDrawer = (UxBoxDrawer) frame.getContentPane().getComponent(0);
        Announcer.MouseDragListener mouseListener = new Announcer.MouseDragListener(frame, uxBoxDrawer);
        frame.addMouseListener(mouseListener);
        frame.addMouseMotionListener(mouseListener);
    }

    public static void announcerMain() {
        Announcer announcer = new Announcer();
        String randomString = "Welcome to reminder...";
        UxBoxDrawer boxDrawer = new UxBoxDrawer(randomString);
        Announcer.boxDrawer = boxDrawer;

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                taskTimer = new TaskTimer();
                taskTimer.setVisible(true);
            }
        });
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Reminder");
            Announcer.frame = frame;
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.getContentPane().add(boxDrawer);

            // Customize frame properties
            frame.setUndecorated(true); // Remove title bar and border
            frame.setResizable(false); // Disable resizing
            frame.pack();

            // Position the frame at the top middle of the screen
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = ((screenSize.width - frame.getWidth()) / 2) + 300;
            frame.setLocation(x, 0);
            frame.setAlwaysOnTop(true);
            hideAppIcon();

            frame.setVisible(false);
            makeFrameDraggable(frame);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to close the application?",
                            "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
                            null);
                    if (confirm == JOptionPane.YES_OPTION) {
                        frame.hide();
                    }
                }
            });
        });

        while (true) {
            boxDrawer.setRandomString(randomString);

            try {
                String delay = configProperties.getProperty("delay");
                Thread.sleep(Integer.valueOf(delay) * 1000); // delay in seconds
                String activityMessage = announcer.checkActiveWindowActivity();
                System.out.println("activityMessage: " + activityMessage);

                if (!activityMessage.trim().isEmpty() && activityMessage.contains("chrome")) {
                    announcer.checkUsersChromeActivity();
                } else if (TaskTimer.activeTaskTimerWindow.equalsIgnoreCase("Maximum") && !activityMessage.trim().isEmpty() && activityMessage.contains("google")) {
                    System.out.println("google");
                    taskTimer.show();
                    announcer.checkUsersFirefoxActivity();
                } else if (!activityMessage.trim().isEmpty() && activityMessage.contains("firefox")) {
                    announcer.checkUsersFirefoxActivity();
                } else if (!activityMessage.trim().isEmpty()) {
                    randomString = activityMessage;
                    boxDrawer.setRandomStringDanger(activityMessage);
                } else {
                    String randomSentence = announcer.getRandomSentence();
                    boxDrawer.setRandomString(randomSentence);
                }

            } catch (InterruptedException e) {
                System.err.println("Sleep interrupted: " + e.getMessage());
            }
        }

    }

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
        announcerMain();

    }

}
