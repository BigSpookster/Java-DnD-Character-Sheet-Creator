// Eli Rosenfeld
// 4/26/2026
// The purpose of this class is to create a collection
// of various types of Item objects a character could have on them in D&D

import java.util.TreeSet;

public class Inventory
{

    // An inventory has a TreeSet of basic Items,
    // a TreeSet of Weapon,
    // a TreeSet of Armor,
    // and a TreeSet of Shield
    // I chose TreeSets for this because I want them to be sorted,
    // and because they each store their own counts,
    // I do not want duplicate entries
    private TreeSet<Item> items;
    private TreeSet<Weapon> weapons;
    private TreeSet<Armor> armor;
    private TreeSet<Shield> shields;

    // An inventory also has an equipped Item for Weapon, Armor, and Shield
    private Weapon equippedWeapon;
    private Armor equippedArmor;
    private Shield equippedShield;

    // It is useful to have a constant that is easily editable
    // for someone's base AC without having any armor equipped
    // In D&D, this number is 10
    private static final int UNARMORED_BASE_AC = 10;

    // I only want a default constructor
    // so the built in functions are necessary to add things to the inventory
    public Inventory()
    {
        items = new TreeSet<Item>();
        weapons = new TreeSet<Weapon>();
        armor = new TreeSet<Armor>();
        shields = new TreeSet<Shield>();

        // You start not having anything equipped
        equippedWeapon = null;
        equippedArmor = null;
        equippedShield = null;

    }

    // This method allows you to add an Item to the items TreeSet
    public void addItem(Item m) throws ItemChildException
    {
        // If the user tries to add a Weapon, Armor, or Shield to items,
        // throw an ItemChildException
        if (m instanceof Weapon || m instanceof Armor || m instanceof Shield)
            throw new ItemChildException("You cannot add a child of Item to your Item inventory");

        else
        {
            // If the item is in the TreeSet already, add the right amount to the count
            if (items.contains(m))
                items.ceiling(m).addCount(m.getCount());

            else
                items.add(m);

        }

    }

    // This method allows you to add a Weapon to the weapons TreeSet
    public void addWeapon(Weapon w)
    {
        // If the Weapon is in the TreeSet already, add the right amount to the count
        if (weapons.contains(w))
            weapons.ceiling(w).addCount(w.getCount());

        else
            weapons.add(w);

    }

    // This method allows you to add Armor to the armor TreeSet
    public void addArmor(Armor a)
    {
        // If the Armor is in the TreeSet already, add the correct amount to the count
        if (armor.contains(a))
            armor.ceiling(a).addCount(a.getCount());

        else
            armor.add(a);

    }

    // This method allows you to add a Shield to the shields TreeSet
    public void addShield(Shield s)
    {
        // If the Shield is in the TreeSet already, add the right amount to the count
        if (shields.contains(s))
            shields.ceiling(s).addCount(s.getCount());

        else
            shields.add(s);

    }

    // This method allows you to equip a Weapon
    public void equipWeapon(String weaponName) throws ItemDoesNotExistException
    {
        // Checking if that armor exists in the inventory
        Weapon res = null;
        for (Weapon w: weapons)
        {
            if (w.getName().equals(weaponName))
            {
                res = w;
                break;

            }

        }

        // If the Weapon does not exist, throw the exception
        if (res == null)
            throw new ItemDoesNotExistException("That weapon does not exist in this inventory");

        // Otherwise, equip that item
        else
            equippedWeapon = res;

    }

    // This method allows you to equip a piece of Armor
    public void equipArmor(String armorName) throws ItemDoesNotExistException
    {
        // Checking if that armor exists in the inventory
        Armor res = null;
        for (Armor a: armor)
        {
            if (a.getName().equals(armorName))
            {
                res = a;
                break;

            }

        }

        // If the Armor does not exist, throw the exception
        if (res == null)
            throw new ItemDoesNotExistException("That armor does not exist in this inventory");

        // Otherwise, equip that item
        else
            equippedArmor = res;

    }

    // This method allows you to equip a Shield
    public void equipShield(String shieldName) throws ItemDoesNotExistException
    {
        // Checking if that armor exists in the inventory
        Shield res = null;
        for (Shield s: shields)
        {
            if (s.getName().equals(shieldName))
            {
                res = s;
                break;

            }

        }

        // If the Shield does not exist, throw the exception
        if (res == null)
            throw new ItemDoesNotExistException("That shield does not exist in this inventory");

        // Otherwise, equip that item
        else
            equippedShield = res;

    }

    // This method returns your armor class based on your equipped Armor and Shield
    public int calcAC()
    {
        int res;

        // If you have no armor equipped, use the constant base AC
        if (equippedArmor == null)
            res = UNARMORED_BASE_AC;

        else
            res = equippedArmor.getACBase();

        // Now we add the Shield's bonus if one is equipped
        if (equippedShield == null)
            return res;

        else
            return res + equippedShield.getACBonus();

    }

    // I want a toString to make inventories easy to view
    public String toString()
    {
        String res = "";

        // Equipped items are an extra little section at the temporary
        res = res + "EQUIPPED\n\n";

        // If a weapon is equipped
        if (equippedWeapon != null)
        {
            // If there aren't any following equipped items,
            // we don't want the extra newlines
            if (equippedArmor == null && equippedShield == null)
                res = res + equippedWeapon;

            else
                res = res + equippedWeapon + "\n\n";

        }

        // If armor is equipped
        if (equippedArmor != null)
        {
            // If there aren't any following equipped items,
            // we don't want the extra newlines
            if (equippedShield == null)
                res = res + equippedArmor;

            else
                res = res + equippedArmor + "\n\n";

        }

        // If a shield is equipped
        if (equippedShield != null)
            res = res + equippedShield;

        res = res + separator();

        // Regular items go first
        res = res + "ITEMS\n";
        int i = 0;
        for (Item m: items)
        {
            // Every 4 items, put a newline
            if (i % 4 == 0)
                res = res + "\n";

            // If this is the last Item, do not add the extra comma
            if (m.toString().equals(items.last().toString()))
                res = res + m.toString();

            else
                res = res + m.toString() + ", ";

            i++;

        }
        // Add a separator
        res = res + separator();

        // Weapons go next
        res = res + "WEAPONS\n\n";
        for (Weapon w: weapons)
        {
            // If this is the last weapon, do not print the extra newlines
            if (w.toString().equals(weapons.last().toString()))
                res = res + w.toString();

            else
                res = res + w.toString() + "\n\n";

        }

        // Add a separator
        res = res + separator();

        // Armor goes next
        res = res + "ARMOR\n\n";
        for (Armor a: armor)
        {
            // If this is the last armor, do not add the newlines
            if (a.toString().equals(armor.last().toString()))
                res = res + a.toString();

            else
                res = res + a.toString() + "\n\n";

        }

        // Add a separator
        res = res + separator();
        
        // Shields go last
        res = res + "SHIELDS\n\n";
        for (Shield s: shields)
        {
            // If it is the last shield, we do not want the extra newlines
            if (s.toString().equals(shields.last().toString()))
                res = res + s.toString();

            else
                res = res + s.toString() + "\n\n";

        }

        // Now, we can finally return the massive string!
        return res;

    }

    // This gives me a chance to make a static method in an object class!
    // This is great practice for methods that belong to the class
    private static String separator()
    {
        return "\n\n------------------------------------------------------\n\n";

    }

    // I need access to the equipped Armor's base AC
    public Armor getEquippedArmor()
    {
        return equippedArmor;

    }

}