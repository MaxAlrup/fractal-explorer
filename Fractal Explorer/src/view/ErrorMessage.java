package view;

import javax.swing.JOptionPane;

/**
 * <i>ErrorMessage<i> makes it easy to display graphical error messages to the user. <br/> <br/> <br/>
 *
 * @author MegaMan
 * @version 1.1
 *
 */
public final class ErrorMessage
{
    private static final String    DEFAULT_MESSAGE = "An unknown error has occured.";
    private static final String    DEFAULT_TITLE   = "ERROR";



    /**
     * Displays an error pop-up window containing information about what just went wrong during the
     * execution and/or what actions to take.<br/><br/>
     *
     * The windows will halt the execution of the program until the user disposes it.
     *
     * @param message - the error message; if the message is of zero length or null, a default error
     *                  message will be displayed instead.
     */
    public static void show(String message)
    {
        // Check if a message was specified. If not, use the default error title
        if (message == null)
        {
            message = DEFAULT_MESSAGE;
        }
        else if (message.isEmpty())
        {
            message = DEFAULT_MESSAGE;
        }

        // Display the error dialog
        JOptionPane.showMessageDialog(null, message + "\n\n", DEFAULT_TITLE, JOptionPane.ERROR_MESSAGE);
    }



    /**
     * Displays an error pop-up window containing information about what just went wrong during the
     * execution and/or what actions to take.<br/><br/>
     *
     * The windows will halt the execution of the program until the user disposes it.
     *
     * @param message - the error message; if the message is of zero length or null, a default error
     *                  message will be displayed instead.
     *
     * @param title   - the title of the window; if the title is of zero length or null, a default title
     *                  will be displayed instead.
     */
    public static void show(String message, String title)
    {
        // Check if a message was specified. If not, use the default error title
        if (message == null)
        {
            message = DEFAULT_MESSAGE;
        }
        else if (message.isEmpty())
        {
            message = DEFAULT_MESSAGE;
        }

        // Check if a title was specified. If not, use the default error title
        if (title == null)
        {
            title = DEFAULT_TITLE;
        }
        else if (title.isEmpty())
        {
            title = DEFAULT_TITLE;
        }

        // Display the error dialog
        JOptionPane.showMessageDialog(null, message + "\n\n", title, JOptionPane.ERROR_MESSAGE);
    }
}