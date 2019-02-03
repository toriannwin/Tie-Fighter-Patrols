/**
 * Tori Windrich
 * 3/18/2018
 * Project 3: Tie Fighter Patrols
 */
package TieFighter;

public class Payload implements Comparable
{
    private String pilot;
    private double area;
    private boolean comparePilot;
    
    //creates a Payload with the passed in name
    public Payload(String p)
    {
        pilot = p;
    }
    //returns the pilot name
    public String getPilot()
    {
        return pilot;
    }
    //returns the area
    public double getArea()
    {
        return area;
    }
    //returns the value of whether or not we are comparing by pilot
    public boolean getCompPilot()
    {
        return comparePilot;
    }
    //sets the pilot to the new name passed in
    public void setPilot(String p)
    {
        pilot = p;
    }
    //sets the area to the new number passed in
    public void setArea(double d)
    {
        area = d;
    }
    //sets the new value to the comparePilot boolean
    public void setComparePilot(boolean b)
    {
        comparePilot = b;
    }
    //overrides the comparable compareTo method
    @Override
    public int compareTo(Object o)
    {
        //if the object is a payload, check more, if not, immediately return -1
        if(o instanceof Payload)
        {
            //if we are comparing by the pilot
            if(comparePilot)
            {
                //compare the names of the two objects and return 0 if equal, 1 if
                //the calling payload is bigger, -1 if the passed in payload is bigger
                if((((Payload)o).pilot).compareTo(pilot) == 0)
                    return 0;
                else if (pilot.compareTo(((Payload)o).pilot) > 0)
                    return 1;
                else
                    return -1;
            }
            //if we are comparing by the area
            else
            {
                //converts the areas to strings in order to check if there are more than 2
                //decimal places. If so, for comparison, temporary doubles a1 and a2 must be
                //the original areas cut off after the 2nd decimal.
                String area1 = area + "";
                if (area1.contains("."))
                {
                    if(area1.length()-4 >= area1.indexOf("."))
                    {
                        area1 = area1.substring(0,area1.indexOf(".")+3);
                    }
                }
                String area2 = ((Payload)o).getArea() + "";
                if (area2.contains("."))
                {
                    if(area2.length()-4 >= area2.indexOf("."))
                    {
                        area2 = area2.substring(0,area2.indexOf(".")+3);
                    }
                }
                //compare the areas of the two objects and return 0 if equal, 1 if
                //the calling payload is bigger, -1 if the passed in payload is bigger
                double a1 = Double.parseDouble(area1);
                double a2 = Double.parseDouble(area2);
                if(a1 < a2)
                    return -1;
                else if (a1 > a2)
                    return 1;
                else
                    return 0;
            }
        }
        return -1;
    }
}
