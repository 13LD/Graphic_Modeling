package com.company;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
public class Main extends JPanel implements ActionListener {

    int x = 160;
    int y = 100;
    double left_part[][] = {
            { x + 64, y + 44 }, { x + 224, y + 148 }, { x + 64 , y + 251 }, { x + 104, y + 148 },
            { x + 64, y + 44 }
    };
    double right_part[][] = {
            { x + 370, y + 145 }, { x + 430, y + 115 }, { x + 404 , y + 145 }, { x + 430, y + 178 },
            { x + 370, y + 145 }
    };
    Timer timer;

    private double scale = 1;
    private double delta = 0.01;

    private double ty = 6;
    private static int maxWidth;
    private static int maxHeight;


    public Main() {
        timer = new Timer(10, this);
        timer.start();
    }
    public void drawPath(Graphics2D g2d, double left_part[][]) {
        GeneralPath body = new GeneralPath();
        body.moveTo(left_part[0][0], left_part[0][1]);
        for (int k = 1; k < left_part.length; k++)
            body.lineTo(left_part[k][0], left_part[k][1]);
        body.closePath();
        g2d.fill(body);
    }


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setBackground(Color.GRAY);
        g2d.setColor(Color.YELLOW);

        g2d.clearRect(0, 0, maxWidth+1, maxHeight+1);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        BasicStroke bs3 = new BasicStroke(20, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER);
        g2d.setStroke(bs3);
        g2d.drawRect(20, 20, 760, 560);

        g2d.scale(scale, 0.99);
        GradientPaint gp = new GradientPaint(5, 25,
                Color.RED, 20, 2, Color.BLUE, true);
        g2d.setPaint(gp);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                (float)scale));

        drawPath(g2d, left_part);
        g2d.setColor(Color.BLUE);

        drawPath(g2d, right_part);

        g2d.setColor(Color.YELLOW);

        g2d.fill(new Ellipse2D.Double(x + 120 , y + 115, 70, 70));
        BasicStroke bs4 = new BasicStroke(1, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER);
        g2d.setStroke(bs4);
        g2d.setColor(Color.BLACK);

        g2d.drawLine(x + 140, y + 135, x + 395, y + 135);
        g2d.drawLine(x + 140, y + 140, x + 395, y + 140);
        g2d.drawLine(x + 140, y + 145, x + 395, y + 145);
        g2d.drawLine(x + 140, y + 150, x + 395, y + 150);
        g2d.drawLine(x + 140, y + 155, x + 395, y + 155);

    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Приклад анімації");
        frame.add(new Main());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 610);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxWidth = size.width - insets.left - insets.right - 1;
        maxHeight = size.height - insets.top - insets.bottom - 1;
    }
    public void actionPerformed(ActionEvent e) {
        if ( scale < 0.01 ) {
            delta = -delta;
        } else if (scale > 0.99) {
            delta = -delta;
        }
        scale += delta;

        repaint();
    }
}