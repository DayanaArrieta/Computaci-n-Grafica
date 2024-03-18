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

public class Mover2D extends JPanel implements KeyListener {
    private ArrayList<Integer> coordenadasX = new ArrayList<>();
    private ArrayList<Integer> coordenadasY = new ArrayList<>();
    private ArrayList<int[]> aristas = new ArrayList<>();

    private double posX = 150; // Posición inicial en el eje X
    private double posY = 150; // Posición inicial en el eje Y

    public Mover2D(String archivo) {
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

        // Dibujar polígono
        Polygon poligono = new Polygon();
        for (int i = 0; i < coordenadasX.size(); i++) {
            int x = coordenadasX.get(i);
            int y = invertedY.get(i);
            poligono.addPoint((int) (x + posX), (int) (y + posY));
        }
        g2d.setColor(Color.BLUE);
        g2d.drawPolygon(poligono);

        // Dibujar aristas
        g2d.setColor(Color.RED);
        for (int[] arista : aristas) {
            int x1 = coordenadasX.get(arista[0]);
            int y1 = invertedY.get(arista[0]);
            int x2 = coordenadasX.get(arista[1]);
            int y2 = invertedY.get(arista[1]);
            g2d.drawLine((int) (x1 + posX), (int) (y1 + posY), (int) (x2 + posX), (int) (y2 + posY));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            // Mover hacia la izquierda
            posX -= 10;
        } else if (key == KeyEvent.VK_RIGHT) {
            // Mover hacia la derecha
            posX += 10;
        } else if (key == KeyEvent.VK_UP) {
            // Mover hacia arriba
            posY -= 10;
        } else if (key == KeyEvent.VK_DOWN) {
            // Mover hacia abajo
            posY += 10;
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
