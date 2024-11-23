package com.timer.coagent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.swing.JPanel;

public class UxBoxDrawer extends JPanel {

    private String randomString = "";

    private static final int PANEL_WIDTH = (int) (4.5 * 72); // 2 inches converted to pixels (assuming 72 DPI)
    private static final int PANEL_HEIGHT = (int) (0.4 * 72); // 0.5 inches converted to pixels (assuming 72 DPI)

    public UxBoxDrawer(String randomString) {
        this.randomString = randomString;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    }

    Graphics2D g2d;

    public void setRandomString(String randomSentence) {
        this.randomString = randomSentence;
        repaint();
    }

    public void setRandomStringInfo(String randomSentence) {
//		System.out.println("Info");
        this.randomString = randomSentence;
        setBackground(Color.CYAN);
        repaint();
    }

    public void setRandomStringSuccess(String randomSentence) {
//		System.out.println("Success");
        this.randomString = randomSentence;
        setBackground(Color.GREEN);
        repaint();
    }

    public void setRandomStringDanger(String randomSentence) {
//		System.out.println("Danger");
        this.randomString = randomSentence;
        setBackground(Color.RED);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g.create();

        // Draw the UI box
        g2d.setColor(Color.LIGHT_GRAY);
//		g2d.setColor(Color.MAGENTA);
        g2d.fill(new Rectangle2D.Double(0, 0, PANEL_WIDTH, PANEL_HEIGHT));

        // Draw the random sentence
        g2d.setColor(Color.BLACK);
        Font font = new Font("Serif", Font.BOLD, 14);
        g2d.setFont(font);
        drawString(g2d, this.randomString, 10, 10, PANEL_WIDTH - 20);

        g2d.dispose();
    }

    private void drawString(Graphics2D g2d, String text, int x, int y, int maxWidth) {
        FontRenderContext frc = g2d.getFontRenderContext();
        AttributedString attrStr = new AttributedString(text);
        attrStr.addAttribute(TextAttribute.FONT, g2d.getFont());
        AttributedCharacterIterator aci = attrStr.getIterator();
        LineBreakMeasurer measurer = new LineBreakMeasurer(aci, frc);
        float wrappingWidth = maxWidth;
        int lineHeight = g2d.getFontMetrics().getHeight();

        while (measurer.getPosition() < aci.getEndIndex()) {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            y += layout.getAscent();
            float dx = layout.isLeftToRight() ? 0 : (wrappingWidth - layout.getAdvance());
            layout.draw(g2d, x + dx, y);
            y += layout.getDescent() + layout.getLeading();
        }
    }

//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(() -> {
//			JFrame frame = new JFrame("UX Box Drawer");
//			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			frame.getContentPane().add(new UxBoxDrawer("test"));
//
//			// Customize frame properties
//			frame.setUndecorated(true); // Remove title bar and border
//			frame.setResizable(false); // Disable resizing
//			frame.pack();
//
//			// Position the frame at the top middle of the screen
//			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//			int x = (screenSize.width - frame.getWidth()) / 3;
//			frame.setLocation(x, 0);
//
//			frame.setVisible(true);
//		});
//	}
}
