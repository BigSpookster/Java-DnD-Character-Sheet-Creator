// Eli Rosenfeld
// 4/25/2026
// This purpose of this class is to create a base item,
// which more specific types of items will inherit from

public class Item implements Comparable<Item>
{
    // An item has a name
    // and an integer that represents the number of that item you have
    protected String itemName;
    protected int count;

    // I will allow for you to have 0 of an item,
    // but not negative of an item
    public Item(String name, int num) throws NegativeItemNumberException
    {
        if (num < 0)
            throw new NegativeItemNumberException("You must have 0 or more of an item");

        else
        {
            itemName = name;
            count = num;

        }

    }

    // It is reasonable to assume that it will be common to only have 1 of an item,
    // so a constructor that takes that into account is appropriate
    public Item(String name)
    {
        itemName = name;
        count = 1;

    }

    // I need a default constructor since other objecst inherit from this class
    public Item()
    {
        // I will make the name an empty String
        // and assume the count is 0
        itemName = "";
        count = 0;

    }

    // Users should get to add to the item count
    public void addCount(int num)
    {
        count += num;

    }

    public void addCount()
    {
        count++;

    }

    // I want the items to be sortable alphabetically
    // There will be no tie breakers
    // because two items with the same name should be treated as identical
    public int compareTo(Item other)
    {
        return this.itemName.compareTo(other.itemName);

    }

    // Users should get to view the item name
    public String getName()
    {
        return itemName;

    }

    // Users should get to view the item count
    public int getCount()
    {
        return count;

    }

    // I want a toString to make viewing items easy
    public String toString()
    {
        return itemName + "(" + count + ")";

    }

}