// Eli Rosenfeld
// 4/24/2026
// The purpose of this class is to define an object
// that can be used to make many Dungeons and Dragons character sheets

import java.util.*;
import java.io.*;

public class CharacterSheet
{
    // The name and species of the character
    private String name;
    private String species;

    // Every character has 6 stats,
    // which go in the order they appear on real character sheets
    // This order is: Strength, Dexterity, Constitution, Intelligence, Wisdom, and Charisma
    private Stat[] stats = new Stat[6];

    // This array just has the names of the stats; it is constant and belongs to the class
    private static final String[] STAT_NAMES = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};

    // Every character has a background,
    // which is a collection of multiple attributes
    private Background background;

    // Every character has a class, which I will call a job
    // since class is a bad name to try to use in java
    // The class decides most of the character's abilities
    private Job classType;

    // Every character has an inventory of items they have
    private Inventory inventory;

    public CharacterSheet(String n, String spec, int[] statVals, Background bg, Job cl) throws InvalidAbilityScoreException, UnusualNumberOfStatsException, ItemChildException
    {
        if (statVals.length != 6)
            throw new UnusualNumberOfStatsException("You must have exactly 6 stats");

        else
        {
            name = n;
            species = spec;
            
            // This loop assumes statVals is in the correct order
            // and places them in the stats array properly
            // It should always run 6 times,
            // but putting 6 instead of statVals.length looks bad to me
            for (int i = 0; i < statVals.length; i++)
            {
                stats[i] = new Stat(STAT_NAMES[i], statVals[i]);

                // To add the bonus from the background,
                // I need to iterate through the background stat names
                String[] tempNames = bg.getStatTitles();
                for (int j = 0; j < tempNames.length; j++)
                {
                    if (stats[i].getStatName().equals(tempNames[j]))
                        stats[i].addOne();

                }

            }

            // Creating the Inventory
            inventory = new Inventory();

            // Creating a temporary ArrayList of Items
            ArrayList<Item> tempItems = cl.getStarterItems();

            // Appending the Background starter Items
            tempItems.addAll(bg.getStarterItems());

            // For each Item in the ArrayList, add that item to the inventory
            for (int i = 0; i < tempItems.size(); i++)
            {
                // Getting each item
                Item cur = tempItems.get(i);

                // If cur is an instance of Weapon, do addWeapon()
                if (cur instanceof Weapon w)
                    inventory.addWeapon(w);

                // If cur is an instance of Armor, do addArmor()
                else if (cur instanceof Armor a)
                    inventory.addArmor(a);

                // If cur is an instance of Shield, do addShield()
                else if (cur instanceof Shield s)
                    inventory.addShield(s);

                // It will only do this if it is a basic item
                else
                    inventory.addItem(cur);

            }

            // Setting the Background and Job to the right variables
            background = bg;
            classType = cl;

        }

    }

    // This method calculates the maxHp of a character
    public int calcHp()
    {
        // Max hp is start hp plus the character's Constitution modifier
        return classType.getStartHp() + stats[2].modifier();

    }

    // Calculates the armor class of a character using the Inventory's calculate function
    public int calcAC()
    {
        int res = inventory.calcAC();

        int dexModifier = stats[1].modifier();

        Armor tempArmor = inventory.getEquippedArmor();

        // If the getEquippedArmor is null, there is no Armor equipped
        // In this case, AC is the Dexterity modifier plus the AC calculated previously
        if (tempArmor == null)
            res += dexModifier;

        // Otherwise, the equipped armor exists
        else
        {
            Integer tempLimiter = tempArmor.getLimiter();

            // If the armor has no limiter,
            // You can freely add your Dexterity modifier
            if (tempLimiter == null)
                res += dexModifier;

            // Making sure the AC does not go above the limiter
            else if (tempLimiter < dexModifier)
                res += tempLimiter;

            else
                res += dexModifier;

        }

        return res;

    }

    // The user might want to equip a different weapon
    public void equipWeapon(String name) throws ItemDoesNotExistException
    {
        inventory.equipWeapon(name);

    }

    // The user might want to equip a different armor
    public void equipArmor(String name) throws ItemDoesNotExistException
    {
        inventory.equipArmor(name);

    }

    // The user might want to equip a different shield
    public void equipShield(String name) throws ItemDoesNotExistException
    {
        inventory.equipShield(name);

    }

    // I want a neat place to put the code to get the list of stats as a string,
    // so I am doing it in this method
    private String statsToString()
    {
        String res = "";
        for (int i = 0; i < stats.length; i++)
            res = res + stats[i] + "\n";

        return res;

    }

    // I want a toString method to make printing a character sheet easier from the main
    public String toString()
    {
        String res = separator();
        res = res + "GENERAL INFORMATION\n\n";

        // Name
        res = res + "Name: " + name + "\n";

        // Background and Class
        res = res + "Background: " + background.getBackgroundName() + "\t";
        res = res + "Class: " + classType.getJobName() + "\n";

        // Species
        res = res + "Species: " + species + "\n";

        // Armor Class and hp
        res = res + "Armor Class: " + this.calcAC() + "\t\t";
        res = res + "Max Hit Points: " + this.calcHp() + "\n\n";

        res = res + separator();
        res = res + "STATS\n\n";

        // All the stats
        res = res + this.statsToString();
        res = res + "\n";

        res = res + separator();
        res = res + "INVENTORY\n";
        res = res + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n";

        // All the inventory
        res = res + inventory.toString();

        res = res + "\n" + separator();

        return res;

    }

    // I need a new type of separator
    // to separate the different major sections of the sheet
    private static String separator()
    {
        return "//////////////////////////////////////////////////////////\n\n";

    }

}