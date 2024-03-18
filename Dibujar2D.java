import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Dibujar2D extends JPanel {
    private ArrayList<Integer> coordenadasX = new ArrayList<>();
    private ArrayList<Integer> coordenadasY = new ArrayList<>();
    private ArrayList<int[]> aristas = new ArrayList<>();

    public Dibujar2D(String archivo) {
        cargarDatos(archivo);
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

        // Invert Y-coordinates
        int height = getHeight();
        ArrayList<Integer> invertedY = new ArrayList<>();
        for (Integer y : coordenadasY) {
            invertedY.add(height - y);
        }

        // Dibujar polígono
        Polygon poligono = new Polygon();
        for (int i = 0; i < coordenadasX.size(); i++) {
            poligono.addPoint(coordenadasX.get(i), invertedY.get(i));
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
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dibujar 2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dibujar2D panel = new Dibujar2D("datos.txt");
        frame.add(panel);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
