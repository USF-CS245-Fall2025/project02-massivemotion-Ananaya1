import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

/**
 * The MassiveMotion class represents a simulation of celestial bodies
 * moving in a 2D space. One central sun remains fixed while randomly
 * generated bodies move across the screen and get removed when exiting
 * the boundary. All configuration is loaded from a .txt properties file.
 */
public class MassiveMotion extends JPanel implements ActionListener {

    /** Swing timer controlling animation speed. */
    protected javax.swing.Timer tm;

    /** Window dimensions (width and height). */
    protected int width;
    protected int height;

    /**
     * List storing all celestial bodies (using student-implemented List interface).
     */
    List<CelestialBody> celestialBodies;

    /** Body probabilities and motion parameters loaded from properties file. */
    double genXProb;
    double genYProb;
    int bodySize;
    int bodyVelocity;

    /** Random number generator for motion and color variations. */
    Random rand = new Random();

    // TODO: insert your code to read from configuration file here.
    public MassiveMotion(String propfile) {
        Properties props = new Properties();
        try {
            FileInputStream in = new FileInputStream(propfile);
            props.load(in);
            in.close();
        } catch (IOException e) {
            System.out.println("Error reading config file: " + e.getMessage());
        }

        int delay = Integer.parseInt(props.getProperty("timer_delay", "75"));
        width = Integer.parseInt(props.getProperty("window_size_x", "640"));
        height = Integer.parseInt(props.getProperty("window_size_y", "480"));

       
        tm = new javax.swing.Timer(delay, this);

       
        // Select which student-implemented List realisation to use.
        String listType = props.getProperty("list", "arraylist").toLowerCase();
        if (listType.equals("arraylist")) {
            celestialBodies = new ArrayList<>();
        } else if (listType.equals("single")) {
            celestialBodies = new LinkedList<>();
        } else if (listType.equals("double")) {
            celestialBodies = new DoublyLinkedList<>();
        } else {
            celestialBodies = new DummyHeadLinkedList<>();
        }

        // Create fixed sun in the center of the window
        int starSize = Integer.parseInt(props.getProperty("star_size", "30"));
        int starX = width / 2;
        int starY = height / 2;

        // Sun has color RED and no velocity, remains fixed.
        CelestialBody star = new CelestialBody(starX, starY, 0, 0, starSize, Color.RED);
        celestialBodies.add(star);

        // Load generation parameters
        genXProb = Double.parseDouble(props.getProperty("gen_x", "0.06"));
        genYProb = Double.parseDouble(props.getProperty("gen_y", "0.06"));
        bodySize = Integer.parseInt(props.getProperty("body_size", "10"));
        bodyVelocity = Integer.parseInt(props.getProperty("body_velocity", "3"));

        // Start animation loop
        tm.start(); // timer should only be started once
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // TODO: Keep this as is.

        // Universe-style background
        this.setBackground(Color.BLACK);

        for (int i = 0; i < celestialBodies.size(); i++) {
            CelestialBody body = celestialBodies.get(i);
            g.setColor(body.getColor());
            g.fillOval(body.getX(), body.getY(), body.getSize(), body.getSize());
        }

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
      
        // Move all celestial bodies except the fixed sun (index 0)
        for (int i = 1; i < celestialBodies.size();) {
            CelestialBody body = celestialBodies.get(i);
            body.move();

            // Remove bodies when they exit screen boundary
            if (body.isOffScreen(width, height)) {
                celestialBodies.remove(i);
            } else {
                i++;
            }
        }

        // Generate new body from left or right border
        if (rand.nextDouble() < genXProb) {
            int y = rand.nextInt(height);
            int vx = rand.nextInt(bodyVelocity * 2 + 1) - bodyVelocity;
            int vy = rand.nextInt(bodyVelocity * 2 + 1) - bodyVelocity;
            if (vx == 0)
                vx = 1;
            int side = rand.nextBoolean() ? 0 : width;
            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            celestialBodies.add(new CelestialBody(side, y, vx, vy, bodySize, color));
        }

        // Generate new body from top or bottom border
        if (rand.nextDouble() < genYProb) {
            int x = rand.nextInt(width);
            int vx = rand.nextInt(bodyVelocity * 2 + 1) - bodyVelocity;
            int vy = rand.nextInt(bodyVelocity * 2 + 1) - bodyVelocity;
            if (vy == 0)
                vy = 1;
            int side = rand.nextBoolean() ? 0 : height;
            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            celestialBodies.add(new CelestialBody(x, side, vx, vy, bodySize, color));
        }

        repaint();
    }

    public static void main(String[] args) {
        System.out.println("Massive Motion starting...");
        MassiveMotion mm = new MassiveMotion("MassiveMotion.txt");

        JFrame jf = new JFrame();
        jf.setTitle("Massive Motion");
        jf.setSize(mm.width, mm.height);
        jf.add(mm);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
