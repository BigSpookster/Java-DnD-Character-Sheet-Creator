// Eli Rosenfeld
// 4/24/2026
// The purpose of this class is to elegantly store all the
// information necessary for a D&D character's stat

public class Stat
{
    // A stat has a name and an ability score (value)
    private String statName;
    private int value;

    public Stat(String name, int val) throws InvalidAbilityScoreException
    {
        // If the ability score is above 30 or below 1, throw the exception
        if (val > 30)
            throw new InvalidAbilityScoreException("Ability scores cannot go above 30");

        else if (val < 1)
            throw new InvalidAbilityScoreException("Ability scores cannot go below 1");

        else
            statName = name;
            value = val; 

    }

    // Ability scores can increase by 1
    public void addOne() throws InvalidAbilityScoreException
    {
        // If the new value will be greater than 30,
        // throw the InvalidAbilityScoreException
        if (value + 1 > 30)
            throw new InvalidAbilityScoreException("You cannot add to a maximized ability score");

        else
            value++;

    }

    // Ability scores can increase by a maximum of 2
    public void addTwo() throws InvalidAbilityScoreException
    {
        // If the new value will be greater than 30,
        // throw the InvalidAbilityScoreException
        if (value + 2 > 30)
            throw new InvalidAbilityScoreException("You cannot add 2 to an ability score greater than 28");

        else
            value += 2;

    }

    // The value of a stat determines the modifier
    // I use a function to return this instead of storing it as an instance variable
    // because it can change based on how a stat changes
    public int modifier()
    {
        int modifier;

        // The modifier is 10 subtracted from the value,
        // then divided by 2 (integer division)
        // This only works when the value is 10 or above
        if (value >= 10)
            modifier = (value - 10) / 2;

        // If the value is 9 or below, I need to be more careful
        else
        {
            // If the number is odd,
            // I need to subtract 1 from the same formula I used before
            if (value % 2 != 0)
                modifier = (value - 10) / 2 - 1;

            // Otherwise, I can use the same formula without change
            else
                modifier = (value - 10) / 2;

        }

        return modifier;

    }

    // I might need a toString
    public String toString()
    {
        if (this.modifier() < 0)
            return statName + ": " + value + " (" + this.modifier() + ")";
        
        return statName + ": " + value + " (+" + this.modifier() + ")";

    }

    // I will need access to the stat name
    public String getStatName()
    {
        return statName;

    }

}