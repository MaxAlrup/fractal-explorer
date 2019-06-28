package startup;

import model.ComplexNumber;


public class ComplexNumberTest
{
    public static void main(String[] args)
    {
        ComplexNumber    nan = null;
        try
        {
            nan = new ComplexNumber(Double.NaN, Double.NaN);
        }
        catch (Exception e)
        {
            System.err.println(e + "\n\n");
        }
        System.out.println("nan = " + nan);


        final ComplexNumber    z0 = new ComplexNumber();
        final ComplexNumber    z1 = new ComplexNumber(-1, 0);
        final ComplexNumber    z2 = new ComplexNumber(0, -1);
        final ComplexNumber    z3 = new ComplexNumber(-1, -1);
        final ComplexNumber    z4 = new ComplexNumber(1, 0);
        final ComplexNumber    z5 = new ComplexNumber(0, 1);
        final ComplexNumber    z6 = new ComplexNumber(1, 1);
        final ComplexNumber    z7 = new ComplexNumber(1/7, Math.sqrt(2));

        final ComplexNumber    z8 = new ComplexNumber(-4, Math.sqrt(2));
        final ComplexNumber    z9 = new ComplexNumber(13, 5);

        System.out.println("z0 = " + z0);
        System.out.println("z1 = " + z1);
        System.out.println("z2 = " + z2);
        System.out.println("z3 = " + z3);
        System.out.println("z4 = " + z4);
        System.out.println("z5 = " + z5);
        System.out.println("z6 = " + z6);
        System.out.println("z7 = " + z7);

        //System.out.println("sgn(z0) = " + z0.sgn());
        System.out.println("sgn(z8) = " + z8.sgn());
        System.out.println("sgn(z9) = " + z9.sgn());
    }
}