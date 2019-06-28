package model;

import java.io.Serializable;
import java.text.DecimalFormat;


/**
 * <h4>ComplexNumber.java</h4> <br>
 *
 * This class represents complex numbers z on the standard form <i>z = a + bi</i>,
 * where a and b are real numbers. a is the real part of the complex number
 * and b is the imaginary part, i is called the imaginary unit. The real part
 * and the imaginary part is often referred to as <i>Re(z)</i> and <i>Im(z)</i> respectively. <br> <br> <br>
 *
 * @author Max Alrup
 * @version 1.0
 */
public final class ComplexNumber implements Serializable
{
    private static final long    serialVersionUID = -3227375672299803843L;

    private final double    a;   // Real part, Re(z)
    private final double    b;   // Imaginary part, Im(z)

    private int hashCode;


    /**
     * Construct the complex number <i>z = 0 + 0i</i>, i.e. <i>z = 0</i>.
     */
    public ComplexNumber()
    {
        a = 0;
        b = 0;
        hashCode = 0;
    }



    /**
     * Construct a complex number on the form: <i>z = a + bi</i>, where a and b are user-defined real numbers.
     *
     * @param a - the real part.
     * @param b - the imaginary part.
     */
    public ComplexNumber(final double a, final double b)
    {
        if (Double.isFinite(a)  &&  Double.isFinite(b))
        {
            this.a = a;
            this.b = b;
        }
        else
        {
            this.a = 0;
            this.b = 0;

            if (Double.isNaN(a)  &&  Double.isNaN(b))
            {
                throw new IllegalArgumentException("NaN is not accepted as arguments to the "
                        + "constructor ComplexNumber(double a, double b).");
            }
            else
            {
                throw new IllegalArgumentException("Only finite numbers are accepted as arguments to the "
                        + "constructor ComplexNumber(double a, double b).");
            }
        }
        hashCode = 0;
    }



    /**
     * Construct a complex number on the form: <i>z = a + bi</i> from a polar form,
     * <i>z = r(cos(phi) + i sin(phi))</i>. The last parameter indicates if the
     * angle is given in radians (true) or in degrees (false).
     *
     * @param r - the absolute value.
     * @param phi - the argument.
     * @param radians - true for radian, false for degree.
     */
    public ComplexNumber(final double r, final double phi, final boolean radians) // TODO: ?
    {
        if (Double.isFinite(r)  &&  Double.isFinite(phi))
        {
            a = r * Math.cos(phi);
            b = r * Math.sin(phi);
        }
        else
        {
            a = 0;
            b = 0;

            if (Double.isNaN(a)  &&  Double.isNaN(b))
            {
                throw new IllegalArgumentException("NaN is not accepted as arguments to the "
                        + "constructor ComplexNumber(double r, double phi, boolean radians).");
            }
            else
            {
                throw new IllegalArgumentException("Only finite numbers are accepted as arguments "
                        + "to the constructor ComplexNumber(double r, double phi, boolean radians).");
            }
        }
        hashCode = 0;
    }



    /**
     * Divides the current complex number with another complex number. Throws an
     * <i>ArithmeticException</i> if dividing by zero, i.e. if <i>z = 0 + 0i</i>.
     *
     * @param z - the complex number to divide with.
     *
     * @return the difference.
     */
    // Formula for division between two complex numbers:  (a + bi) / (c + di) = ((ac + bd) / (c^2 + d^2)) + ((bc - ad) / (c^2 + d^2))i
    public ComplexNumber div(final ComplexNumber z)
    {
        if (z.getRealPart() == 0  &&  z.getImaginaryPart() == 0)  // Check if the complex division is defined
        {
            throw new ArithmeticException("Division with the complex number \"" + z + "\" is undefined.");
        }
        else
        {
            final double    re = (a * z.getRealPart() + b * z.getImaginaryPart()) / (Math.pow(z.getRealPart(), 2) + Math.pow(z.getImaginaryPart(), 2));
            final double    im = (b * z.getRealPart() - a * z.getImaginaryPart()) / (Math.pow(z.getRealPart(), 2) + Math.pow(z.getImaginaryPart(), 2));

            return new ComplexNumber(re, im);
        }
    }



