// Eli Rosenfeld
// 4/25/2026
// The purpose of this class is to create a child of Item
// that represents a shield and its specific qualities

public class Shield extends Item
{
    // Shields give a bonus to armor class (AC)
    // Usually this bonus is +2,
    // but it is within reason to assume it could be larger or smaller
    private int acBonus;

    // I will allow a shield to have a negative bonus
    // because it could be cursed or something to that effect
    public Shield(String name, int bonus, int num)
    {
        itemName = name;
        count = num;
        acBonus = bonus;

    }

    // The user shouldn't have to provide a count
    // This assumes the user has only 1 shield
    public Shield(String name, int bonus)
    {
        itemName = name;
        count = 1;
        acBonus = bonus;

    }

    // Since most shields are +2 to AC,
    // there can be a default constructor that assumes
    // the name is Shield
    // the AC bonus is 2
    // and the count is 1
    public Shield()
    {
        itemName = "Shield";
        count = 1;
        acBonus = 2;

    }

    // I need to override the compareTo
    // because two shields with the same name
    // but different bonuses to AC are different objects
    public int compareTo(Item other)
    {
        // This strategy seems to be required to overwrite the compareTo from Item
        if (other instanceof Shield s)
        {
            // If the names are identical, the higher bonus should come first
            if (this.itemName.equals(s.itemName))
                return s.acBonus - this.acBonus;

        }

        return this.itemName.compareTo(other.itemName);
        
    }

    // Users should be able to view the AC bonus
    public int getACBonus()
    {
        return acBonus;

    }

    // I want a toString to make viewing shields easy
    public String toString()
    {
        String res = super.toString() + "\n";
        res = res + "AC Bonus: " + acBonus;

        return res;

    }

}