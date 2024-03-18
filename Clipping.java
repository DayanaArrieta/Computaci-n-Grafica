import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Clipping extends JPanel implements MouseListener {

    Line2D.Double linea1;
    Rectangle2D.Double clippingRectangle;

    public Clipping() {
        linea1 = new Line2D.Double();
        clippingRectangle = new Rectangle2D.Double(-200, -100, 400, 200);
        this.addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Draw clipping rectangle in black
        g2d.setColor(Color.BLACK);
        g2d.draw(clippingRectangle);

        // Draw the line in green if entirely inside the clipping rectangle, red otherwise
        if (lineInsideRectangle(linea1, clippingRectangle)) {
            g2d.setColor(Color.GREEN);
            g2d.draw(linea1);
        } else {
            g2d.setColor(Color.RED);
            g2d.draw(linea1);
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
        linea1.x1 = e.getX();
        linea1.y1 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        linea1.x2 = e.getX();
        linea1.y2 = e.getY();
        repaint();
    }

    // Check if a line is entirely inside a rectangle
    private boolean lineInsideRectangle(Line2D.Double line, Rectangle2D.Double rectangle) {
        return rectangle.contains(line.getP1()) && rectangle.contains(line.getP2());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clipping Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Clipping clippingExample = new Clipping();
        frame.add(clippingExample);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
