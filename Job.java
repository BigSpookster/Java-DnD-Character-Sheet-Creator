// Eli Rosenfeld
// 4/26/2026
// The purpose of this class is to define a Job object that acts as a D&D class

import java.util.*;

public class Job
{
    // A Job has a name,
    // a starter amount of hitpoints,
    // and an ArrayList of Items it gives you
    private String jobName;
    private int startHp;
    private ArrayList<Item> starterItems;

    // In a world where I had more time to work on this,
    // A job would also have a list of abilities, hit dice, etc

    public Job(String name, int totHealth, ArrayList<Item> items) throws InvalidHitPointsException
    {
        if (totHealth <= 0)
            throw new InvalidHitPointsException("You must have a positive hit point total");

        else
        {
            jobName = name;
            startHp = totHealth;
            starterItems = items;

        }

    }

    // The user should be able to view the jobName
    public String getJobName()
    {
        return jobName;

    }

    // The user should be able to view the maxHp
    public int getStartHp()
    {
        return startHp;

    }

    // The user should be able to view the list of Item
    public ArrayList<Item> getStarterItems()
    {
        return starterItems;

    }

}