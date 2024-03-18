import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Mover3D extends JPanel implements KeyListener {
    private ArrayList<Integer> coordenadasX = new ArrayList<>();
    private ArrayList<Integer> coordenadasY = new ArrayList<>();
    private ArrayList<int[]> aristas = new ArrayList<>();

    private double posX = 0; // Posición en el eje X
    private double posZ = 0; // Posición en el eje Z
    private double angle = 0; // Ángulo de giro

    public Mover3D(String archivo) {
        cargarDatos(archivo);
        addKeyListener(this);
        setFocusable(true);
    }

    private void cargarDatos(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            int numVertices = Integer.parseInt(br.readLine());

            for (int i = 0; i < numVertices; i++) {
                String[] coordenadas = br.readLine().split(" ");
                int x = Integer.parseInt(coordenadas[0]);
                int y = Integer.parseInt(coordenadas[1]);
                coordenadasX.add(x);
                coordenadasY.add(y);
            }

            int numAristas = Integer.parseInt(br.readLine());

            for (int i = 0; i < numAristas; i++) {
                String[] puntosArista = br.readLine().split(" ");
                int puntoA = Integer.parseInt(puntosArista[0]);
                int puntoB = Integer.parseInt(puntosArista[1]);
                aristas.add(new int[] { puntoA, puntoB });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Invertir Y-coordinates
        int height = getHeight();
        ArrayList<Integer> invertedY = new ArrayList<>();
        for (Integer y : coordenadasY) {
            invertedY.add(height - y);
        }

        // Dibujar polígono en la nueva posición
        Polygon poligono = new Polygon();
        for (int i = 0; i < coordenadasX.size(); i++) {
            if (i < invertedY.size()) {
                int x = (int) (coordenadasX.get(i) * Math.cos(angle) - invertedY.get(i) * Math.sin(angle) + posX);
                int y = (int) (coordenadasX.get(i) * Math.sin(angle) + invertedY.get(i) * Math.cos(angle) + posZ);
                poligono.addPoint(x, y);
            }
        }
        g2d.setColor(Color.BLUE);
        g2d.drawPolygon(poligono);

        // Dibujar aristas
        g2d.setColor(Color.RED);
        for (int[] arista : aristas) {
            if (arista[0] < coordenadasX.size() && arista[1] < coordenadasX.size()) {
                int x1 = (int) (coordenadasX.get(arista[0]) * Math.cos(angle) - invertedY.get(arista[0]) * Math.sin(angle) + posX);
                int y1 = (int) (coordenadasX.get(arista[0]) * Math.sin(angle) + invertedY.get(arista[0]) * Math.cos(angle) + posZ);
                int x2 = (int) (coordenadasX.get(arista[1]) * Math.cos(angle) - invertedY.get(arista[1]) * Math.sin(angle) + posX);
                int y2 = (int) (coordenadasX.get(arista[1]) * Math.sin(angle) + invertedY.get(arista[1]) * Math.cos(angle) + posZ);
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            // Mover hacia adelante
            posX += Math.cos(angle);
            posZ += Math.sin(angle);
        } else if (key == KeyEvent.VK_DOWN) {
            // Mover hacia atrás
            posX -= Math.cos(angle);
            posZ -= Math.sin(angle);
        } else if (key == KeyEvent.VK_LEFT) {
            // Girar a la izquierda
            angle += 0.1;
        } else if (key == KeyEvent.VK_RIGHT) {
            // Girar a la derecha
            angle -= 0.1;
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mover 2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Mover2D panel = new Mover2D("datos.txt");
        frame.add(panel);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
