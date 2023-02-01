
//*******************************************************************
//
//   File: Rings.java
//   Class: Rings 
//
//*******************************************************************
import java.io.File;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.IOException;

public class Rings {

    // set up panel
    static final int SIZE = 500;
    static DrawingPanel panel = new DrawingPanel(SIZE, SIZE);
    static Graphics2D g = panel.getGraphics();

    // enable double buffering
    static BufferedImage offscreen = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
    static Graphics2D osg = offscreen.createGraphics();

    // animation pause (in miliseconds)
    public static final int DELAY = 500;

    // background image
    public static final String BACKGROUND = "starfield.jpg";
    public static BufferedImage bgImage;

    public static void main(String[] args) {

        // load BACKGROUND image
        loadBG();

        // play animation
        animateRings();
    }

    
    // load BACKGROUND image into the variable bgImage
    public static void loadBG() {
        try {
            bgImage = ImageIO.read(new File(BACKGROUND));
        } catch (IOException e) {
            System.out.println("Could not open " + bgImage);
            System.exit(1);
        }
    }
    
    public static void animateRings() {
        long count = 0;
        while (true) {
            // draw background
            osg.drawImage(bgImage, 0, 0, null);

            // draw planet
            drawPlanet(count);

            // double buffer -- copy osg to g
            g.drawImage(offscreen, 0, 0, null);

            // pause to display frame
            panel.sleep(DELAY);
            
            // update count for next ring color
            count++;
        }
    }

    public static void drawPlanet(long count) {
        // set planet parameters
        int X = SIZE / 2;
        int Y = SIZE / 2;
        int D = SIZE / 5;

        // draw planet
        osg.setColor(Color.BLUE);
        osg.fillOval(X, Y, D, D);
        
        // find center of planet
        int CX = X + D / 2;
        int CY = Y + D / 2; 

        // draw rings, centered on planet
        if (count % 2 == 0) {
            osg.setColor(Color.CYAN);
        }
        else {
            osg.setColor(Color.MAGENTA);
        }

        int dX = (int) (D * 1.8);
        int dY = (int) (D * 0.4);

        int eX = CX - dX / 2;
        int eY = CY - dY / 2;
        
        int theta = 32;
        int phi = 90 + theta;
        int gamma = 360 - 2 * theta;

        osg.drawArc(eX, eY, dX, dY, phi, gamma);
    }
}
