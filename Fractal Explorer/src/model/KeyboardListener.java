package model;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import view.GUI;

public class KeyboardListener implements KeyListener
{
    @Override
    public void keyPressed(KeyEvent e)
    {
        final int    key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE)
        {
            // Exit the application
            System.exit(0);
        }
        else if (key == KeyEvent.VK_PRINTSCREEN)
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            final int    userSelection = fileChooser.showSaveDialog(GUI.getInstance());

            if (userSelection == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    final Robot     robot = new Robot();
                    final String    format = "png";
                    final String    fileName = "test." + format;

                    final Rectangle    screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    final BufferedImage    screenFullImage = robot.createScreenCapture(screenRect);
                    ImageIO.write(screenFullImage, format, new File(fileName));

                    System.out.println("A full screenshot saved!");
                }
                catch (AWTException | IOException ex)
                {
                    System.err.println(ex);
                };
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e)
    {
        // Unused
    }


    @Override
    public void keyTyped(KeyEvent e)
    {
        // Unused
    }
}