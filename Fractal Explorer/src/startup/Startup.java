package startup;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import model.ImageFragment;
import model.MandelbrotSetComputingThread;
import view.ErrorMessage;
import view.GUI;


// TODO: Zoom with the scroll-wheel


public final class Startup
{
    private static final int    DEFAULT_MAX_NUMBER_OF_ITERATIONS = 3000;
    private static final int    DEFAULT_ZOOM = 400;
    private static final int    DEFAULT_COLOR_SCHEME = 10;
    private static final int    NUMBER_OF_RESERVED_CORES = 2;

    private static final String ERROR_MESSAGE = "The program have encountered a critical error " +
            "and is forced to exit.\n\n\nTechnical information:\n\n";


    public static void main(String[] args)
    {
        int maxNumberOfIterations = 0;
        int zoom = 0;
        int colorScheme = 0;



        if (args.length != 0  &&  args.length != 3)
        {
            System.out.println("ERROR:"); // TODO:
            System.exit(1);
        }
        else if (args.length == 3)
        {
            try
            {
                maxNumberOfIterations = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e)
            {
                System.err.println("Argument" + args[0] + " must be an integer."); // TODO:
                System.exit(1);
            }

            try
            {
                zoom = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e)
            {
                System.err.println("Argument" + args[0] + " must be an integer."); // TODO:
                System.exit(1);
            }

            try
            {
                colorScheme = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e)
            {
                System.err.println("Argument" + args[0] + " must be an integer."); // TODO:
                System.exit(1);
            }
        }
        else
        {
            maxNumberOfIterations = DEFAULT_MAX_NUMBER_OF_ITERATIONS;
            zoom = DEFAULT_ZOOM;
            colorScheme = DEFAULT_COLOR_SCHEME;
        }
        //========================================================================================================================


        // Create the GUI (singleton)
        final GUI    window = GUI.getInstance();


        // Get the number of available processor cores
        final int     numberOfWorkingThreads = Runtime.getRuntime().availableProcessors() - NUMBER_OF_RESERVED_CORES;


        // Define the width of the partitions that the computing threads will be working on
        final int    partitionWidth = window.getWidth() / numberOfWorkingThreads;



        // Create a list for managing the computing threads
        final ArrayList<MandelbrotSetComputingThread>     threadCollection = new ArrayList<MandelbrotSetComputingThread>();


        // Initialize all the computing threads and add them to threadCollection
        for (int i = 0; i < numberOfWorkingThreads; i++)
        {
            //final int    startX = 0 + i * partitionWidth;
            final int    startX = i * partitionWidth;
            final int    endX   = partitionWidth + partitionWidth  * i;

            final int    id = i + 1;
            threadCollection.add(new MandelbrotSetComputingThread(id, startX, 0, endX, window.getHeight(), maxNumberOfIterations, zoom));
        }


        // Create a thread pool to handle the computing threads
        ExecutorService    pool = Executors.newFixedThreadPool(numberOfWorkingThreads);

        List<Future<ImageFragment>>    futureList = null;
        try
        {
            futureList = pool.invokeAll(threadCollection);
        }
        catch (Exception e)
        {
            // Display an pop-up error message and exit
            ErrorMessage.show(ERROR_MESSAGE + "Cumputing threads did not execute correctly.");
            System.exit(0);
        }
        pool.shutdown();
        pool = null;
        threadCollection.clear();
        //========================================================================================================================

        if (futureList == null)
        {
            // Display an pop-up error message and exit
            ErrorMessage.show(ERROR_MESSAGE + "Cumputing threads did not execute correctly.");
            System.exit(0);
        }
        else if (futureList.size() != numberOfWorkingThreads)
        {
            // Display an pop-up error message and exit
            ErrorMessage.show(ERROR_MESSAGE + "Cumputing threads did not execute correctly.");
            System.exit(0);
        }
        //System.out.println("futureList.size() = " + futureList.size());

        // The image that will represent the Mandelbrot set
        final BufferedImage    mandelbrotSetImage = new BufferedImage(window.getWidth(), window.getHeight(), BufferedImage.TYPE_INT_RGB);
        final Graphics         g = mandelbrotSetImage.getGraphics();
        boolean                test = false;

        // Merge the image fragments (partitions) to the complete Mandelbrot set image
        for (int i = 0; i < futureList.size(); i++)
        {
            try
            {
                final Image   imageFragment = futureList.get(i).get().getFragment();
                int           x = 0;
                final int     y = 0;

                if (i == 0)
                {
                    x = 0;
                }
                else
                {
                    x = i * partitionWidth - 1;
                }

                test = g.drawImage(imageFragment, x, y, null);

                //System.out.println("ID " + futureList.get(i).get().getID() + ":   " + test);
                System.out.println("X-coordinate:  " + x + ",    Y-coordinate:  " + y + "\n");

            }
            catch (Exception e)
            {
                System.err.println("Image " + (i + 1) + " failed.");
            }
        }
        System.out.println();
        System.out.println();
        window.displayImage(mandelbrotSetImage);


        g.dispose();
        futureList.clear();
        futureList = null;



        // Suggest to the VM to try and free up unused resources
        Runtime.getRuntime().gc();
    }
}