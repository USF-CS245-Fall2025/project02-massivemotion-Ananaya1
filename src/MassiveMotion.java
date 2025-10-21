import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * The MassiveMotion class represents a simulation of celestial bodies
 * moving in a 2D space. One central "sun" remains fixed while smaller
 * bodies are generated along the window edges and move across the screen.
 * The simulation is configurable via a properties file.
 * 
 * @author Ananaya
 * @version 1.0
 */
public class MassiveMotion extends JPanel implements ActionListener {

    /** Swing timer controlling animation speed. */
    protected javax.swing.Timer tm;

    /** Window dimensions (width and height). */
    protected int width;
    protected int height;

    /** List to store all celestial bodies (planets, asteroids, etc.). */
    List<CelestialBody> celestialBodies;

    /** Probability of generating a body from X or Y edges. */
    double genXProb;
    double genYProb;

    /** Default body size and velocity, read from configuration. */
    int bodySize;
    int bodyVelocity;

    /** Random number generator for creating randomized motion and color. */
    Random rand = new Random();

    
    public MassiveMotion(String propfile) {
        //  : insert your code to read from configuration file here.
        Properties props = new Properties();
        try {
            FileInputStream in = new FileInputStream(propfile);
            props.load(in);
            in.close();
        } catch (IOException e) {
            System.out.println("Error reading config file: " + e.getMessage());
        }

        // Read configuration values or use defaults if missing
        int delay = Integer.parseInt(props.getProperty("timer_delay", "75"));
        width = Integer.parseInt(props.getProperty("window_size_x", "640"));
        height = Integer.parseInt(props.getProperty("window_size_y", "480"));

        //  : Replace the first argument with delay with value from config file.
        tm = new javax.swing.Timer(delay, this);

        //  : Consider removing the next two lines (coordinates) for random starting
        // locations.
        // Decide which list implementation to use for storing celestial bodies.
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

        // Create the fixed Sun in the center of the screen
        int starX = width / 2; // Center X
        int starY = height / 2; // Center Y
        int starSize = Integer.parseInt(props.getProperty("star_size", "30"));

        // Sun is red and stationary (velocity 0)
        CelestialBody star = new CelestialBody(starX, starY, 0, 0, starSize, Color.RED);
        celestialBodies.add(star);

        // Read configuration for new body generation
        genXProb = Double.parseDouble(props.getProperty("gen_x", "0.06"));
        genYProb = Double.parseDouble(props.getProperty("gen_y", "0.06"));
        bodySize = Integer.parseInt(props.getProperty("body_size", "10"));
        bodyVelocity = Integer.parseInt(props.getProperty("body_velocity", "3"));

        // Start the timer (animation loop)
        tm.start(); // Start timer once (not in paintComponent)
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //  : Keep this as is.

        //  : Paint each ball. Here's how to paint all celestial bodies:
        // Set universe-like dark background
        this.setBackground(Color.BLACK);

        // Draw each celestial body with its current position and color
        for (int i = 0; i < celestialBodies.size(); i++) {
            CelestialBody body = celestialBodies.get(i);
            g.setColor(body.getColor());
            g.fillOval(body.getX(), body.getY(), body.getSize(), body.getSize());
        }

        //  : Keep timer logic in constructor
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //  : Change the location of each ball.

        // Move all existing celestial bodies except the Sun (index 0)
        for (int i = 1; i < celestialBodies.size(); i++) {
            CelestialBody body = celestialBodies.get(i);
            body.move();
        }

        // Randomly generate new bodies along X edges
        if (rand.nextDouble() < genXProb) {
            int y = rand.nextInt(height);
            int vx = rand.nextInt(bodyVelocity * 2 + 1) - bodyVelocity;
            int vy = rand.nextInt(bodyVelocity * 2 + 1) - bodyVelocity;
            if (vx == 0)
                vx = 1; // ensure non-zero velocity
            int side = rand.nextBoolean() ? 0 : width;

            // Randomized color for visual variety
            Color randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            CelestialBody newBody = new CelestialBody(side, y, vx, vy, bodySize, randomColor);
            celestialBodies.add(newBody);
        }

        // Randomly generate new bodies along Y edges
        if (rand.nextDouble() < genYProb) {
            int x = rand.nextInt(width);
            int vx = rand.nextInt(bodyVelocity * 2 + 1) - bodyVelocity;
            int vy = rand.nextInt(bodyVelocity * 2 + 1) - bodyVelocity;
            if (vy == 0)
                vy = 1; // ensure non-zero velocity
            int side = rand.nextBoolean() ? 0 : height;

            // Randomized color for visual variety
            Color randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            CelestialBody newBody = new CelestialBody(x, side, vx, vy, bodySize, randomColor);
            celestialBodies.add(newBody);
        }

        //  : Keep this at the end to refresh the screen
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
