// Eli Rosenfeld
// 4/25/2026
// The purpose of this class is to inherit from Item
// to create a more specific object that represents armor

public class Armor extends Item
{
    // The only differences between generic Items and Armor
    // is the base number for armor class (AC),
    // the Dexterity bonus limiter,
    // and whether or not the armor gives you disadvantage on stealth checks
    private int acBase;
    private Integer limiter; // This is an Integer because I want it to be able to be null when it does not exist
    private boolean disadvantage;

    public Armor(String name, int base, Integer lim, boolean dis, int num) throws NegativeLimiterException
    {
        // If the limiter is negative, throw a NegativeLimiterException
        if (lim != null && lim < 0)
            throw new NegativeLimiterException("Armor class limiters cannot be negative");

        else
        {
            itemName = name;
            count = num;
            acBase = base;
            limiter = lim;
            disadvantage = dis;

        }

    }

    // I want a constructor for if the armor has no limiter
    public Armor(String name, int base, boolean dis, int num)
    {
        itemName = name;
        count = num;
        acBase = base;
        limiter = null;
        disadvantage = dis;

    }

    // The second constructor reasonably assumes that you only want 1 armor
    public Armor(String name, int base, Integer lim, boolean dis) throws NegativeLimiterException
    {
        // If the limiter is negative, throw a NegativeLimiterException
        if (lim < 0)
            throw new NegativeLimiterException("Armor class limiters cannot be negative");

        else
        {
            itemName = name;
            count = 1;
            acBase = base;
            limiter = lim;
            disadvantage = dis;

        }

    }

    // I want a constructor for if the armor has no limiter and you only want 1
    // The second constructor reasonably assumes that you only want 1 armor
    public Armor(String name, int base, boolean dis)
    {
        itemName = name;
        count = 1;
        acBase = base;
        limiter = null;
        disadvantage = dis;

    }

    // Armor can have identical names and still be different
    public int compareTo(Item other)
    {
        // This strategy seems to be required to overwrite the compareTo from Item
        if (other instanceof Armor a)
        {
            if (this.itemName.equals(a.itemName))
            {
                // If the base AC is also equal
                if (a.acBase == this.acBase)
                {
                    // If the limiters are also the same
                    if (a.limiter == this.limiter)
                    {
                        // If the disadvantage is the same, they are the same object
                        if (a.disadvantage == this.disadvantage)
                            return 0;

                        // If other has no disadvantage, it goes first, so return 1
                        if (a.disadvantage == false)
                            return 1;

                        // This will only happen if this.disadvantage is false and other.disadvantage is true
                        return -1;

                    }

                    // In this case, I want to return the item with the higher limiter,
                    // but the limiter could be null for either one,
                    // so I must account for that

                    // If other has a null limiter, it gets sorted first
                    if (a.limiter == null)
                        return 1;

                    // If this has a null limiter, it gets sorted first
                    if (this.limiter == null)
                        return -1;

                    // Returning the higher limiter
                    return a.limiter - this.limiter;
                    
                }

                // If the names are equal, I want to return the higher base AC
                return a.acBase - this.acBase;

            }

        }

        return this.itemName.compareTo(other.itemName);

    }

    // Users should be able to view the base AC
    public int getACBase()
    {
        return acBase;

    }

    // Users should be able to view the Dexterity limiter
    public Integer getLimiter()
    {
        return limiter;

    }

    // Users should be able to see if armor gives them stealth disadvantage
    public boolean getDisadvantageStatus()
    {
        return disadvantage;

    }

    // I want a toString method to make armor easy to view
    public String toString()
    {
        String res = super.toString() + "\n";
        res = res + "Base AC: " + acBase;

        // If there is a limiter, add it to the string
        if (limiter != null)
            res = res + "\tDex Limit: " + limiter;
        
        if (this.disadvantage)
            res = res + "\tStealth Disadvantage";

        return res;

    }

}