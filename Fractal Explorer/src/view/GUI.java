package view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import model.KeyboardListener;


public final class GUI extends JFrame
{
    // Singleton instance
    private static final GUI    INSTANCE = new GUI();

    // Constants
    private static final long      serialVersionUID     = 1180142671526360527L;
    private static final String    WINDOW_TITLE         = "Mandelbrot Set Explorer";
    private static final int       LOADING_SCREEN_COLOR = 0;
    private static final int       START_X_COORDINATE   = 0;
    private static final int       START_Y_COORDINATE   = 0;


    // The image that will represent the Mandelbrot set
    private BufferedImage    image;

    // Loading-screen image
    private BufferedImage    loadingScreenImage;


    // Hidden constructor
    private GUI()
    {
        super(WINDOW_TITLE);


        // Get screen resolution
        final Toolkit    toolkit  = Toolkit.getDefaultToolkit();
        final int    screenWidth  = (int) toolkit.getScreenSize().getWidth();
        final int    screenHeight = (int) toolkit.getScreenSize().getHeight();

        loadingScreenImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);


        // Create a black image, used as "loading screen"
        for (int x = 0; x < screenWidth; x++)
        {
            for (int y = 0; y < screenHeight; y++)
            {
                loadingScreenImage.setRGB(x, y, LOADING_SCREEN_COLOR);
            }
        }
        image = loadingScreenImage;


        // Window properties
        setBounds(START_X_COORDINATE, START_Y_COORDINATE, screenWidth, screenHeight);
        getContentPane().setBackground(Color.BLACK);
        setAlwaysOnTop(false);
        setUndecorated(true);
        setVisible(true);
        setFocusable(true);

        // Listeners
        addKeyListener(new KeyboardListener());
    }



    // Thread-safe
    public static GUI getInstance()
    {
        return INSTANCE;
    }


    public void displayImage(final BufferedImage image)
    {
        this.image = image;
        this.repaint();
    }



    @Override
    public void paint(final Graphics g)
    {
        g.drawImage(image, START_X_COORDINATE, START_Y_COORDINATE, this);
    }
}