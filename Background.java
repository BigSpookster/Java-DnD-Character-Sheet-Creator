// Eli Rosenfeld
// 4/26/2026
// The purpose of this class is to store all the information present in D&D backgrounds

import java.util.*;

public class Background
{
    // A background in D&D has a name,
    // an array of stat names that will be improved,
    // and an ArrayList that stores some starter Items
    // Technically you are given a choice in how you want these stats to increase,
    // but in the interest of time, I will not worry about implementing that
    private String backgroundName;
    private String[] statTitles;
    private ArrayList<Item> starterItems;

    // If I had more time, a background would also have some other things,
    // Like skill proficiencies, tool proficiencies, and feats

    public Background(String n, String[] names, ArrayList<Item> items)
    {
        backgroundName = n;
        statTitles = names;
        starterItems = items;

    }

    // The user should get to view the background's name
    public String getBackgroundName()
    {
        return backgroundName;

    }

    // The user should get to view the ArrayList of stat names
    public String[] getStatTitles()
    {
        return statTitles;

    }

    // The user should get to view the ArrayList of Items
    public ArrayList<Item> getStarterItems()
    {
        return starterItems;

    }

}