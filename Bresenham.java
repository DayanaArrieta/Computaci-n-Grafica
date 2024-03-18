import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Bresenham extends JPanel implements MouseListener, MouseMotionListener {

    int x1, y1, x2, y2;
    boolean drawing;

    public Bresenham() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        drawing = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLUE);
        if (drawing) {
            drawBresenhamLine(x1, y1, x2, y2, g2d);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
        drawing = false;  // Reset drawing when mouse is pressed
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        drawing = true;  // Set drawing to true when mouse is released
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        drawing = true;  // Set drawing to true when mouse is dragged
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private void drawBresenhamLine(int x1, int y1, int x2, int y2, Graphics2D g2d) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            g2d.drawLine(x1, y1, x1, y1); // Draw the pixel at the current position

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bresenham Line Drawing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Bresenham ev = new Bresenham();
        frame.add(ev);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

