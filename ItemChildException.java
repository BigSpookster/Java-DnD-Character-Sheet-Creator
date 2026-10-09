// Eli Rosenfeld
// 4/26/2026
// The purpose of this class is to create a custom exception
// that is thrown when a user attempts to add a child of Item to an items TreeSet

public class ItemChildException extends Exception
{
    public ItemChildException(String s)
    {
        super(s);

    }

}