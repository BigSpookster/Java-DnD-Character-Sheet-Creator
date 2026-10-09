// Eli Rosenfeld
// 4/26/2026
// The purpose of this class is to inherit from Item
// and add the specific aspects that a weapon has in D&D

public class Weapon extends Item
{
    // A Weapon has an int that represents the number of sides of its damage die
    // and a maximum range (in feet) the weapon can be used at (this is always an int in D&D)
    private int damageDie;
    private int maxRange;

    // Constructor for if the user wants to have more than 1 of the same weapon
    public Weapon(String name, int die, int r, int num) throws InvalidWeaponException
    {
        // If the damage die is not positive, throw the InvalidWeaponException
        if (die <= 0)
            throw new InvalidWeaponException("The damage die must have a positive number of sides");

        // If the range is not positive, throw the InvalidWeaponException
        if (r <= 0)
            throw new InvalidWeaponException("The weapon's range must be a positive value");

        else
        {
            itemName = name;
            count = num;
            damageDie = die;
            maxRange = r;

        }

    }

    // Constructor for if the user wants only 1 weapon
    public Weapon(String name, int die, int r) throws InvalidWeaponException
    {
        // If the damage die is not positive, throw the InvalidWeaponException
        if (die <= 0)
            throw new InvalidWeaponException("The damage die must have a positive number of sides");

        // If the range is not positive, throw the InvalidWeaponException
        if (r <= 0)
            throw new InvalidWeaponException("The weapon's range must be a positive value");

        else
        {
            itemName = name;
            count = 1;
            damageDie = die;
            maxRange = r;

        }

    }

    // Weapons with the same name can be different
    public int compareTo(Item other)
    {
        // This strategy seems to be required to overwrite the compareTo from Item
        if (other instanceof Weapon w)
        {
            // If the names are the same
            if (this.itemName.equals(w.itemName))
            {
                // If the damage dies are equal
                if (this.damageDie == w.damageDie)
                {
                    // The larger range should come first in this case
                    return w.maxRange - this.maxRange;

                }

                // In this case, the weapon with the higher damage die goes first
                return w.damageDie - this.damageDie;

            }

        }

        return this.itemName.compareTo(other.itemName);

    }

    // Users should be able to see the damage die a weapon has
    public int getDamageDie()
    {
        return damageDie;

    }

    // Users should be able to see the max range a weapon has
    public int getMaxRange()
    {
        return maxRange;

    }

    // I want a toString method to make weapons easy to view
    public String toString()
    {
        String res = super.toString() + "\n";
        res = res + "Damage: d" + damageDie + "\t";
        res = res + "Max Range: " + maxRange + "ft";

        return res;

    }

}