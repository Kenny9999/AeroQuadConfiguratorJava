package AeroQuad.configurator.ui.uiutils;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class UiUtils
{
    public static final Color DEFAULT_PANEL_BACKGROUND_COLOR = Color.black;

    public static final Font NORMAL_FONT = new FontUIResource("Ubuntu-L", Font.BOLD, 18);
    public static final Font MEDIUM_FONT = new FontUIResource("Ubuntu-L", Font.BOLD, 16);
    public static final Font SMALL_FONT = new FontUIResource("Ubuntu-L", Font.BOLD, 14);

    public static final Color OFF_WHITE_COLOR = new ColorUIResource(235, 235, 235);
    public static final Color DARK_GREY_COLOR = new ColorUIResource(150, 150, 150);
    public static final Color BLACK_COLOR = Color.black;

    public static final int HEATHER_PREFERED_HEIGHT = 40;
    public static final int HEATHER_SMALL_PREFERED_HEIGHT = 25;

    public static boolean areFloatEquals(final String str1, final String str2)
    {
        try
        {
            float v1 = Float.valueOf(str1);
            float v2  = Float.valueOf(str2);
            if (v1 == v2)
            {
                return true;
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return false;
    }

    public static boolean areStringToDoubleEquals(final String number1String, final String number2String)
    {
        try
        {
            final double number1 = Double.valueOf(number1String);
            final double number2 = Double.valueOf(number2String);
            if (number1 == number2)
            {
                return true;
            }
        }
        catch (final Exception e)
        {
        }
        return false;
    }
}