    /**
     * Checks if the current ComplexNumber is equal to another ComplexNumber.
     * Equal in this case means that both the real parts are equal as well as the imaginary parts.
     *
     * @return true if the ComplexNumbers are equal.
     */
    @Override
    public boolean equals(final Object o)
    {
        if (o == null)
        {
            return false;
        }
        else if (o == this)
        {
            return true;
        }
        else if (!(o instanceof ComplexNumber))
        {
            return false;
        }
        else
        {
            final ComplexNumber    z = (ComplexNumber)o;

            if (Double.compare(a, z.getRealPart()) == 0  &&  Double.compare(b, z.getImaginaryPart()) == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }



    /**
     * Gets the distance to the origin (also referred to as the modulus or magnitude).
     *
     * @return the distance.
     */
    public double abs()
    {
        return Math.sqrt(a * a + b * b);
    }



    /**
     * Gets the distance to the origin (also referred to as the modulus or magnitude).
     *
     * @return the distance.
     */
    public double arg()
    {
        return Math.atan2(a, b);
    }



    /**
     * Gets the complex conjugate.
     *
     * @return the conjugate.
     */
    public ComplexNumber conj()
    {
        final double    re = a;
        final double    im = b * -1;  // Change the sign of the imaginary part

        return new ComplexNumber(re, im);
    }



    /**
     * Get the imaginary part of the complex number.
     *
     * @return the imaginary part.
     */
    public double getImaginaryPart()
    {
        return b;
    }



    /**
     * Get the real part of the complex number.
     *
     * @return the real part.
     */
    public double getRealPart()
    {
        return a;
    }



    @Override
    public int hashCode()
    {
        // check if the hash code have been cached by a previous call
        if (hashCode == 0)
        {
            // Get long bits to hash
            final long realLongBits      = Double.doubleToLongBits(a);
            final long imaginaryLongBits = Double.doubleToLongBits(b);

            // Hash the long bits
            final int realInt      = (int) (realLongBits ^ (realLongBits >>> 32));
            final int imaginaryInt = (int) (imaginaryLongBits ^ (imaginaryLongBits >>> 32));

            // Perform the "actual" hashing
            hashCode = 17;
            hashCode = 31 * hashCode + realInt;
            hashCode = 31 * hashCode + imaginaryInt;

            return hashCode;
        }
        else
        {
            // Cached hash code
            return hashCode;
        }
    }



    /**
     * Returns true if the imaginary part of the complex number is zero, in which case it can be considered a real number.
     *
     * @return true if Im(z) is zero, false otherwise.
     */
    public boolean isRealNumber()
    {
        if (b == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    /**
     * Returns true if both the real and imaginary part of the complex number is zero.
     *
     * @return true if Re and Im(z) are zero, false otherwise.
     */
    public boolean isZero()
    {
        if (a == 0  &&  b == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    /**
     * Subtracts the current complex number with another complex number.
     *
     * @param z - the complex number to subtract.
     *
     * @return the rest.
     */
    public ComplexNumber sub(final ComplexNumber z)
    {
        final double    re = a + z.getRealPart();
        final double    im = (b + z.getImaginaryPart()) * -1;

        return new ComplexNumber(re, im);
    }



    /**
     * Adds the current complex number with another complex number.
     *
     * @param z - the complex number to be added.
     *
     * @return the sum.
     */
    public ComplexNumber add(final ComplexNumber z)
    {
        final double    re = a + z.getRealPart();
        final double    im = b + z.getImaginaryPart();

        return new ComplexNumber(re, im);
    }



    /**
     * Returns the nth power of the current complex number.
     *
     * @param n - the power.
     * @return the nth power.
     */
    public ComplexNumber pow(final int n)
    {
        // De Moivre's formula for the nth power of z:   z^n = cos(n*phi) + i*sin(n*phi),   where phi = arg(z)
        final double    r   = arg();
        final double    a   = Math.cos(n * r);
        final double    b   = Math.sin(n * r);

        return new ComplexNumber(a, b);
    }



    /**
     * Returns the signum function of the complex number; <i>sgn(b)</i> if only a is zero,
     * zero if <i>a</i> and <i>b</i> are both zero, 1.0 if <i>a</i> is greater than zero
     * and -1.0 if <i>a</i> is less than zero.
     *
     * @return the signum function of the ComplexNumber
     */
    public double sgn()
    {
        if (isZero())
        {
            return 0;
        }
        else if (getRealPart() > 0)
        {
            return 1;
        }
        else if(getRealPart() < 0)
        {
            return -1;
        }
        else if (getRealPart() == 0)
        {
            return Math.signum(getImaginaryPart());
        }
        else
        {
            throw new ArithmeticException("The complex signum funtion sgn is undefined for (" + toString() + ")");
        }
    }



    public ComplexNumber sqrt()
    {
        final double    r   = Math.sqrt(abs());

        if (r == 0)
        {
            return new ComplexNumber();
        }
        else
        {
            final double    phi = arg() / 2;
            final double    a   = r * Math.cos(phi);
            final double    b   = r * Math.sin(phi);

            return new ComplexNumber(a, b);
        }
    }



    /**
     * Multiplies the current complex number with another complex number.
     *
     * @param z - the complex number to multiply with.
     *
     * @return the product.
     */
    // Formula for multiplication between two complex numbers:  (a + bi) * (c + di) = (ac - bd) + (bc + ad)i
    public ComplexNumber times(final ComplexNumber z)
    {
        final double    re = a * z.getRealPart() - b * z.getImaginaryPart();
        final double    im = b * z.getRealPart() + a * z.getImaginaryPart();

        return new ComplexNumber(re, im);
    }



    /**
     * Returns a string representation of the current complex number <i>z = a + bi</i>
     * on the polar form <i>z = r(cos(phi) + i*sin(phi))</i>, where <i>r = |z|</i> and <i>phi = arg(z)</i>
     *
     * @return the polar form of z.
     */
    public String toPolarForm()
    {
        final double    arg = arg();
        final double    r   = abs();

        if (r == 0)
        {
            return "0";
        }
        else
        {
            return r + "( cos(" + arg + ") + i sin(" + arg + ") )";
        }
    }



    /**
     * Returns a string that represents the complex number. The number is displayed on the form: <i>a + bi</i>.
     *
     * <h3>Special formatting information:</h3>
     * <li>If the real part is 0, it will be omitted completely. Example: 0 + 2i will be displayed as 2i.</li>
     * <li>If the imaginary part is -1, the "1" will be omitted. Example: 2 - 1i will be displayed as 2 - i.</li>
     * <li>If the imaginary part is 0, it will be omitted completely. Example: 2 + 0i will be displayed as 2.</li>
     * <li>If the imaginary part is 1, the "1" will be omitted. Example: 2 + 1i will be displayed as 2 + i.</li> <br> <br> <br>
     *
     * @return the string representation.
     */
    @Override
    public String toString()
    {
        // Only display decimals if there are any (zeroes dosen't count)
        final DecimalFormat    decimalFormatter = new DecimalFormat();
        decimalFormatter.setDecimalSeparatorAlwaysShown(false);

        if (a == 0)  // Special case #1:  if a = 0 and b € R  , print (b) instead of (0 + b)
        {
            return decimalFormatter.format(b);
        }
        if (b == -1)  // Special case #2:  if a € R and b = -1, print (a - i) instead of (a - 1i)
        {
            return decimalFormatter.format(a) + " - i";
        }
        if (b == 0)  // Special case #3:  if a € R and b = 0, print (a) instead of (a + 0i)
        {
            return decimalFormatter.format(a);
        }
        if (b == 1)  // Special case #4:  if a € R and b = 1, print (a + i) instead of (a + 1i)
        {
            return decimalFormatter.format(a) + " + i";
        }
        else
        {
            if (b < 0)
            {
                return decimalFormatter.format(a) + " - " + decimalFormatter.format(Math.abs(b)) + "i";
            }
            else
            {
                return decimalFormatter.format(a) + " + " + decimalFormatter.format(b) + "i";
            }
        }
    }



    public ComplexNumber ln()
    {
        // TODO:
        return null;
    }



    public ComplexNumber lg()
    {
        // TODO:
        return null;
    }



    public ComplexNumber log()
    {
        final double    re = Math.log(abs());
        final double    im = arg();

        return new ComplexNumber(re, im);
    }



    public ComplexNumber arcsin()
    {
        // TODO:
        return null;
    }



    public ComplexNumber arccos()
    {
        // TODO:
        return null;
    }



    public ComplexNumber arctan()
    {
        // TODO:
        return null;
    }



    public ComplexNumber sin()
    {
        final double    re = Math.cosh(b) * Math.sin(a);
        final double    im = Math.sinh(b) * Math.cos(a);

        return new ComplexNumber(re, im);
    }



    public ComplexNumber cos()
    {
        final double    re = Math.cosh(a) * Math.cos(b);
        final double    im = Math.sinh(a) * Math.sin(b);

        return new ComplexNumber(re, im);
    }



    public ComplexNumber tan()
    {
        return sin().div(cos());
    }



    public ComplexNumber sinh()
    {
        final double    re = Math.sinh(a) * Math.cos(b);
        final double    im = Math.cosh(a) * Math.sin(b);

        return new ComplexNumber(re, im);
    }



    public ComplexNumber cosh()
    {
        final double    re = Math.cosh(a) * Math.cos(b);
        final double    im = Math.sinh(a) * Math.sin(b);

        return new ComplexNumber(re, im);
    }



    public ComplexNumber tanh()
    {
        // TODO:
        return null;
    }



    public ComplexNumber exp()
    {
        final double    re = Math.exp(a) * Math.cos(b);
        final double    im = Math.exp(a) * Math.sin(b);

        return new ComplexNumber(re, im);
    }



    /** TODO:
     Negative of this complex number (chs stands for change sign).
     This produces a new Complex number and doesn't change
     this Complex number.
     <br>-(x+i*y) = -x-i*y.
     @return -z where z is this Complex number.
     */
    public ComplexNumber chs()
    {
        return new ComplexNumber(-a,-b);
    }
}