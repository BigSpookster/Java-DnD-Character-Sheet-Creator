// Eli Rosenfeld
// 4/26/20206
// The purpose of this class is to create a custom exception
// that is thrown when you try to equip an item you do not have

public class ItemDoesNotExistException extends Exception
{
    public ItemDoesNotExistException(String s)
    {
        super(s);

    }

}