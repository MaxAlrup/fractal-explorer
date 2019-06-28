package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;


public final class MandelbrotSetComputingThread implements Callable<ImageFragment>
{
    // Constants
    private static final double    LIMIT = 2.0;
    private static final double    MAX_ABOSULTE_VALUE = LIMIT * LIMIT;
    private static final double    LOG2 = Math.log(2);
    //private static final double    ESCAPE_RADIUS = 144;

    private int       threadID;
    private int       startX;
    private int       startY;
    private int       endX;
    private int       endY;
    private int       maxNumberOfIterations;
    private int       colorScheme;
    private double    zoom;



    public MandelbrotSetComputingThread(int id, int startX, int startY, int endX, int endY, int maxNumberOfIterations, int zoom)
    {
        threadID = id;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.maxNumberOfIterations = maxNumberOfIterations;
        this.zoom = zoom;
        colorScheme = 10;
    }



    @Override
    public ImageFragment call()
    {
        try
        {
            // Set properties for this thread
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            Thread.currentThread().setName("Computing thread " + threadID);
        }
        catch (SecurityException e)
        {
            // TODO: Handle
            e.printStackTrace();
        }



        final int    width  = endX - startX;
        final int    height = endY - startY;


        // The image that will describe the Mandelbrot set
        final BufferedImage    mandelbrotSetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        int    xCoord = 0;
        int    yCoord = 0;

        // Iterate though all pixels on the screen, and calculate the Mandelbrot set
        for (int y = startY; y < endY; y++, yCoord++)
        {
            for (int x = startX; x < endX; x++, xCoord++)
            {
                //System.out.println("Coordinate:   (" + x + ", " + y + ")");

                // Construct the complex number c
//				final double    a = (x - endX / 2) / zoom; // ORG
//				final double    b = (y - endY / 2) / zoom; // ORG
                //final double    a = (endX - x / 2) / zoom;
                //final double    b = (endY - y / 2) / zoom;
//			    final double    a = (x - xCoord / 2) / zoom;
//			    final double    b = (y - yCoord / 2) / zoom;
//			    final double    a = (xCoord - x / 2) / zoom;
//                final double    b = (yCoord - y / 2) / zoom;
                final double    a = (xCoord - endX / 2) / zoom;
                final double    b = (yCoord - endY / 2) / zoom;
                final ComplexNumber    c = new ComplexNumber(a, b);


                // Get the color of c, black color means that the complex point c is a member of the Mandelbrot set
                final int    rgb = calculateColorAt(c).getRGB();


                try
                {
                    // Color a the pixel (xCoord, yCoord) in the BufferedImage
                    mandelbrotSetImage.setRGB(xCoord, yCoord, rgb);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    System.err.println("\n\nArrayIndexOutOfBoundsException:  While coloring mandelbrotSetImage in thread [" + Thread.currentThread().getName() + "]");
                }
            }
            xCoord = 0;
        }
        System.out.println("ID:  " + threadID + ",    Dimensions:  " + width + "x" + height + "     -------->     X:  (" + startX + ", " + (endX-1) + "),          Y:  (" + startY + ", " + (endY-1) + ")");
        System.err.println("Thread \"" + Thread.currentThread().getName() + "\" done.\n");

        return new ImageFragment(threadID, mandelbrotSetImage);
    }



    /*
     * Calculates the color at the specified complex point returns the color.
     * This method uses the Normalized Iteration Count algorithm for coloring, which uses the
     * escape radius and the iteration count together, and the sine and cosine function
     * are then applied on top to generate a smooth cyclic gradient.
     */
    private Color calculateColorAt(final ComplexNumber c)
    {
        final double    ptYSq = c.getImaginaryPart() * c.getImaginaryPart();
        final double    xOff  = c.getRealPart() - 0.25;
        final double    q     = Math.pow(xOff, 2) + ptYSq;


        if (q * (q + xOff) < ptYSq / MAX_ABOSULTE_VALUE)
        {
            return Color.BLACK; //http://en.wikipedia.org/wiki/Mandelbrot_fractal#Optimizations
        }


        ComplexNumber    z = new ComplexNumber();  // z = 0 + 0i
        int    iterations = 0;


        while (iterations < maxNumberOfIterations  &&  z.abs() <= MAX_ABOSULTE_VALUE)
        {
            final double    a = z.getRealPart() * z.getRealPart() - z.getImaginaryPart() * z.getImaginaryPart() + c.getRealPart();
            final double    b = LIMIT * z.getRealPart() * z.getImaginaryPart() + c.getImaginaryPart();

            z = new ComplexNumber(a, b);
            iterations++;
        }


        if (iterations == maxNumberOfIterations)
        {
            return Color.BLACK;
        }
        else
        {
            final double    mu = iterations - Math.log(Math.log(z.abs())) / LOG2;
            final float     rg = (float) Math.cos(mu / colorScheme) / 2 + 0.5f;
            final float     b  = (float) Math.sin(mu / colorScheme) / 2 + 0.5f;

            return new Color(rg, rg, b);
        }
    }
}