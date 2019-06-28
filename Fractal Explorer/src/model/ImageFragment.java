package model;

import java.awt.image.BufferedImage;

public final class ImageFragment implements Comparable<ImageFragment>
{
    private int    id;
    private BufferedImage    fragment;



    public ImageFragment(int id, BufferedImage fragment)
    {
        this.id = id;
        this.fragment = fragment;
    }


    /**
     * Returns the ID of this image fragment. The ID is not required to be unique.
     *
     * @return the ID.
     */
    public int getID()
    {
        return id;
    }

    /**
     * Returns the image/fragment itself, which is an instance of <i>BufferedImage</i>.
     *
     * @return the image.
     */
    public BufferedImage getFragment()
    {
        return fragment;
    }



    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }
        else if (o == this)
        {
            return true;
        }
        else if (!(o instanceof ImageFragment))
        {
            return false;
        }
        else
        {
            final ImageFragment    otherImageFragment = (ImageFragment)o;

            if (id == otherImageFragment.getID())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }


    @Override
    public int hashCode()
    {
        // Returns a hash code based on the values of the ID, the image fragments hash code and the primes 7 and 31
        return (7 + id)  *  (31 + fragment.hashCode());
    }


    @Override
    public String toString()
    {
        return "Image Fragment " + id;
    }


    @Override
    public int compareTo(ImageFragment imageFragment)
    {
        if (id == imageFragment.id)
        {
            return 0;
        }
        else if (id < imageFragment.id)
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }
}